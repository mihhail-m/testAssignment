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

    def "User should receive error when submitting invalid input data"() {
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
}
