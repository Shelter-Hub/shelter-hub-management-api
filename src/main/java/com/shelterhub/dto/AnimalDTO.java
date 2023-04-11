package com.shelterhub.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class AnimalDTO {
    private UUID id;
    private String name;
    private Short age;
    private UUID medical_record_id;
    private String animal_type;
}
