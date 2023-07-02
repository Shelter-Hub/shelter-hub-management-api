package com.shelterhub.dto;

import com.shelterhub.domain.enums.Gender;
import com.shelterhub.domain.enums.Size;
import com.shelterhub.domain.model.Animal;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class AnimalDTO {
    private UUID id;
    private String name;
    private String identification;
    private Short age;
    private UUID medicalRecordId;
    private String animalType;
    private String behavior;
    private String breed;
    private String history;
    private Gender gender;
    private Size size;

    public Animal toAnimal() {
        return Animal.builder()
            .id(getId())
            .name(getName())
            .identification(getIdentification())
            .age(getAge())
            .medicalRecordId(getMedicalRecordId())
            .animalType(getAnimalType())
            .behavior(getBehavior())
            .breed(getBreed())
            .history(getHistory())
            .gender(getGender())
            .size(getSize())
            .build();
    }
}


