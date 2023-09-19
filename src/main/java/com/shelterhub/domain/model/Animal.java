package com.shelterhub.domain.model;

import com.shelterhub.domain.enums.AnimalType;
import com.shelterhub.domain.enums.Gender;
import com.shelterhub.domain.enums.Size;
import com.shelterhub.dto.AnimalResponseDTO;
import com.shelterhub.dto.EstimatedAgeDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
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

    public AnimalResponseDTO toResponse() {
        return AnimalResponseDTO.builder()
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