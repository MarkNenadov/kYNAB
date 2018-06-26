package budget

import base.JsonObject
import base.YnabObject

class YnabPayee() : YnabObject() {
    constructor( jsonObject: JsonObject ) : this()

    constructor(id: String, payeeName: String) : this() {
        this.ynabId = id
        this.name = payeeName
    }
}