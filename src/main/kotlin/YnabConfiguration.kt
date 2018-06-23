import org.yaml.snakeyaml.Yaml
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

class YnabConfiguration() {
    val config = Yaml().loadAs( Files.newInputStream( Paths.get( "config.yaml" ) ), Properties::class.java )

    fun getUrl( endpointName : String ) : String {
        return config.getProperty( "ynab_base_url" ) + "/" + endpointName + "?access_token=" + config.getProperty( "personal_access_token" )
    }
}