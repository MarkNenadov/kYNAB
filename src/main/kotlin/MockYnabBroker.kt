import budget.YnabBudget
import budget.YnabBudgetSummary
import budget.YnabTransaction
import budget.category.YnabBudgetCategory
import budget.category.YnabCategoryHistory

class MockYnabBroker : YnabBroker {
    override fun getBudgetSummaries(): MutableList<YnabBudgetSummary> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getBudgetById(ynabId: String): YnabBudget {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getBudgetByName(name: String): YnabBudget {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getOverBudgetCategories(budgetYnabId: String): List<YnabBudgetCategory> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getCategoryHistory(budgetYnabId: String, categoryYnabId: String): YnabCategoryHistory {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getTransactionsByMemo(budgetYnabId: String, memoText: String): List<YnabTransaction> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}