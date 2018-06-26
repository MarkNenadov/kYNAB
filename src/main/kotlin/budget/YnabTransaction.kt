package budget

import base.JsonObject
import base.YnabObject
import budget.category.YnabBudgetCategory
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import org.json.JSONObject

class YnabTransaction() : YnabObject() {
    var date = ""
    var memo = ""
    var payee : YnabPayee? = null;
    var category : YnabBudgetCategory? = null;
    var amount = 0

    constructor( jsonObject: JsonObject ) : this() {
        loadYnabId( jsonObject )
        date = jsonObject.getString( "date" )

        if ( !jsonObject.isNull( "memo" ) ) {
            memo = jsonObject.getString("memo")
        }

        amount = jsonObject.getInt( "amount" )

        category = YnabBudgetCategory( jsonObject.getString( "category_id" ), jsonObject.getString( "category_name" ) )
        payee = YnabPayee( jsonObject.getString( "payee_id" ), jsonObject.getString( "payee_name" ) )
    }

    @JsonIgnore
    fun memoContains(memoText: String): Boolean {
        return memo.toUpperCase().contains( memoText.toUpperCase() )
    }
}