package com.pythonbyte.kynab.budget

import com.pythonbyte.kynab.base.JsonObject
import com.pythonbyte.kynab.base.YnabObject
import com.pythonbyte.kynab.budget.category.YnabBudgetCategory

class YnabBudgetMonth() : YnabObject() {
    var date = ""
    var note = ""
    var toBeBudgetedAmount = 0
    var categories: MutableList<YnabBudgetCategory> = mutableListOf()


    constructor(monthJsonObject: JsonObject) : this() {
        date = monthJsonObject.getString("month")
        note = monthJsonObject.getString("note")
        toBeBudgetedAmount = monthJsonObject.getInt("to_be_budgeted")

        for (categoryJsonObject in monthJsonObject.getArray("categories")) {
            addCategory(categoryJsonObject)
        }
    }

    private fun addCategory(jsonObject: JsonObject) {
        categories.add(YnabBudgetCategory(jsonObject))
    }

}