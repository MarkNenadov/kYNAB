package budget

import base.JsonObject
import base.YnabObject
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import org.json.JSONObject

class YnabTransaction(jsonObject: JsonObject) : YnabObject() {
    var date = ""
    var memo = ""
    var amount = 0

    init {
        loadYnabId( jsonObject )
        date = jsonObject.getString( "date" )

        if ( !jsonObject.isNull( "memo" ) ) {
            memo = jsonObject.getString("memo")
        }

        amount = jsonObject.getInt( "amount" )
    }

    @JsonIgnore
    fun memoContains(memoText: String): Boolean {
        return memo.toUpperCase().contains( memoText.toUpperCase() )
    }
}