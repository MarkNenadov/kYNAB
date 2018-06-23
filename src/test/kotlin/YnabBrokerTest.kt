import org.junit.Test

class YnabBrokerTest {
    val TESTING_BUDGET_ID = "8f35f8d5-e0ba-421e-b41e-8a43235d0f3b"
    val TESTING_BUDGET_NAME = "2017+Family Budget"
    val TESTING_CATEGORY_ID = "put a category id here"

    val ynabBroker = YnabBrokerImpl( YnabConfiguration() )
    @Test
    fun testGetBudgetSummaries() {
        val budgets = ynabBroker.getBudgetSummaries()

        for (budget in budgets) {
            println( budget.name + " " + budget.ynabId + " " + budget.lastModifiedDate )
        }
    }

    @Test
    fun testGetBudgetById() {
        val budget = ynabBroker.getBudgetById( TESTING_BUDGET_ID )

        println( budget.getJson() )
    }

    @Test
    fun testGetBudgetByName() {
        val budget = ynabBroker.getBudgetByName( TESTING_BUDGET_NAME )

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