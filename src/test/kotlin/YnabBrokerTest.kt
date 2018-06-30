import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import base.assertNotEmpty
import budget.YnabTransaction;

class YnabBrokerTest {
    val TESTING_BUDGET_ID = "716692c6-55db-4b79-b11d-48efa0ca1f23"
    val TESTING_BUDGET_NAME = "TESTING"
    val TESTING_CATEGORY_ID = "ed953aad-02d4-4d1f-9524-ae5af699a132"
    val TESTING_TRANSACTION_ID = "96cc9028-21ad-4f7f-b575-72612ceb311e"
    val TESTING_ACCOIUNT_ID = "b0b3e9ba-4f2c-44b8-91d5-27815ae86fed"

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
        assertNotEmpty(budget.budgetMonths)

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
        var ynabTransaction = YnabTransaction()
        ynabTransaction = ynabBroker.createTransaction(TESTING_BUDGET_ID, ynabTransaction);

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
        val transactions = ynabBroker.getTransactions(TESTING_BUDGET_ID)

        for(transaction in transactions) {
            println(transaction.getJson())
        }
    }

    @Test
    fun testGetTransaction() {
        val transaction = ynabBroker.getTransaction(TESTING_BUDGET_ID, TESTING_TRANSACTION_ID)
        println(transaction.getJson())
    }

    @Test
    fun testCreateTransaction() {
        var transaction = ynabBroker.getTransaction(TESTING_BUDGET_ID, TESTING_TRANSACTION_ID)
        transaction = ynabBroker.createTransaction( TESTING_BUDGET_ID, transaction)

        print( transaction )
    }

    @Test
    fun testGetAccounts() {
        val accounts = ynabBroker.getAccounts(TESTING_BUDGET_ID)

        for(account in accounts) {
            println(account.getJson())
        }
    }

    @Test
    fun testGetAccount() {
        val account = ynabBroker.getAccount(TESTING_BUDGET_ID, TESTING_ACCOIUNT_ID)

        assertNotNull(account)
        assertEquals(TESTING_ACCOIUNT_ID, account.ynabId)
    }

}