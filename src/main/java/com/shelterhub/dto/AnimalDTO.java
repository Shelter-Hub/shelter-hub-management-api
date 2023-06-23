package com.shelterhub.dto;

import com.shelterhub.domain.enums.Gender;
import com.shelterhub.domain.enums.Size;
import com.shelterhub.domain.model.Animal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    public static Animal toAnimal(AnimalDTO animalDTO) {
        return Animal.builder()
            .id(animalDTO.getId())
            .name(animalDTO.getName())
            .identification(animalDTO.getIdentification())
            .age(animalDTO.getAge())
            .medicalRecordId(animalDTO.getMedicalRecordId())
            .animalType(animalDTO.getAnimalType())
            .behavior(animalDTO.getBehavior())
            .breed(animalDTO.getBreed())
            .history(animalDTO.getHistory())
            .gender(animalDTO.getGender())
            .size(animalDTO.getSize())
            .build();
    }

    public static Animal toAnimalWithSameAnimalId(AnimalDTO animalDTO, UUID animalId) {
        return Animal.builder()
                .id(animalId)
                .name(animalDTO.getName())
                .identification(animalDTO.getIdentification())
                .age(animalDTO.getAge())
                .medicalRecordId(animalDTO.getMedicalRecordId())
                .animalType(animalDTO.getAnimalType())
                .behavior(animalDTO.getBehavior())
                .breed(animalDTO.getBreed())
                .history(animalDTO.getHistory())
                .gender(animalDTO.getGender())
                .size(animalDTO.getSize())
                .build();
    }
}


