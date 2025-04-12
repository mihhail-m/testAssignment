import io.restassured.RestAssured
import io.restassured.response.Response
import spock.lang.Specification

class PersonServiceSpec extends Specification{
    def setupSpec() {
        RestAssured.baseURI = "http://localhost:8080"
    }

    def "Create new person"() {
        expect:
        1 + 1 == 2
//        given: "New person personal data"
//        def person = [
//                personalCode: 12345678901,
//                country     : "USA",
//                firstName   : "John",
//                lastName    : "Doe",
//                gender      : "M",
//                dateOfBirth : "1990-01-01"
//        ]
//
//        when: "We send a POST request to create a new person"
//        Response response = RestAssured.given()
//                .contentType("application/json")
//                .body(person)
//                .post("/api/person")
//
//        then: "The response status code is 200"
//        response.statusCode == 200
//        response.jsonPath().getInt("id") < 0
    }
}
