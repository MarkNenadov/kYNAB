class YnabConfiguration( var personalAccessToken: String ) {
    val YNAB_BASE_URL = "https://api.youneedabudget.com/v1"

    fun getUrl( endpointName : String ) : String {1
        return YNAB_BASE_URL + "/" + endpointName + "?access_token=" + personalAccessToken
    }
}