package com.shelterhub.dto;

import com.shelterhub.domain.enums.Gender;
import com.shelterhub.domain.enums.Size;
import lombok.Data;

import java.util.UUID;

@Data
public class AnimalDTO {
    private UUID id;
    private String name;
    private String identification;
    private Short age;
    private UUID medical_record_id;
    private String animal_type;
    private String behavior;
    private String breed;
    private String history;
    private Gender gender;
    private Size size;
}
