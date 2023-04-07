package com.archersland.shelterhub.domain;

import jakarta.persistence.*;

import java.util.List;
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
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false)
    private String name;
    private int age;
    private int medical_record_id;
    private String animal_type;
    @ElementCollection
    private List<String> disabilities;
    @ElementCollection
    private List<String> observations;

    public Animal(UUID id, int age, int medical_record_id, String name, String animal_type, List<String> disabilities, List<String> observations) {
        this.id = id;
        this.age = age;
        this.medical_record_id = medical_record_id;
        this.name = name;
        this.animal_type = animal_type;
        this.disabilities = disabilities;
        this.observations = observations;
    }

    public Animal() {

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getMedical_record_id() {
        return medical_record_id;
    }

    public void setMedical_record_id(int medical_record_id) {
        this.medical_record_id = medical_record_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAnimal_type() {
        return animal_type;
    }

    public void setAnimal_type(String animal_type) {
        this.animal_type = animal_type;
    }

    public List<String> getDisabilities() {
        return disabilities;
    }

    public void setDisabilities(List<String> disabilities) {
        this.disabilities = disabilities;
    }

    public List<String> getObservations() {
        return observations;
    }

    public void setObservations(List<String> observations) {
        this.observations = observations;
    }

}