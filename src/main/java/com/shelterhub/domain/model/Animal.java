package com.shelterhub.domain.model;

import com.shelterhub.domain.enums.AnimalType;
import com.shelterhub.domain.enums.Gender;
import com.shelterhub.domain.enums.Size;
import com.shelterhub.dto.response.AnimalResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
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
    private String behavior;
    private String breed;
    private String history;
    private LocalDate estimatedAge;
    private UUID medicalRecordId;
    private AnimalType animalType;
    private Gender gender;
    private Size size;

    public AnimalResponse toResponse() {
        return AnimalResponse.builder()
                .id(getId())
                .name(getName())
                .identification(getIdentification())
                .behavior(getBehavior())
                .breed(getBreed())
                .history(getHistory())
                .estimatedAge(getEstimatedAge())
                .medicalRecordId(getMedicalRecordId())
                .animalType(String.valueOf(getAnimalType()))
                .gender(getGender())
                .size(getSize())
                .build();
    }
}