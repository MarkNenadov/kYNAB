import budget.YnabBudget
import budget.YnabBudgetSummary
import budget.YnabTransaction
import budget.category.YnabBudgetCategory
import budget.category.YnabCategoryHistory

interface YnabBroker {
    fun getBudgetSummaries() : MutableList<YnabBudgetSummary>
    fun getBudgetById( ynabId: String ): YnabBudget
    fun getBudgetByName( name: String ): YnabBudget
    fun getOverBudgetCategories(budgetYnabId: String, month: String): List<YnabBudgetCategory>
    fun getCategoryHistory( budgetYnabId: String, categoryYnabId: String): YnabCategoryHistory
    fun getTransactionsByMemo( budgetYnabId: String, memoText: String): List<YnabTransaction>
}