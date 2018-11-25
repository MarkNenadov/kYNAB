package com.pythonbyte.kynab.budget.category

import com.pythonbyte.kynab.base.JsonObject
import com.pythonbyte.kynab.base.YnabObject
import com.pythonbyte.kynab.budget.YnabBudgetMonth
import com.fasterxml.jackson.annotation.JsonIgnore

class YnabBudgetCategory() : YnabObject() {
    var balance = 0
    var budgeted = 0
    var activity = 0

    @JsonIgnore
    var referenceBudgetMonth: YnabBudgetMonth? = null

    constructor( jsonObject: JsonObject) : this() {
        loadYnabId( jsonObject )
        name = jsonObject.getString( "name" )
        balance = jsonObject.getInt( "balance" )
        budgeted = jsonObject.getInt( "budgeted" )
        activity = jsonObject.getInt( "activity" )
    }

    constructor(id: String, categoryName: String) : this() {
        this.ynabId = id
        this.name = categoryName
    }

    fun isOverBudget() : Boolean {
        return balance < 0
    }
}