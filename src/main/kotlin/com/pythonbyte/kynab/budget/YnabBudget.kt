package com.pythonbyte.kynab.budget

import com.pythonbyte.kynab.base.JsonObject
import com.pythonbyte.kynab.base.YnabObject
import com.pythonbyte.kynab.budget.category.YnabBudgetCategory

class YnabBudget() : YnabObject() {
    val budgetMonths: MutableList<YnabBudgetMonth> = mutableListOf()
    val accounts: MutableList<YnabAccount> = mutableListOf()
    val payees: MutableList<YnabPayee> = mutableListOf()
    val transactions: MutableList<YnabTransaction> = mutableListOf()
    var lastModifiedDate = ""
    var serverKnowledgeNumber = 0

    constructor(jsonObject: JsonObject) : this() {
        loadYnabId(jsonObject)
        loadName(jsonObject)
        loadModifiedDate(jsonObject)
        loadBudgetMonths(jsonObject)
        loadAccountsList(jsonObject)
        loadPayees(jsonObject)
        loadTransactions(jsonObject)
    }

    constructor(jsonObject: JsonObject, serverKnowledgeNumber: Int) : this(jsonObject) {
        this.serverKnowledgeNumber = serverKnowledgeNumber
    }

    private fun loadModifiedDate(jsonObject: JsonObject) {
        lastModifiedDate = jsonObject.getString("last_modified_on")
    }

    private fun loadTransactions(jsonObject: JsonObject) {
        for (transactionJsonObject in jsonObject.getArray("transactions")) {
            addTransaction(transactionJsonObject)
        }
    }

    private fun addTransaction(transactionJsonObject: JsonObject) {
        transactions.add(YnabTransaction(transactionJsonObject))
    }

    private fun loadPayees(jsonObject: JsonObject) {
        for (payeeJsonObject in jsonObject.getArray("payees")) {
            payees.add(YnabPayee(payeeJsonObject))
        }
    }

    private fun loadAccountsList(jsonObject: JsonObject) {
        for (accountJsonObject in jsonObject.getArray("accounts")) {
            accounts.add(YnabAccount(accountJsonObject))
        }
    }

    private fun loadBudgetMonths(jsonObject: JsonObject) {
        for (monthJsonObject in jsonObject.getArray("months")) {
            addBudgetMonth(monthJsonObject)
        }
    }

    private fun addBudgetMonth(jsonObject: JsonObject) {
        budgetMonths.add(YnabBudgetMonth(jsonObject))
    }

    fun getCategoriesForAllMonths(): List<YnabBudgetCategory> {
        val categories: MutableList<YnabBudgetCategory> = mutableListOf()

        for (budgetMonth in budgetMonths) {
            for (category in budgetMonth.categories) {
                category.referenceBudgetMonth = budgetMonth
                categories.add(category)
            }
        }
        return categories
    }

    fun hasDeltaInformation(): Boolean {
        return serverKnowledgeNumber != 0
    }

    // TODO: Finish implementing this
    fun refreshFromDeltaBudget(deltaBudget: YnabBudget) {
        serverKnowledgeNumber = deltaBudget.serverKnowledgeNumber
    }
}