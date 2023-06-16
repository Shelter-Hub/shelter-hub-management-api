package com.shelterhub.utils;

import com.github.javafaker.Faker;
import com.shelterhub.domain.enums.Gender;
import com.shelterhub.domain.enums.Size;
import com.shelterhub.dto.AnimalDTO;

import java.util.UUID;

public class AnimalUtils {
    public static AnimalDTO buildAnimalDTO(boolean hasId) {
        Faker faker = new Faker();
        AnimalDTO animal = new AnimalDTO();
        if(hasId) {
            animal.setId(UUID.randomUUID());
        }
        animal.setName(faker.dog().name());
        animal.setAge((short) faker.number().numberBetween(1, 20));
        animal.setAnimalType(faker.animal().name());
        animal.setBehavior(faker.dog().memePhrase());
        animal.setBreed(faker.dog().breed());
        animal.setIdentification(faker.dog().memePhrase());
        animal.setHistory(faker.dog().memePhrase());
        animal.setGender(Gender.FEMALE);
        animal.setSize(Size.SMALL);
        return animal;
    }
}
