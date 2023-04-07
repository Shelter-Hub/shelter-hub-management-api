package com.archersland.shelterhub.domain;

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
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false)
    private String name;
    private Short age;
    private UUID medical_record_id;
    private String animal_type;

//    @ElementCollection
//    private List<String> disabilities;
//    @ElementCollection
//    private List<String> observations;

}