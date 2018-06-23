package budget

import base.JsonObject
import budget.category.YnabBudgetCategory

class YnabBudget(jsonObject : JsonObject) : YnabBudgetSummary( jsonObject  ) {
    val budgetMonths : MutableList<YnabBudgetMonth> = mutableListOf()
    val accounts : MutableList<YnabAccount> = mutableListOf()
    val payees : MutableList<YnabPayee> = mutableListOf()
    val transactions : MutableList<YnabTransaction> = mutableListOf()

    init {
        loadBudgetMonths( jsonObject )
        loadAccountsList( jsonObject )
        loadPayees( jsonObject )
        loadTransactions( jsonObject )
    }

    private fun loadTransactions(jsonObject: JsonObject) {
        for ( trasactionJsonObject in jsonObject.getArray("transactions" ) ) {
            addTransaction( trasactionJsonObject )
        }
    }

    private fun addTransaction(jsonObject: JsonObject) {
        transactions.add( YnabTransaction( jsonObject ) )
    }

    private fun loadPayees(jsonObject: JsonObject) {
        for (payeeJsonObject in jsonObject.getArray("payees")) {
            payees.add( YnabPayee( payeeJsonObject ))
        }
    }

    private fun loadAccountsList( jsonObject: JsonObject ) {
        for (accountJsonObject in jsonObject.getArray("accounts")) {
            accounts.add( YnabAccount( accountJsonObject ) )
        }
    }

    private fun loadBudgetMonths( jsonObject: JsonObject ) {
        for ( monthJsonObject in jsonObject.getArray("months" ) ) {
            addBudgetMonth( monthJsonObject )
        }
    }

    private fun addBudgetMonth( jsonObject: JsonObject) {
        budgetMonths.add( YnabBudgetMonth( jsonObject ) )
    }

    fun getCategoriesForAllMonths(): List<YnabBudgetCategory> {
        val categories: MutableList<YnabBudgetCategory> = mutableListOf()

        for( budgetMonth in budgetMonths ) {
            for( category in budgetMonth.categories ) {
                category.referenceBudgetMonth = budgetMonth
                categories.add( category )
            }
        }
        return categories
    }
}