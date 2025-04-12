import io.restassured.response.Response

import static io.restassured.RestAssured.*
import static org.hamcrest.Matchers.*

class GetPersonSpec extends BaseSpecification {
    def "Get a person by valid ID"() {
        given: "An existing valid person ID"
        when: "We send a GET request to retrieve the person"
        Response response = given()
                .get("/${personId}")

        then: "The response status code is 200 and all fields should not be empty"
        response.then().assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("id", not(empty()))
                .body("country", not(empty()))
                .body("personalCode", not(empty()))
                .body("firstName", not(empty()))
                .body("lastName", not(empty()))
                .body("gender", not(empty()))
                .body("dateOfBirth", not(empty()))
                .body("dateOfDeath", nullValue())
        where:
        personId << [
            39608222719,
            49912121111,
            19812033333
        ]
    }

    def "Get 404 response for invalid ID"() {
        given: "An invalid person ID"
        when: "User send GET request with invalid ID value"
        Response response = given().get("/${invalidPersonId}")
        then: "The response should return 404 status"
        expect:
        response.then().assertThat().statusCode(HttpStatus.NOT_FOUND.value())
                .body("error", is(HttpStatus.NOT_FOUND.name()))
                .body("errorMessage", is("Person not found."))
        where:
        invalidPersonId << [
            123,
            1,
            -1,
            79912311234, // starts with invalid number
            19913311234, // invalid month value
            19900111234,
            19912333234, // invalid day value
            2991231123, // 3 digits for serial number
        ]
    }

    // TODO
    def "Get 500 error status for wrong endpoint"() {}
}
