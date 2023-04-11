package com.shelterhub.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class MedicalRecordDTO {
    private UUID id;
    private UUID animal_id;
}
