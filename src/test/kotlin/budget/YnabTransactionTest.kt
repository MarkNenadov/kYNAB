package budget

import base.JsonObject
import org.json.JSONObject
import org.junit.Test
import kotlin.test.assertEquals

class YnabTransactionTest {
    val jsonValue = "{\"ynabId\":\"\",\"name\":\"\",\"date\":\"2018-01-01\",\"memo\":\"\",\"amount\":44}"
    val jsonObject = JsonObject(JSONObject(jsonValue))
    val ynabTransaction = YnabTransaction(jsonObject)

    @Test
    fun testGetJson() {
        assertEquals( jsonValue, ynabTransaction.getJson())
    }

    @Test
    fun testCreateFromJson() {
        val newTransaction = ynabTransaction.createFromJson( jsonValue ) as YnabTransaction
        assertEquals( ynabTransaction.getJson(), newTransaction.getJson())
    }

}