import java.io.File
import java.io.FileInputStream
import java.util.Properties

object ApiConfig {

    private const val API_KEY = "API_KEY"

    val apiKey: String
        get() = try {
            apiProperties().getProperty(API_KEY)
        } catch (e: Exception) {
            System.getenv()[API_KEY]!!
        }

    private fun apiProperties(): Properties {
        val filename = "api.properties"
        val file = File(filename)
        if (!file.exists()) {
            throw Error(
                "You need to prepare a $filename file in the project root directory"
            )
        }
        val properties = Properties()
        properties.load(FileInputStream(file))
        return properties
    }
}

