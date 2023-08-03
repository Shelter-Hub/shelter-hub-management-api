package com.shelterhub.utils;

import com.github.javafaker.Faker;
import com.shelterhub.domain.enums.Gender;
import com.shelterhub.domain.enums.Size;
import com.shelterhub.dto.AnimalDTO;

import java.util.UUID;

public class AnimalUtils {
    public static AnimalDTO buildAnimalDTO(boolean hasId) {
        Faker faker = new Faker();

        var animalBuilder = AnimalDTO.builder();

        if(hasId) {
            animalBuilder.id(UUID.randomUUID());
        }

        return animalBuilder
                .name(faker.dog().name())
                //.age((short) faker.number().numberBetween(1, 20))
               //refatorar com estimatedAgeDTO retornando uma LocalDate fake
                .animalType(faker.animal().name())
                .behavior(faker.dog().memePhrase())
                .breed(faker.dog().breed())
                .identification(faker.dog().memePhrase())
                .history(faker.dog().memePhrase())
                .gender(Gender.FEMALE)
                .size(Size.SMALL)
                .build();
    }
}
