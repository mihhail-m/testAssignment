import io.restassured.RestAssured
import io.restassured.response.Response

class PersonServiceSpec extends BaseSpecification {
    private String endpoint = "/person"

    def "Get a person by ID"() {
        given: "An existing person ID"
        def personId = 1

        when: "We send a GET request to retrieve the person"
        Response response = RestAssured
                .given()
                .get("/${personId}")

        then: "The response status code is 200"
        response.statusCode == 200
        response.jsonPath().getInt("id") == personId
    }
}
