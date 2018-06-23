import org.junit.Test

class YnabBrokerTest {
    val TESTING_BUDGET_ID = "put a budget id here"
    val TESTING_CATEGORY_ID = "put a category id here"

    val ynabBroker = YnabBroker( YnabConfiguration() )
    @Test
    fun testGetBudgetSummaries() {
        val budgets = ynabBroker.getBudgetSummaries()

        for (budget in budgets) {
            println( budget.name + " " + budget.ynabId + " " + budget.lastModifiedDate )
        }
    }

    @Test
    fun testGetBudget() {
        val budget = ynabBroker.getBudget( TESTING_BUDGET_ID )

        println( budget.getJson() )
    }

    @Test
    fun testGetCategoryHistory() {
        val categoryHistory = ynabBroker.getCategoryHistory( TESTING_BUDGET_ID, TESTING_CATEGORY_ID )

        println()
        println( categoryHistory.name )
        for(item in categoryHistory.items) {
            println( item.date + ": " + item.activity )
        }
    }

    @Test
    fun testGetOverBudgetCategories() {
        val categoriesOverBudget = ynabBroker.getOverBudgetCategories( TESTING_BUDGET_ID )

        for(categoryOverBudget in categoriesOverBudget) {
            println( categoryOverBudget.getJson() )
        }
    }

    @Test
    fun testGetTransactionsByMemo() {
        val trasactions = ynabBroker.getTransactionsByMemo( TESTING_BUDGET_ID, "ashley" )

        for(transaction in trasactions) {
            println( transaction.getJson() )
        }
    }

}