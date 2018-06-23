package budget.category

import base.JsonObject
import base.YnabObject
import budget.YnabBudgetMonth
import com.fasterxml.jackson.annotation.JsonIgnore

class YnabBudgetCategory(categoryJsonObject: JsonObject) : YnabObject() {
    var balance = 0
    var budgeted = 0
    var activity = 0

    @JsonIgnore
    var referenceBudgetMonth: YnabBudgetMonth? = null

    init {
        name = categoryJsonObject.getString( "name" )
        ynabId = categoryJsonObject.getString( "id" )
        balance = categoryJsonObject.getInt( "balance" )
        budgeted = categoryJsonObject.getInt( "budgeted" )
        activity = categoryJsonObject.getInt( "activity" )
    }

    fun isOverBudget() : Boolean {
        return balance < 0
    }
}