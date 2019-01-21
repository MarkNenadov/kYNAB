package com.pythonbyte.budget

import com.pythonbyte.kynab.YnabBroker
import com.pythonbyte.kynab.YnabBrokerImpl
import com.pythonbyte.kynab.base.assertNotEmpty
import com.pythonbyte.kynab.budget.YnabTransaction
import org.junit.Test
import org.yaml.snakeyaml.Yaml
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class YnabBrokerTest {
    val defaultTestPropertiesPath = "src/test/resources/properties.yml"
    val testProperties = loadTestProperties()

    private val accessToken: String = checkAccessToken(testProperties["accessToken"] as String)
    private val testingBudgetId: String by testProperties
    private val testingBudgetName: String by testProperties
    private val testingCategoryId: String by testProperties
    private val testingTransactionId: String by testProperties
    private val testingPayeeId: String by testProperties
    private val testingAccoiuntId: String by testProperties

    val ynabBroker: YnabBroker = YnabBrokerImpl(accessToken)

    @Test
    fun `Test getting budgets from YNAB`() {
        val budgets = ynabBroker.getBudgetsPartiallyLoaded()

        assertNotEmpty(budgets)

        for (budget in budgets) {
            assertNotEmpty(budget.name)
            assertNotEmpty(budget.ynabId)
            assertNotEmpty(budget.lastModifiedDate)
        }
        for (budget in budgets) {
            println(budget.name + " " + budget.ynabId + " " + budget.lastModifiedDate)
        }
    }

    @Test
    fun `Test getting budget by ID`() {
        val budget = ynabBroker.getBudgetById(testingBudgetId)

        assertNotNull(budget)
        assertEquals(testingBudgetName, budget.name)
        assertNotEmpty(budget.ynabId)
        assertNotEmpty(budget.lastModifiedDate)
        assertEquals(testingBudgetId, budget.ynabId)
        assertNotEmpty(budget.budgetMonths)

        println(budget.getJson())
    }

    @Test
    fun `Test refreshing budget without changes`() {
        val budget = ynabBroker.getBudgetById(testingBudgetId)

        val budgetRefreshed = ynabBroker.getRefreshedBudget(budget)

        assertEquals(budget.serverKnowledgeNumber, budgetRefreshed.serverKnowledgeNumber)
    }

    @Test
    fun `Test refreshing budget with new transactions`() {
        val budget = ynabBroker.getBudgetById(testingBudgetId)

        // add new transaction via api
        ynabBroker.createTransaction(testingBudgetId, YnabTransaction())

        val budgetRefreshed = ynabBroker.getRefreshedBudget(budget)

        assertEquals(budget.serverKnowledgeNumber + 1, budgetRefreshed.serverKnowledgeNumber)
    }

    @Test
    fun `Test getting budget by name`() {
        val budget = ynabBroker.getBudgetByName(testingBudgetName)

        // TODO: Add assertion
        println(budget.getJson())
    }

    @Test
    fun `Test getting category history`() {
        val categoryHistory = ynabBroker.getCategoryHistory(testingBudgetId, testingCategoryId)

        // TODO: Add assertion
        println()
        println(categoryHistory.name)
        for (item in categoryHistory.items) {
            println(item.date + ": " + item.activity)
        }
    }

    @Test
    fun `Test getting over-spent categories`() {
        val categoriesOverBudget = ynabBroker.getOverSpentCategories(testingBudgetId, "2018-06")

        assertEquals(1, categoriesOverBudget.size)

        for (categoryOverBudget in categoriesOverBudget) {
            println(categoryOverBudget.getJson())
        }
    }

    @Test
    fun `Test getting transaction by memo`() {
        val transactions = ynabBroker.getTransactionsByMemo(testingBudgetId, "ashley")

        // TODO: Add assertion

        for (transaction in transactions) {
            println(transaction.getJson())
        }
    }

    @Test
    fun `Test getting transactions`() {
        val transactions = ynabBroker.getTransactions(testingBudgetId)

        // TODO: Add assertion

        for (transaction in transactions) {
            println(transaction.getJson())
        }
    }

    @Test
    fun `Test getting transaction by ID`() {
        val transaction = ynabBroker.getTransaction(testingBudgetId, testingTransactionId)
        println(transaction.getJson())
        // TODO: Add assertion
    }

    @Test
    fun `Test creating transaction`() {
        var transaction = ynabBroker.getTransaction(testingBudgetId, testingTransactionId)
        transaction = ynabBroker.createTransaction(testingBudgetId, transaction)

        // TODO: Add assertion
        print(transaction)
    }

    @Test
    fun `Test get accounts`() {
        val accounts = ynabBroker.getAccounts(testingBudgetId)

        // TODO: Add assertion
        for (account in accounts) {
            println(account.getJson())
        }
    }

    @Test
    fun `Test get account by ID`() {
        val account = ynabBroker.getAccount(testingBudgetId, testingAccoiuntId)

        assertNotNull(account)
        assertEquals(testingAccoiuntId, account.ynabId)
    }

    @Test
    fun `Test get payees`() {
        val payees = ynabBroker.getPayees(testingBudgetId)

        assertNotEmpty(payees)
    }

    @Test
    fun `Test get payees by ID`() {
        val payee = ynabBroker.getPayee(testingBudgetId, testingPayeeId)

        assertNotNull(payee)
        assertEquals(testingPayeeId, payee.ynabId)
        assertNotEmpty(payee.name)
    }

    private fun loadTestProperties(): Properties {
        val path = System.getenv("KYNAB_PROPS") ?: defaultTestPropertiesPath
        println("Loading test properties from $path")
        return Yaml().loadAs(Files.newInputStream(Paths.get(path)), Properties::class.java)
    }

    private fun checkAccessToken(token: String?): String {
        check(token != null) { "Missing access token, \"accessToken\", in properties file." }
        check(token != "access token here") { "Must replace access token in properties file before testing." }
        return token
    }
}