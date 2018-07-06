import budget.YnabAccount
import budget.YnabBudget
import budget.YnabPayee
import budget.YnabTransaction
import budget.category.YnabBudgetCategory
import budget.category.YnabCategoryHistory

interface YnabBroker {
    fun getBudgetsPartiallyLoaded() : MutableList<YnabBudget>

    fun getBudgetById( ynabId: String ): YnabBudget

    fun budgetRequiresRefresh(budget: YnabBudget ): Boolean

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