package com.shelterhub.utils

import br.com.shelterhubmanagementapi.domain.enums.AnimalType
import br.com.shelterhubmanagementapi.domain.enums.Gender
import br.com.shelterhubmanagementapi.domain.enums.Size
import br.com.shelterhubmanagementapi.dto.request.AnimalRequest
import br.com.shelterhubmanagementapi.dto.request.EstimatedAgeRequest
import net.datafaker.Faker
import java.util.UUID

object AnimalTestUtils {
    fun buildAnimalDTO(): AnimalRequest {
        val faker = Faker()

        return AnimalRequest(
            name = faker.dog().name(),
            identification = faker.dog().memePhrase(),
            behavior = faker.dog().memePhrase(),
            breed = faker.dog().breed(),
            history = faker.dog().memePhrase(),
            estimatedAge = EstimatedAgeRequest(years = 2020, months = 5, days = 20),
            medicalRecordId = UUID.randomUUID(),
            animalType = AnimalType.Canine.toString(),
            gender = Gender.FEMALE,
            size = Size.SMALL
        )
    }

    fun buildAnimalDTO(animalType: String?): AnimalRequest {
        val faker = Faker()

        return AnimalRequest(
            name = faker.dog().name(),
            identification = faker.dog().memePhrase(),
            behavior = faker.dog().memePhrase(),
            breed = faker.dog().breed(),
            history = faker.dog().memePhrase(),
            estimatedAge = EstimatedAgeRequest(years = 2, months = 5, days = 20),
            medicalRecordId = UUID.randomUUID(),
            animalType = AnimalType.Canine.toString(),
            gender = Gender.FEMALE,
            size = Size.SMALL
        )
    }
}
