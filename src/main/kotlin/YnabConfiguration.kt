import org.yaml.snakeyaml.Yaml
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

class YnabConfiguration() {
    val TESTING_ACCESS_TOKEN_PATH = "personal_access_token.yaml"
    val TESTING_ACCESS_TOKEN = loadTestingAccessToken()
    val YNAB_BASE_URL = "https://api.youneedabudget.com/v1"

    private fun loadTestingAccessToken(): String {
        val yaml = Yaml()
        val config = yaml.loadAs( Files.newInputStream( Paths.get( TESTING_ACCESS_TOKEN_PATH ) ), Properties::class.java )

        return  config.getProperty("token")
    }

    fun getUrl( endpointName : String ) : String {1
        return YNAB_BASE_URL + "/" + endpointName + "?access_token=" + TESTING_ACCESS_TOKEN
    }
}