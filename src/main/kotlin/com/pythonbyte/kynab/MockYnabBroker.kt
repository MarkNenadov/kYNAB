package com.pythonbyte.kynab

import com.pythonbyte.kynab.budget.YnabAccount
import com.pythonbyte.kynab.budget.YnabBudget
import com.pythonbyte.kynab.budget.YnabPayee
import com.pythonbyte.kynab.budget.YnabTransaction
import com.pythonbyte.kynab.budget.category.YnabBudgetCategory
import com.pythonbyte.kynab.budget.category.YnabCategoryHistory

class MockYnabBroker : YnabBroker {
    override fun createTransaction(ynabBudgetId: String, transaction: YnabTransaction): YnabTransaction {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override fun getAccounts(budgetYnabId: String): MutableList<YnabAccount> {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override fun getAccount(budgetYnabId: String, accountYnabId: String): YnabAccount {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override fun getPayees(budgetYnabId: String): List<YnabPayee> {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override fun getPayee(budgetYnabId: String, payeeYnabId: String): YnabPayee {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override fun getRefreshedBudget(staleBudget: YnabBudget): YnabBudget {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override fun budgetRequiresRefresh(budget: YnabBudget): Boolean {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override fun getBudgetsPartiallyLoaded(): MutableList<YnabBudget> {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override fun getBudgetById(ynabId: String): YnabBudget {
        val budget = YnabBudget()
        budget.ynabId = ynabId

        return budget
    }

    override fun getBudgetByName(name: String): YnabBudget {
        val budget = YnabBudget()
        budget.name = name

        return budget
    }

    override fun getOverSpentCategories(budgetYnabId: String, month: String): List<YnabBudgetCategory> {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override fun getTransactions(budgetYnabId: String): List<YnabTransaction> {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override fun getTransaction(budgetYnabId: String, transactionYnabId: String): YnabTransaction {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override fun getCategoryHistory(budgetYnabId: String, categoryYnabId: String): YnabCategoryHistory {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override fun getTransactionsByMemo(budgetYnabId: String, memoText: String): List<YnabTransaction> {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }
}
