import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import base.assertNotEmpty

class YnabBrokerTest {
    val TESTING_BUDGET_ID = "716692c6-55db-4b79-b11d-48efa0ca1f23"
    val TESTING_BUDGET_NAME = "TESTING"
    val TESTING_CATEGORY_ID = "ed953aad-02d4-4d1f-9524-ae5af699a132"
    val TESTING_TRANSACTION_ID = "b46d0227-03a2-443a-a07a-3cc149386f67"

    val ynabBroker: YnabBroker = YnabBrokerImpl(YnabConfiguration())

    @Test
    fun testGetBudgetsPartiallyLoaded() {
        val budgets = ynabBroker.getBudgetsPartiallyLoaded()

        assertNotEmpty(budgets)

        for(budget in budgets) {
            assertNotEmpty(budget.name)
            assertNotEmpty(budget.ynabId)
            assertNotEmpty(budget.lastModifiedDate)
        }
        for(budget in budgets) {
            println(budget.name + " " + budget.ynabId + " " + budget.lastModifiedDate)
        }
    }

    @Test
    fun testGetBudgetById() {
        val budget = ynabBroker.getBudgetById(TESTING_BUDGET_ID)

        assertNotNull(budget)
        assertEquals(TESTING_BUDGET_NAME, budget.name)
        assertNotEmpty(budget.ynabId)
        assertNotEmpty(budget.lastModifiedDate)
        assertEquals(TESTING_BUDGET_ID, budget.ynabId)
        assertNotEmpty( budget.budgetMonths )

        println(budget.getJson())
    }

    @Test
    fun testGetRefreshedBudgetWhenNoChange() {
        val budget = ynabBroker.getBudgetById(TESTING_BUDGET_ID)

        val budgetRefreshed = ynabBroker.getRefreshedBudget(budget)

        assertEquals(budget.serverKnowledgeNumber, budgetRefreshed.serverKnowledgeNumber)
    }

    @Test
    fun testGetRefreshedBudgetWhenNewTransaction() {
        val budget = ynabBroker.getBudgetById(TESTING_BUDGET_ID)

        // add new transaction via api

        val budgetRefreshed = ynabBroker.getRefreshedBudget(budget)

        assertEquals(budget.serverKnowledgeNumber + 1, budgetRefreshed.serverKnowledgeNumber)
    }

    @Test
    fun testGetBudgetByName() {
        val budget = ynabBroker.getBudgetByName(TESTING_BUDGET_NAME)

        println(budget.getJson())
    }

    @Test
    fun testGetCategoryHistory() {
        val categoryHistory = ynabBroker.getCategoryHistory(TESTING_BUDGET_ID, TESTING_CATEGORY_ID)

        println()
        println(categoryHistory.name)
        for(item in categoryHistory.items) {
            println(item.date + ": " + item.activity)
        }
    }

    @Test
    fun testGetOverBudgetCategories() {
        val categoriesOverBudget = ynabBroker.getOverBudgetCategories(TESTING_BUDGET_ID, "2018-06")

        assertEquals(2, categoriesOverBudget.size)

        for(categoryOverBudget in categoriesOverBudget) {
            println(categoryOverBudget.getJson())
        }
    }

    @Test
    fun testGetTransactionsByMemo() {
        val trasactions = ynabBroker.getTransactionsByMemo(TESTING_BUDGET_ID, "ashley")

        for(transaction in trasactions) {
            println(transaction.getJson())
        }
    }

    @Test
    fun testGetTransactions() {
        val trasactions = ynabBroker.getTransactions(TESTING_BUDGET_ID)

        for(transaction in trasactions) {
            println(transaction.getJson())
        }
    }

    @Test
    fun testGetTransaction() {
        val transaction = ynabBroker.getTransaction(TESTING_BUDGET_ID, TESTING_TRANSACTION_ID)
        println(transaction.getJson())
    }

}