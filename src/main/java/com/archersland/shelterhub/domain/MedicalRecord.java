package com.archersland.shelterhub.domain;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
public class MedicalRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private int animal_id;
    private String description;
    private String diagnosis;

    @ElementCollection
    private List<String> medications;
    @ElementCollection
    private List<String> treatments;
    @ElementCollection
    private List<String> vaccinations;

    public MedicalRecord(UUID id, String description, String diagnosis, List<String> medications, List<String> treatments, List<String> vaccinations) {
        this.id = id;
        this.description = description;
        this.diagnosis = diagnosis;
        this.medications = medications;
        this.treatments = treatments;
        this.vaccinations = vaccinations;
    }

    public MedicalRecord() {

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getAnimal_id() {
        return animal_id;
    }

    public void setAnimal_id(int animal_id) {
        this.animal_id = animal_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public List<String> getMedications() {
        return medications;
    }

    public void setMedications(List<String> medications) {
        this.medications = medications;
    }

    public List<String> getTreatments() {
        return treatments;
    }

    public void setTreatments(List<String> treatments) {
        this.treatments = treatments;
    }

    public List<String> getVaccinations() {
        return vaccinations;
    }

    public void setVaccinations(List<String> vaccinations) {
        this.vaccinations = vaccinations;
    }
}
