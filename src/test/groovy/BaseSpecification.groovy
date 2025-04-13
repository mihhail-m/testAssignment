import io.restassured.RestAssured
import spock.lang.Specification


/*
 * All common config for spec files goes here
 */

abstract class BaseSpecification extends Specification {
    def setupSpec() {
        RestAssured.baseURI = "http://localhost"
        RestAssured.port = 8080
        RestAssured.basePath = "/api/person"

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails()
    }
}
