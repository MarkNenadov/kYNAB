package com.pythonbyte.kynab

import com.pythonbyte.kynab.base.JsonObject
import com.pythonbyte.kynab.budget.category.YnabBudgetCategory
import com.pythonbyte.kynab.budget.category.YnabCategoryHistory
import com.pythonbyte.kynab.base.YnabHttp
import com.pythonbyte.kynab.budget.YnabAccount
import com.pythonbyte.kynab.budget.YnabBudget
import com.pythonbyte.kynab.budget.YnabPayee
import com.pythonbyte.kynab.budget.YnabTransaction

class YnabBrokerImpl(private val accessToken:String, val baseUrl:String = "https://api.youneedabudget.com/v1") : YnabBroker {
    override fun getAccounts(budgetYnabId: String): MutableList<YnabAccount> {
        val result = mutableListOf<YnabAccount>()

        val responseData = YnabHttp.get(getUrl("budgets/$budgetYnabId/accounts")).data

        responseData?.getArray("accounts")?.forEach { result.add(YnabAccount(it)) }

        return result
    }

    override fun getAccount(budgetYnabId: String, accountYnabId: String): YnabAccount {
        val responseData = YnabHttp.get(getUrl("budgets/$budgetYnabId/accounts/$accountYnabId")).data

        if(responseData != null) {
            return YnabAccount(responseData.getObject("account"))
        } else {
            throw Exception("Account [$accountYnabId] not found.")
        }
    }

    override fun getBudgetsPartiallyLoaded(): MutableList<YnabBudget> {
        val result: MutableList<YnabBudget> = mutableListOf()

        val dataList = getDataFromYnab("budgets")

        dataList.forEach { result.add(YnabBudget(it)) }

        return result
    }

    override fun getBudgetById(ynabId: String): YnabBudget {
        val responseData: JsonObject? = YnabHttp.get(getUrl("budgets/$ynabId")).data

        if(responseData == null) {
            throw Exception("Can't find data for com.pythonbyte.budget")
        }

        val result = responseData.getObject("com/pythonbyte/kynab/budget")
        val serverKnowledgeNumber = responseData.getInt("server_knowledge")

        return YnabBudget(result, serverKnowledgeNumber)
    }

    override fun getRefreshedBudget(staleBudget: YnabBudget): YnabBudget {
        if(!staleBudget.hasDeltaInformation()) {
            throw Exception("YnabBudget object is missing delta information (serverKnowledgeNumber)")
        }

        val responseData: JsonObject? = YnabHttp.get(getUrl("budgets/${staleBudget.ynabId}" ), staleBudget.serverKnowledgeNumber).data

        if(responseData == null) {
            throw Exception("Can't find data for com.pythonbyte.budget")
        }

        val result = responseData.getObject("com/pythonbyte/kynab/budget")
        val serverKnowledgeNumber = responseData.getInt("server_knowledge")

        val deltaBudget = YnabBudget(result, serverKnowledgeNumber)

        staleBudget.refreshFromDeltaBudget(deltaBudget)

        return staleBudget
    }

    override fun getBudgetByName(name: String): YnabBudget {
        var budgetId = ""
        for(budgetSummary in getBudgetsPartiallyLoaded()) {
            if(budgetSummary.name == name) {
                budgetId = budgetSummary.ynabId
            }
        }

        return getBudgetById(budgetId)
    }


    override fun getOverSpentCategories(budgetYnabId: String, month: String): List<YnabBudgetCategory> {
        val budget = getBudgetById(budgetYnabId)

        val matchingBudgetMonths = budget.budgetMonths.filter { budgetMonth -> budgetMonth.date == getMonthAsFullDate(month) }

        if(matchingBudgetMonths.size == 0) {
            throw Exception("Couldn't find a budgetMonth matching $month on com.pythonbyte.budget [$budgetYnabId]")
        } else if(matchingBudgetMonths.size > 1) {
            throw Exception("Strange! Couldn't find a unique budgetMonth matching $month on com.pythonbyte.budget [$budgetYnabId]")
        }

        return matchingBudgetMonths[0].categories.filter { category -> category.isOverBudget() }
    }

    private fun getMonthAsFullDate(month: String): String {
        return "$month-01"
    }

    override fun getCategoryHistory(budgetYnabId: String, categoryYnabId: String): YnabCategoryHistory {
        val categoryHistory = YnabCategoryHistory()

        val budget = getBudgetById(budgetYnabId)

        for(category in budget.getCategoriesForAllMonths()) {
            if(category.ynabId == categoryYnabId) {
                if(categoryHistory.name == "") {
                    categoryHistory.initialize(category)
                }

                categoryHistory.addItem(category.referenceBudgetMonth, category)
            }

        }

        return categoryHistory
    }

    override fun getTransactions(budgetYnabId: String): List<YnabTransaction> {
        val result = mutableListOf<YnabTransaction>()

        val responseData = YnabHttp.get(getUrl("budgets/$budgetYnabId/transactions")).data

        responseData?.getArray("transactions")?.forEach { result.add(YnabTransaction(it)) }

        return result
    }

    override fun getTransaction(budgetYnabId: String, transactionYnabId: String): YnabTransaction {
        val responseData = YnabHttp.get(getUrl("budgets/$budgetYnabId/transactions/$transactionYnabId")).data

        if(responseData != null) {
            return YnabTransaction(responseData.getObject("transaction"))
        } else {
            throw Exception("Transaction [$transactionYnabId] not found.")
        }
    }

    override fun getTransactionsByMemo(budgetYnabId: String, memoText: String): List<YnabTransaction> {
        val matchingTransactions: MutableList<YnabTransaction> = mutableListOf()

        val budget = getBudgetById(budgetYnabId)

        for(currentTransaction in budget.transactions) {
            if(currentTransaction.memoContains(memoText)) {
                matchingTransactions.add(currentTransaction)
            }
        }

        return matchingTransactions
    }

    private fun getDataFromYnab(endpointName: String): List<JsonObject> {
        val responseData = YnabHttp.get(getUrl(endpointName)).data

        if(responseData == null) {
            throw Exception("Can't find data for $endpointName")
        }

        return responseData.getArray(endpointName)
    }

    override fun budgetRequiresRefresh(budget: YnabBudget): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createTransaction(ynabBudgetId: String, transaction: YnabTransaction): YnabTransaction {
        val endpointName = "budgets/$ynabBudgetId/transactions"
        val postData = transaction.getJsonForCreate()

        var responseData = YnabHttp.post(getUrl(endpointName), postData).data

        throw Exception("Not fully implemented")
    }

    override fun getPayees(budgetYnabId: String): List<YnabPayee> {
        val result = mutableListOf<YnabPayee>()

        val responseData = YnabHttp.get(getUrl("budgets/$budgetYnabId/payees")).data

        responseData?.getArray("payees")?.forEach { result.add(YnabPayee(it)) }

        return result
    }

    override fun getPayee(budgetYnabId: String, payeeYnabId: String): YnabPayee {
        val responseData = YnabHttp.get(getUrl("budgets/$budgetYnabId/payees/$payeeYnabId")).data

        return if(responseData != null) {
            YnabPayee(responseData.getObject("payee"))
        } else {
            throw Exception("Payee [$payeeYnabId] not found.")
        }
    }

    fun getUrl( endpointName : String ) = getEndpointPath( endpointName ) + "?access_token=$accessToken"

    fun getEndpointPath (endpointName: String) = "$baseUrl/$endpointName"
}