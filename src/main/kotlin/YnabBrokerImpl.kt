import base.JsonObject
import base.YnabResponse
import budget.*
import budget.category.YnabBudgetCategory
import budget.category.YnabCategoryHistory
import khttp.get
import khttp.post
import org.jetbrains.annotations.Mutable

class YnabBrokerImpl(var configuration: YnabConfiguration) : YnabBroker {
    override fun getAccounts(budgetYnabId: String): MutableList<YnabAccount> {
        val result = mutableListOf<YnabAccount>()

        val responseData = makeYnabGet("budgets/" + budgetYnabId + "/accounts").data

        responseData?.getArray("accounts")?.forEach { result.add(YnabAccount(it)) }

        return result;
    }

    override fun getAccount(budgetYnabId: String, accountYnabId: String): YnabAccount {
        val responseData = makeYnabGet("budgets/" + budgetYnabId + "/accounts/" + accountYnabId).data

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
        val responseData: JsonObject? = makeYnabGet("budgets/" + ynabId).data

        if(responseData == null) {
            throw Exception("Can't find data for budget")
        }

        val result = responseData.getObject("budget")
        val serverKnowledgeNumber = responseData.getInt("server_knowledge")

        return YnabBudget(result, serverKnowledgeNumber)
    }

    override fun getRefreshedBudget(staleBudget: YnabBudget): YnabBudget {
        if(!staleBudget.hasDeltaInformation()) {
            throw Exception("YnabBudget object is missing delta information (serverKnowledgeNumber)")
        }

        val responseData: JsonObject? = makeYnabGet("budgets/" + staleBudget.ynabId, staleBudget.serverKnowledgeNumber).data

        if(responseData == null) {
            throw Exception("Can't find data for budget")
        }

        val result = responseData.getObject("budget")
        val serverKnowledgeNumber = responseData.getInt("server_knowledge")

        print(result)

        val deltaBudget = YnabBudget(result, serverKnowledgeNumber)

        staleBudget.refreshFromDeltaBudget(deltaBudget);

        return staleBudget;
    }

    override fun getBudgetByName(name: String): YnabBudget {
        var budgetId = "";
        for(budgetSummary in getBudgetsPartiallyLoaded()) {
            if(budgetSummary.name == name) {
                budgetId = budgetSummary.ynabId
            }
        }

        return getBudgetById(budgetId)
    }


    override fun getOverBudgetCategories(budgetYnabId: String, month: String): List<YnabBudgetCategory> {
        val budget = getBudgetById(budgetYnabId)

        val matchingBudgetMonths = budget.budgetMonths.filter { budgetMonth -> budgetMonth.date == getMonthAsFullDate(month) }

        if(matchingBudgetMonths.size == 0) {
            throw Exception("Couldn't find a budgetMonth matching $month on budget [$budgetYnabId]")
        } else if(matchingBudgetMonths.size > 1) {
            throw Exception("Strange! Couldn't find a unique budgetMonth matching $month on budget [$budgetYnabId]")
        }

        return matchingBudgetMonths[0].categories.filter { category -> category.isOverBudget() }
    }

    private fun getMonthAsFullDate(month: String): String {
        return month + "-01"
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

        val responseData = makeYnabGet("budgets/" + budgetYnabId + "/transactions").data

        responseData?.getArray("transactions")?.forEach { result.add(YnabTransaction(it)) }

        return result;
    }

    override fun getTransaction(budgetYnabId: String, transactionYnabId: String): YnabTransaction {
        val responseData = makeYnabGet("budgets/" + budgetYnabId + "/transactions/" + transactionYnabId).data

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
        val responseData = makeYnabGet(endpointName).data

        if(responseData == null) {
            throw Exception("Can't find data for $endpointName")
        }

        return responseData.getArray(endpointName)
    }

    private fun makeYnabGet(endpointName: String, serverKnowledgeNumber: Int = 0): YnabResponse {
        val url = configuration.getUrl(endpointName) + "&last_knowledge_of_server=$serverKnowledgeNumber"
        val response = YnabResponse(get(url = url))

        if(response.hasError()) {
            print(response.errors[0].toString())
            throw Exception("Error connecting to YNAB $endpointName endpoint ${response.errors[0].toString()}]")
        }

        return response
    }

    override fun budgetRequiresRefresh(budget: YnabBudget): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createTransaction(ynabBudgetId: String, transaction: YnabTransaction): YnabTransaction {
        val endpointName = "budgets/" + ynabBudgetId + "/transactions"
        val postData = transaction.getJsonForCreate()

        print( "post [" + postData   + "]" )

        var responseData = makeYnabPost(endpointName, postData).data

        throw Exception( "Not fully implemented" )
    }

    private fun makeYnabPost(endpointName: String, postData: String): YnabResponse {
        val response = YnabResponse(post(url = configuration.getUrl(endpointName), data = postData))

        if(response.hasError()) {
            print(response.errors[0].toString())
            throw Exception("Error connecting to YNAB $endpointName endpoint ${response.errors[0].toString()}]")
        }

        return response
    }
}