package com.shelterhub.utils;

import br.com.shelterhubmanagementapi.domain.enums.AnimalType;
import br.com.shelterhubmanagementapi.domain.enums.Gender;
import br.com.shelterhubmanagementapi.domain.enums.Size;
import br.com.shelterhubmanagementapi.dto.request.AnimalRequest;
import br.com.shelterhubmanagementapi.dto.request.EstimatedAgeRequest;
import net.datafaker.Faker;

import java.util.UUID;

public class AnimalTestUtils {

    public static AnimalRequest buildAnimalDTO(boolean hasId) {
        Faker faker = new Faker();

        var animalBuilder = AnimalRequest.builder();

        if (hasId) {
            animalBuilder.id(UUID.randomUUID());
        }

        return animalBuilder
                .name(faker.dog().name())
                .identification(faker.dog().memePhrase())
                .behavior(faker.dog().memePhrase())
                .breed(faker.dog().breed())
                .history(faker.dog().memePhrase())
                .estimatedAge(EstimatedAgeRequest.builder().years(2).months(5).days(20).build())
                .medicalRecordId(UUID.randomUUID())
                .animalType(AnimalType.Canine.toString())
                .gender(Gender.FEMALE)
                .size(Size.SMALL)
                .build();
    }

    public static AnimalRequest buildAnimalDTO(boolean hasId, String animalType) {
        Faker faker = new Faker();

        var animalBuilder = AnimalRequest.builder();

        if (hasId) {
            animalBuilder.id(UUID.randomUUID());
        }

        return animalBuilder
                .name(faker.dog().name())
                .estimatedAge(EstimatedAgeRequest.builder().years(2).months(5).days(20).build())
                .animalType(animalType)
                .behavior(faker.dog().memePhrase())
                .breed(faker.dog().breed())
                .identification(faker.dog().memePhrase())
                .history(faker.dog().memePhrase())
                .gender(Gender.FEMALE)
                .size(Size.SMALL)
                .build();
    }
}
