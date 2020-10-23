# kYNAB
A Kotlin interface to the YNAB (You Need A Budget) API.

### Urls

* YNAB's API Website: https://api.youneedabudget.com/
* Generating a Personal Access Token: https://api.youneedabudget.com/#authentication-overview

### Getting Started

In order to begin, you will need a [Personal Access Token](https://api.youneedabudget.com/#authentication-overview)
                   
**!! DO NOT COMMIT YOUR PERSONAL ACCESS TOKEN TO GIT !!**

### The com.pythonbyte.kynab.YnabBroker API

```
interface com.pythonbyte.kynab.YnabBroker {
    fun getBudgetsPartiallyLoaded() : MutableList<YnabBudget>

    fun getBudgetById( ynabId: String ): YnabBudget

    fun budgetRequiresRefresh(com.pythonbyte.budget: YnabBudget ): Boolean

    fun getRefreshedBudget(staleBudget: YnabBudget ): YnabBudget

    fun getBudgetByName( name: String ): YnabBudget

    fun getAccounts( budgetYnabId: String ) : MutableList<YnabAccount>

    fun getAccount( budgetYnabId: String, accountYnabId: String ) : YnabAccount

    fun getOverSpentCategories(budgetYnabId: String, month: String): List<YnabBudgetCategory>

    fun getCategoryHistory( budgetYnabId: String, categoryYnabId: String): YnabCategoryHistory

    fun createTransaction(ynabBudgetId: String, transaction: YnabTransaction): YnabTransaction

    fun getTransaction(  budgetYnabId: String, transactionYnabId : String ) : YnabTransaction

    fun getTransactions( budgetYnabId : String ) : List<YnabTransaction>

    fun getTransactionsByMemo( budgetYnabId: String, memoText: String): List<YnabTransaction>

    fun getPayees( budgetYnabId : String ) : List<YnabPayee>

    fun getPayee(budgetYnabId: String, payeeYnabId: String): YnabPayee
}
```
