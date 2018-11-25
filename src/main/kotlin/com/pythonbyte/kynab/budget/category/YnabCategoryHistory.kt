package com.pythonbyte.kynab.budget.category

import com.pythonbyte.kynab.base.YnabObject
import com.pythonbyte.kynab.budget.YnabBudgetMonth

class YnabCategoryHistory : YnabObject() {
    val items: MutableList<YnabCategoryHistoryItem> = mutableListOf()

    fun addItem(date: String?, activity: Int) {
        if(date == null) {
            throw Exception("Category history [$name] is missing date")
        }

        items.add(YnabCategoryHistoryItem(date, activity))
    }

    fun addItem(ynabBudgetMonth: YnabBudgetMonth?, category: YnabBudgetCategory) {
        addItem(ynabBudgetMonth?.date, category.activity)
    }

    fun initialize(category: YnabBudgetCategory) {
        name = category.name
        ynabId = category.ynabId
    }
}