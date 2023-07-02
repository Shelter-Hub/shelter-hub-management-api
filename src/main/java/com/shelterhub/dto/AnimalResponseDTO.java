package com.shelterhub.dto;

import com.shelterhub.domain.enums.Gender;
import com.shelterhub.domain.enums.Size;
import com.shelterhub.domain.model.Animal;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AnimalResponseDTO {
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
