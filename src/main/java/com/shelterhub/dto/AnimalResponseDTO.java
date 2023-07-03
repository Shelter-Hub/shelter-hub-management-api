package com.shelterhub.dto;

import com.shelterhub.domain.enums.Gender;
import com.shelterhub.domain.enums.Size;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class AnimalResponseDTO {
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
}
