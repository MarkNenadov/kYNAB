import budget.YnabBudget
import budget.YnabTransaction
import budget.category.YnabBudgetCategory
import budget.category.YnabCategoryHistory

class MockYnabBroker : YnabBroker {
    override fun getRefreshedBudget(staleBudget: YnabBudget): YnabBudget {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun budgetRequestRefresh(budget: YnabBudget): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getBudgetsPartiallyLoaded(): MutableList<YnabBudget> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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

    override fun getOverBudgetCategories(budgetYnabId: String, month: String): List<YnabBudgetCategory> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getTransactions(budgetYnabId: String) : List<YnabTransaction> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getTransaction(budgetYnabId: String, transactionYnabId: String): YnabTransaction {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getCategoryHistory(budgetYnabId: String, categoryYnabId: String): YnabCategoryHistory {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getTransactionsByMemo(budgetYnabId: String, memoText: String): List<YnabTransaction> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}