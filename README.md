# kYNAB
A Kotlin interface to the YNAB (You Need A Budget) API.

### Urls

* YNAB's API Website: https://api.youneedabudget.com/
* Generating a Personal Access Token: https://api.youneedabudget.com/#authentication-overview

### Getting Started

Add kYNAB as a [Gradle source dependency](https://blog.gradle.org/introducing-source-dependencies):

settings.gradle
```groovy
sourceControl {
    gitRepository("https://github.com/MarkNenadov/kYNAB.git") {
        producesModule("com.pythonbyte:kYNAB")
    }
}
```

build.gradle
```groovy
implementation('com.pythonbyte:kYNAB') {
    version {
        branch = 'master'
    }
}
```


### Development

For testing, fill out the `src/test/resources/propterties.yml` file with values matching your budget.
You will need a [Personal Access Token](https://api.youneedabudget.com/#authentication-overview)

```yaml
# For testing, put your token here:
accessToken: access token here

# For testing, replace these testing variables with values for your budget:
testingBudgetId: 716692c6-55db-4b79-b11d-48efa0ca1f23
testingBudgetName: TESTING
testingCategoryId: ed953aad-02d4-4d1f-9524-ae5af699a132
testingTransactionId: 96cc9028-21ad-4f7f-b575-72612ceb311e
testingPayeeId: e2f5814d-431c-47ec-8101-c3da519257f9
testingAccoiuntId: b0b3e9ba-4f2c-44b8-91d5-27815ae86fed
```

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
