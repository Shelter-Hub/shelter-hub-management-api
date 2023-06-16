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
public class AnimalService {

    @Autowired
    private AnimalRepository animalRepository;

    public AnimalDTO create (AnimalDTO animalDTO){
        Animal animal = new Animal();
        animal.setName(animalDTO.getName());
        animal.setAge(animalDTO.getAge());
        animal.setAnimalType(animalDTO.getAnimalType());
        animal.setMedicalRecordId(animalDTO.getMedicalRecordId());
        animalRepository.save(animal);
        animalDTO.setId(animal.getId());
        return animalDTO;
    }

    public AnimalDTO update (AnimalDTO animalDTO, UUID animalId){
        Animal animal = animalRepository.getReferenceById(animalId);
        Animal animalToBePersisted = AnimalDTO.toAnimal(animalDTO);

        animalRepository.save(animal);
        return animalDTO;
    }

    public List<AnimalDTO> getAllAnimals () {
        return animalRepository
                .findAll()
                .stream()
                .map(Animal::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<AnimalDTO> getAnimalById (UUID animalId) {
        return animalRepository
                .findById(animalId)
                .map(Animal::toDTO);
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
}
