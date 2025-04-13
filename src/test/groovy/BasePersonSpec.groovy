import io.restassured.RestAssured

/*
 * Person's endpoint specific configurations goes here.
 */
abstract class BasePersonSpec extends BaseSpecification {
    def setupSpec() {
        RestAssured.basePath += "/person"
    }
}
