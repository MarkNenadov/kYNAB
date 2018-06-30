package budget

import base.JsonObject
import base.YnabObject

class YnabAccount() : YnabObject() {
    var balance = 0
    var clearedBalance = 0
    var unclearedBalance = 0
    var type = ""
    var onBudget = false

    constructor(jsonObject: JsonObject) : this() {
        loadYnabId(jsonObject)
        name = jsonObject.getString("name")
        balance = jsonObject.getInt("balance")
        clearedBalance = jsonObject.getInt("cleared_balance")
        unclearedBalance = jsonObject.getInt("uncleared_balance")
        type = jsonObject.getString("type")
        onBudget = jsonObject.getBoolean("on_budget")
    }
}