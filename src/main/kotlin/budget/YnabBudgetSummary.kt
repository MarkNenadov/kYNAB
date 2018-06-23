package budget

import base.JsonObject
import base.YnabObject
import org.json.JSONObject

open class YnabBudgetSummary(jsonObject: JsonObject ) : YnabObject() {
    var lastModifiedDate = ""

    init {
        name = jsonObject.getString("name" )
        ynabId = jsonObject.getString("id" )
        lastModifiedDate = jsonObject.getString( "last_modified_on" )
    }
}