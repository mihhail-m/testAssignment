package models

import groovy.json.JsonOutput
import groovy.transform.builder.Builder
import net.datafaker.Faker

import java.time.LocalDate

/**
 * Represents a Person entity using Builder pattern for flexibility.
 */
@Builder
class PersonModel {
    Long id // id generated by the "backend"
    Long personalCode
    String country
    String firstName
    String lastName
    String gender
    LocalDate dateOfBirth
    LocalDate dateOfDeath

    String toJson() {
        return JsonOutput.toJson([
                id          : id,
                personalCode: personalCode,
                country     : country,
                firstName   : firstName,
                lastName    : lastName,
                gender      : gender,
                dateOfBirth : dateOfBirth?.toString(),
                dateOfDeath : dateOfDeath?.toString()
        ])
    }

    static PersonModel generateRandomValidPerson() {
        Faker faker = new Faker()
        def gender = ['M', 'F']
        gender.shuffle()

        return new PersonModel(
                firstName: faker.name().firstName(),
                lastName: faker.name().lastName(),
                country: faker.country().countryCode3(),
                gender: gender.take(1).first(),
                dateOfBirth: LocalDate.of(
                        faker.number().numberBetween(1900, 2025),
                        faker.number().numberBetween(1, 12),
                        faker.number().numberBetween(1, 28)),
                personalCode: faker.expression(
                        // Generates random valid ID based on the given regex
                        // This is regex for validating Estonian's unique IDs
                        "#{regexify '^[1-6]{1}[0-9]{2}(0[1-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[0-1])[0-9]{4}\$'}")
                        .toLong()
        )
    }

    static List<PersonModel> generateRandomValidPersonJsonList(int length) {
        Faker faker = new Faker()
        return faker.collection(
                () -> generateRandomValidPerson().toJson())
                .maxLen(length)
                .generate()
    }
}
