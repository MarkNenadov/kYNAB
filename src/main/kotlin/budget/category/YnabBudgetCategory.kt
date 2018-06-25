package budget.category

import base.JsonObject
import base.YnabObject
import budget.YnabBudgetMonth
import com.fasterxml.jackson.annotation.JsonIgnore

class YnabBudgetCategory(jsonObject: JsonObject) : YnabObject() {
    var balance = 0
    var budgeted = 0
    var activity = 0

    @JsonIgnore
    var referenceBudgetMonth: YnabBudgetMonth? = null

    init {
        loadYnabId( jsonObject )
        name = jsonObject.getString( "name" )
        balance = jsonObject.getInt( "balance" )
        budgeted = jsonObject.getInt( "budgeted" )
        activity = jsonObject.getInt( "activity" )
    }

    fun isOverBudget() : Boolean {
        return balance < 0
    }
}