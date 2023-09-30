package com.shelterhub.dto.request;

import com.shelterhub.domain.enums.AnimalType;
import com.shelterhub.domain.enums.Gender;
import com.shelterhub.domain.enums.Size;
import com.shelterhub.domain.model.Animal;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.EnumUtils;

import java.util.UUID;

@Data
@Builder
public class AnimalRequest {
    private UUID id;
    private String name;
    private String identification;
    private String behavior;
    private String breed;
    private String history;
    private EstimatedAgeRequest estimatedAge;
    private UUID medicalRecordId;
    private String animalType;
    private Gender gender;
    private Size size;

    public Animal toAnimal() {
        return Animal.builder()
                .id(getId())
                .name(getName())
                .identification(getIdentification())
                .estimatedAge(estimatedAge.toEstimatedAge())
                .medicalRecordId(getMedicalRecordId())
                .animalType(EnumUtils.getEnum(AnimalType.class, getAnimalType(), AnimalType.Unknown))
                .behavior(getBehavior())
                .breed(getBreed())
                .history(getHistory())
                .gender(getGender())
                .size(getSize())
                .build();
    }
}


