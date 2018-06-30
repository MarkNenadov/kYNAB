package budget

import base.JsonObject
import base.YnabObject
import budget.category.YnabBudgetCategory
import com.fasterxml.jackson.annotation.JsonIgnore

class YnabTransaction() : YnabObject() {
    var date = ""
    var memo = ""
    var payee: YnabPayee? = null;
    var category: YnabBudgetCategory? = null;
    var amount = 0
    var accountId = ""

    constructor(transactionJsonObject: JsonObject) : this() {
        loadYnabId(transactionJsonObject)
        date = transactionJsonObject.getString("date")

        if(!transactionJsonObject.isNull("memo")) {
            memo = transactionJsonObject.getString("memo")
        }

        amount = transactionJsonObject.getInt("amount")

        val categoryName = ""
        if(transactionJsonObject.hasKey("category_name")) {
            transactionJsonObject.getString("category_name")
        }

        category = YnabBudgetCategory(transactionJsonObject.getString("category_id"), categoryName)

        val payeeName = ""
        if(transactionJsonObject.hasKey("payee_name")) {
            transactionJsonObject.getString("payee_name")
        }
        payee = YnabPayee(transactionJsonObject.getString("payee_id"), payeeName)
    }

    @JsonIgnore
    fun memoContains(memoText: String): Boolean {
        return memo.toUpperCase().contains(memoText.toUpperCase())
    }

    override fun getJson() : String {
        val objectNode = mapper.createObjectNode();
        objectNode.put( "account_id", accountId )
        objectNode.put( "date", "" )
        objectNode.put( "amount", amount )

        return ""
    }
}