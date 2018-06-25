# kYNAB
A Kotlin interface to the YNAB (You Need A Budget) API.

### Urls

* YNAB's API Website: https://api.youneedabudget.com/
* Generating a Personal Access Token: https://api.youneedabudget.com/#authentication-overview

### Getting Started

In order to begin, you must get a Personal Access Token from YNAB and then put it into config yaml (in the personal_access_token property)

### The YnabBroker API

```
interface YnabBroker {
    fun getBudgetSummaries() : MutableList<YnabBudgetSummary>
    fun getBudgetById( ynabId: String ): YnabBudget
    fun getBudgetByName( name: String ): YnabBudget
    fun getOverBudgetCategories( budgetYnabId: String): List<YnabBudgetCategory>
    fun getCategoryHistory( budgetYnabId: String, categoryYnabId: String ): YnabCategoryHistory
    fun getTransaction(  budgetYnabId: String, transactionYnabId : String ) : YnabTransaction
    fun getTransactions( budgetYnabId : String ) : List<YnabTransaction>
    fun getTransactionsByMemo( budgetYnabId: String, memoText: String ): List<YnabTransaction>
}
```
