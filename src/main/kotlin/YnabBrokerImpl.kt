import base.JsonObject
import base.YnabResponse
import budget.*
import budget.category.YnabBudgetCategory
import budget.category.YnabCategoryHistory
import khttp.get

class YnabBrokerImpl(var configuration: YnabConfiguration ) : YnabBroker {
    override fun getBudgetSummaries() : MutableList<YnabBudgetSummary> {
        val result : MutableList<YnabBudgetSummary> = mutableListOf()

        val dataList = getDataFromYnab( "budgets" )

        dataList.forEach{ result.add( YnabBudgetSummary( it ) ) }

        return result
    }

    override fun getBudgetById(ynabId: String ): YnabBudget {
        val responseData : JsonObject? = getFromYnab("budgets/" + ynabId ).data

        if ( responseData == null ) {
            throw Exception( "Can't find data for budget" )
        }

        val result = responseData.getObject( "budget" )

        return YnabBudget( result )
    }

    override fun getBudgetByName(name: String): YnabBudget {
        var budgetId = "";
        for(budgetSummary in getBudgetSummaries()) {
            if ( budgetSummary.name == name ) {
                budgetId = budgetSummary.ynabId
            }
        }

        return getBudgetById( budgetId )
    }


    override fun getOverBudgetCategories( budgetYnabId: String, month: String ): List<YnabBudgetCategory> {
        val budget = getBudgetById( budgetYnabId )

        val matchingBudgetMonths = budget.budgetMonths.filter { budgetMonth -> budgetMonth.date == getMonthAsFullDate( month ) }

        if ( matchingBudgetMonths.size == 0 ) {
            throw Exception( "Couldn't find a budgetMonth matching $month on budget [$budgetYnabId]" )
        } else if ( matchingBudgetMonths.size > 1 ) {
            throw Exception( "Strange! Couldn't find a unique budgetMonth matching $month on budget [$budgetYnabId]" )
        }

        return matchingBudgetMonths[0].categories.filter { category -> category.isOverBudget() }
    }

    private fun getMonthAsFullDate( month: String ): String {
        return month + "-01"
    }

    override fun getCategoryHistory(budgetYnabId: String, categoryYnabId: String): YnabCategoryHistory {
        val categoryHistory = YnabCategoryHistory()

        val budget = getBudgetById( budgetYnabId )

        for ( category in budget.getCategoriesForAllMonths() ) {
            if ( category.ynabId == categoryYnabId ) {
                if ( categoryHistory.name == "" ) {
                    categoryHistory.initialize( category )
                }

                categoryHistory.addItem( category.referenceBudgetMonth, category )
            }

        }

        return categoryHistory
    }

    override fun getTransactions(budgetYnabId: String): List<YnabTransaction> {
        var result = mutableListOf<YnabTransaction>()

        var responseData = getFromYnab( "budgets/" + budgetYnabId + "/transactions" ).data

        responseData?.getArray( "transactions" )?.forEach { result.add( YnabTransaction( it ) ) }

        return result;
    }

    override fun getTransaction(budgetYnabId: String, transactionYnabId: String): YnabTransaction {
        var responseData = getFromYnab( "budgets/" + budgetYnabId + "/transactions/" + transactionYnabId ).data

        if ( responseData != null ) {
            return YnabTransaction(responseData.getObject("transaction"))
        } else {
            throw Exception( "Transaction [$transactionYnabId] not found.")
        }
    }

    override fun getTransactionsByMemo(budgetYnabId: String, memoText: String): List<YnabTransaction> {
        val matchingTransactions : MutableList<YnabTransaction> = mutableListOf()

        val budget = getBudgetById( budgetYnabId )

        for( currentTransaction in budget.transactions ) {
            if ( currentTransaction.memoContains( memoText ) ) {
                matchingTransactions.add( currentTransaction )
            }
        }

        return matchingTransactions
    }

    private fun getDataFromYnab( endpointName: String ): List<JsonObject> {
        val responseData = getFromYnab( endpointName ).data

        if ( responseData == null ) {
            throw Exception( "Can't find data for $endpointName" )
        }

        return responseData.getArray( endpointName )
    }

    private fun getFromYnab( endpointName: String ): YnabResponse {
        val response = YnabResponse(get(url = configuration.getUrl(endpointName)))

        if ( response.hasError() ) {
            print( response.errors[0].toString() )
            throw Exception( "Error connecting to YNAB $endpointName endpoint ${response.errors[0].toString()}]" )
        }

        return response
    }
}