import io.restassured.RestAssured
import spock.lang.Specification


/*
 * All common config for spec files goes here
 */

abstract class BaseSpecification extends Specification {
    def setupSpec() {
        String propertiesPath = "src/test/env.properties"
        Properties properties = new Properties()
        File propsFile = new File(propertiesPath)

        propsFile.withInputStream {
            properties.load(it)
        }

        RestAssured.baseURI = "http://${properties.get("host")}"
        RestAssured.port = properties.get("port").toString().toInteger()
        RestAssured.basePath = properties.get("baseEndpoint")
        // For debugging
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails()
    }
}
