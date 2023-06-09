package com.shelterhub.domain.model;

import com.shelterhub.domain.enums.Gender;
import com.shelterhub.domain.enums.Size;
import jakarta.persistence.*;
import lombok.Data;
import java.util.UUID;
@Entity
@Data
@Table(name = "animal")
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

//    @ElementCollection
//    private List<String> disabilities;
//    @ElementCollection
//    private List<String> observations;

}