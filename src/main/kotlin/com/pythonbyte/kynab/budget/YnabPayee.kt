package com.pythonbyte.kynab.budget

import com.pythonbyte.kynab.base.JsonObject
import com.pythonbyte.kynab.base.YnabObject

class YnabPayee() : YnabObject() {
    var deleted = false
    var transferAccountId = ""

    constructor(jsonObject: JsonObject) : this(jsonObject.getString("id"), jsonObject.getString("name")) {
        deleted = jsonObject.getBoolean("deleted")
        transferAccountId = jsonObject.getString("transfer_account_id")
    }

    constructor(id: String, payeeName: String) : this() {
        this.ynabId = id
        this.name = payeeName
    }
}