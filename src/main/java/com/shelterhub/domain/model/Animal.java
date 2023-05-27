package com.shelterhub.domain.model;

import com.shelterhub.domain.enums.Gender;
import com.shelterhub.domain.enums.Size;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

/** TODO CRUD Animal */

// Criar a camada de Controller para Animal:
//
//- POST para criar animal
//- PUT para editar um animal
//- DELETE para deletar um animal
//- GET para pegar 1 animal
//- GET para pegar todos os animais

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
    private UUID medical_record_id;
    private String animal_type;
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