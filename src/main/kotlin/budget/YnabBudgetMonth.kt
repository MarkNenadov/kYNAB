package budget

import base.JsonObject
import budget.category.YnabBudgetCategory

class YnabBudgetMonth( monthJsonObject: JsonObject ) {
    var date = ""
    var categories: MutableList<YnabBudgetCategory> = mutableListOf()

    init {
        date = monthJsonObject.getString( "month" )

        for ( categoryJsonObject in monthJsonObject.getArray("categories") ) {
            addCategory( categoryJsonObject )
        }

    }

    private fun addCategory(jsonObject: JsonObject) {
        categories.add( YnabBudgetCategory( jsonObject ) )
    }

}