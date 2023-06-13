package com.shelterhub.service;

import com.shelterhub.database.AnimalRepository;
import com.shelterhub.domain.model.Animal;
import com.shelterhub.dto.AnimalDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AnimalFacade {
    @Autowired
    private AnimalRepository animalRepository;

    public AnimalDTO create (AnimalDTO animalDTO){
        Animal animal = new Animal();
        animal.setName(animalDTO.getName());
        animal.setAge(animalDTO.getAge());
        animal.setAnimal_type(animalDTO.getAnimal_type());
        animal.setMedical_record_id(animalDTO.getMedical_record_id());
        animalRepository.save(animal);
        animalDTO.setId(animal.getId());
        return animalDTO;
    }

    public AnimalDTO update (AnimalDTO animalDTO, UUID animalId){
        Animal animalInDb = animalRepository.getReferenceById(animalId);
        animalInDb.setName(animalDTO.getName());
        animalInDb.setAge(animalDTO.getAge());
        animalInDb.setAnimal_type(animalDTO.getAnimal_type());
        animalInDb.setMedical_record_id(animalDTO.getMedical_record_id());
        animalRepository.save(animalInDb);
        return animalDTO;
    }

    public List<AnimalDTO> getAllAnimals () {
        return animalRepository
                .findAll()
                .stream()
                .map(this::converter).collect(Collectors.toList());
    }

    public Optional<AnimalDTO> getAnimalById (UUID animalId) {
        return animalRepository
                .findById(animalId)
                .map(this::converter);
    }

    public String delete (UUID animalId) {
        Optional<Animal> animalInDb = animalRepository.findById(animalId);
        if (animalInDb.isEmpty()) {
            return "Animal not found";
        } else {
            animalRepository.deleteById(animalId);
            return "Animal " + animalId + " was deleted successfully";
        }
    }

    private AnimalDTO converter (Animal animal) {
        AnimalDTO result = new AnimalDTO();
        result.setId(animal.getId());
        result.setName(animal.getName());
        result.setAge(animal.getAge());
        result.setAnimal_type(animal.getAnimal_type());
        result.setMedical_record_id(animal.getMedical_record_id());
        return result;
    }
}
