import io.restassured.RestAssured
import io.restassured.http.ContentType
import io.restassured.response.Response
import models.PersonModel

import static io.restassured.RestAssured.*
import static org.hamcrest.Matchers.*

import java.time.LocalDate

class PostPersonSpec extends BaseSpecification {
    def "User can create new person record with valid data"() {
        given: "Correct valid input data"
        when: "User sends POST request to /api/person"
        then: "User should receive 200 status"
        Response response = given().contentType(ContentType.JSON).body(person).post()
        expect:
        response.then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("id", not(empty()))
                .body("personalCode", not(empty()))
                .body("country", not(empty()))
                .body("firstName", not(empty()))
                .body("lastName", not(empty()))
                .body("gender", not(empty()))
                .body("dateOfBirth", not(empty()))
                .body("dateOfDeath", nullValue())
        where:
        person << PersonModel.generateRandomValidPersonJsonList(5)
    }

    def "User should receive 422 error when submitting invalid input data"() {
        given: "Invalid input data"
        when: "User sends POST request to /api/person"
        then: "User should receive 422 response with error message"
        Response response = given().contentType(ContentType.JSON).body(person.toJson()).post()
        expect:
        response.then()
                .assertThat()
                .statusCode(HttpStatus.INVALID_REQUEST.value())
                .body("error", is(HttpStatus.INVALID_REQUEST.name()))
                .body("errorMessage", is("Unable to process the request."))

        where:
        person << [
                // Missing country
                new PersonModel(
                        personalCode: 39608222719,
                        firstName: "Mihhail",
                        lastName: "Matisinets",
                        gender: "M",
                        dateOfBirth: LocalDate.of(1996, 8, 22)
                ),
                // Missing personalCode
                new PersonModel(
                        country: "EE",
                        firstName: "Misha",
                        lastName: "Matis",
                        gender: "F",
                        dateOfBirth: LocalDate.of(1995, 1, 1)
                ),
                // Missing first name
                new PersonModel(
                        personalCode: 59512222739,
                        country: "LV",
                        lastName: "Matis",
                        gender: "M",
                        dateOfBirth: LocalDate.of(1995, 1, 31)
                ),
                // Missing last name
                new PersonModel(
                        personalCode: 59512222739,
                        country: "LV",
                        firstName: "Misha",
                        gender: "F",
                        dateOfBirth: LocalDate.of(1995, 1, 31)
                ),
                // Missing birthdate field
                new PersonModel(
                        personalCode: 39608222719,
                        firstName: "Mihhail",
                        lastName: "Matisinets",
                        gender: "M",
                ),
                // Missing multiple fields
                new PersonModel(
                        personalCode: 59512222739,
                        country: "LV",
                        firstName: "Misha",
                ),
                // Empty object
                new PersonModel()
        ]
    }

    def "User should get 500 error when submitting POST request to invalid endpoint"() {
        given: "Invalid endpoint"
        when: "User sends POST request to invalid endpoint"
        RestAssured.basePath = "/shop"
        PersonModel person = PersonModel.generateRandomValidPerson()
        Response response = given().contentType(ContentType.JSON).body(person.toJson()).post()
        then: "User should receive 500 error"
        expect:
        response
                .then()
                .assertThat()
                .statusCode(500)
                .body("error", is(HttpStatus.SERVER_ERROR.name()))
    }

    def "User should get 500 error when submitting POST request with invalid content type"() {
        given: "Invalid content-type value"
        when: "User sends POST request with invalid content-type"
        PersonModel person = PersonModel.generateRandomValidPerson()
        Response response = given().contentType(ContentType.TEXT).body(person.toJson()).post()
        then: "User should receive 500 error"
        expect:
        response
                .then()
                .assertThat()
                .statusCode(500)
                .body("error", is(HttpStatus.SERVER_ERROR.name()))
    }
}
