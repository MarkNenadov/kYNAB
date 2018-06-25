import org.junit.Test
import kotlin.test.assertEquals

class YnabBrokerTest {
    val TESTING_BUDGET_ID = "8f35f8d5-e0ba-421e-b41e-8a43235d0f3b"
    val TESTING_BUDGET_NAME = "2017+Family Budget"
    val TESTING_CATEGORY_ID = "put a category id here"
    val TESTING_TRANSACTION_ID = "b46d0227-03a2-443a-a07a-3cc149386f67"

    val ynabBroker : YnabBroker = YnabBrokerImpl( YnabConfiguration() )
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
        val categoriesOverBudget = ynabBroker.getOverBudgetCategories( TESTING_BUDGET_ID, "2018-06" )

        assertEquals( 2, categoriesOverBudget.size )

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

    @Test
    fun testGetTransactions() {
        val trasactions = ynabBroker.getTransactions( TESTING_BUDGET_ID )

        for(transaction in trasactions) {
            println( transaction.getJson() )
        }
    }

    @Test
    fun testGetTransaction() {
        val transaction = ynabBroker.getTransaction( TESTING_BUDGET_ID, TESTING_TRANSACTION_ID )
        println( transaction.getJson() )
    }

}