package com.pythonbyte.budget

import com.pythonbyte.kynab.base.JsonObject
import com.pythonbyte.kynab.budget.YnabTransaction
import org.json.JSONObject
import org.junit.Test
import kotlin.test.assertEquals

class YnabTransactionTest {
    val jsonValue = "{\"ynabId\":\"\",\"name\":\"\",\"date\":\"2018-01-01\",\"memo\":\"\",\"payee\":{\"ynabId\":\"\",\"name\":\"\",\"deleted\":false,\"transferAccountId\":\"\"},\"category\":{\"ynabId\":\"\",\"name\":\"\",\"balance\":0,\"budgeted\":0,\"activity\":0,\"overBudget\":false},\"amount\":44,\"accountId\":\"\",\"jsonForCreate\":\"{\\\"transaction\\\":{\\\"account_id\\\":\\\"\\\",\\\"date\\\":\\\"2018-01-01\\\",\\\"amount\\\":44,\\\"payee_id\\\":\\\"\\\",\\\"category_id\\\":\\\"\\\",\\\"memo\\\":\\\"\\\",\\\"cleared\\\":false,\\\"approved\\\":true,\\\"flag_color\\\":\\\"red\\\",\\\"import_id\\\":\\\"\\\"}}\"}"
    val jsonObject = JsonObject(JSONObject(jsonValue))
    val ynabTransaction = YnabTransaction(jsonObject)

    @Test
    fun testGetJson() {
        assertEquals(jsonValue, ynabTransaction.getJson())
    }

    @Test
    fun testCreateFromJson() {
        val newTransaction = ynabTransaction.createFromJson(jsonValue) as YnabTransaction
        assertEquals(ynabTransaction.getJson(), newTransaction.getJson())
    }
}
