package budget

import base.JsonObject
import base.YnabObject
import org.json.JSONObject

open class YnabBudgetSummary(jsonObject: JsonObject ) : YnabObject() {
    var lastModifiedDate = ""

    init {
        loadYnabId( jsonObject )
        name = jsonObject.getString("name" )
        lastModifiedDate = jsonObject.getString( "last_modified_on" )
    }
}