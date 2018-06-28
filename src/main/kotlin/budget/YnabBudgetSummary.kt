package budget

import base.JsonObject
import base.YnabObject

open class YnabBudgetSummary() : YnabObject() {
    var lastModifiedDate = ""

    constructor(jsonObject: JsonObject) : this() {
        loadYnabId(jsonObject)
        name = jsonObject.getString("name")
        lastModifiedDate = jsonObject.getString("last_modified_on")
    }
}