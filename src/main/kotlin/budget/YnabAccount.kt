package budget

import base.JsonObject

class YnabAccount( jsonObject: JsonObject) {
    var name : String = ""
    var balance = 0

    init {
        name = jsonObject.getString( "name" )
        balance = jsonObject.getInt("balance" )
    }
}