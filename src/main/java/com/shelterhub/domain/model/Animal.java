package com.shelterhub.domain.model;

import com.shelterhub.domain.enums.Gender;
import com.shelterhub.domain.enums.Size;
import com.shelterhub.dto.AnimalDTO;
import com.shelterhub.dto.AnimalResponseDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Column;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false)
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

    public AnimalDTO toDTO() {
        return AnimalDTO.builder()
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

    public AnimalResponseDTO toResponse() {
        return AnimalResponseDTO.builder()
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