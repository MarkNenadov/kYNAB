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

        accountId = transactionJsonObject.getString("account_id")
    }

    @JsonIgnore
    fun memoContains(memoText: String): Boolean {
        return memo.toUpperCase().contains(memoText.toUpperCase())
    }

    fun getJsonForCreate() : String {
        val rootNode = mapper.createObjectNode();

        val transactionNode = mapper.createObjectNode();
        transactionNode.put( "account_id", accountId )
        transactionNode.put( "date", "" )
        transactionNode.put( "amount", amount )
        transactionNode.put( "payee_id", payee?.ynabId )
        transactionNode.put( "category_id", category?.ynabId )
        transactionNode.put( "memo", memo )
        // need to do cleared, approved, flag_color, import_id for real
        transactionNode.put( "cleared", false )
        transactionNode.put( "approved", true )
        transactionNode.put( "flag_color", "red" )
        transactionNode.put( "import_id", "" )

        // TODO: figure out alterative to this deprecated method
        rootNode.put( "transaction", transactionNode)

        return rootNode.toString()
    }
}