package com.shelterhub.service;

import com.shelterhub.database.AnimalRepository;
import com.shelterhub.domain.model.Animal;
import com.shelterhub.dto.AnimalDTO;
import com.shelterhub.dto.AnimalResponseDTO;
import com.shelterhub.exception.PersistenceFailedException;
import com.shelterhub.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AnimalService {

    @Autowired
    private AnimalRepository animalRepository;

    public AnimalResponseDTO create(AnimalDTO animalDTO){
        try {
            var animalToBePersisted = animalDTO.toAnimal();
            var createdAnimal = animalRepository.save(animalToBePersisted);

            return createdAnimal.toResponse();
        } catch (Exception ex) {
            throw new PersistenceFailedException(ex.getLocalizedMessage());
        }
    }

    public AnimalResponseDTO updateById(AnimalDTO animalDTO, UUID animalId) {
        Optional<Animal> animal = animalRepository.findById(animalId);
        getAnimalIfPresent(animal);

        Animal animalEntity = animalDTO.toAnimal();
        var updatedAnimal = animalRepository.save(animalEntity);
        return updatedAnimal.toResponse();
    }

    public List<AnimalResponseDTO> getAllAnimals() {
        List<AnimalResponseDTO> animals = new ArrayList<>();
        var animalEntities = animalRepository.findAll();

        animalEntities.forEach( animal ->
                animals.add(animal.toResponse())
        );

        return animals;
    }

    public AnimalResponseDTO getAnimalById(UUID animalId) {
        var animal = animalRepository
                .findById(animalId);

        return getAnimalIfPresent(animal).toResponse();
    }

    public AnimalResponseDTO delete(UUID animalId) {
        Optional<Animal> animalOrNull = animalRepository.findById(animalId);
        var animal = getAnimalIfPresent(animalOrNull);
        animalRepository.deleteById(animalId);
        return animal.toResponse();
    }

    private Animal getAnimalIfPresent(Optional<Animal> animal) {
        return animal.orElseThrow(ResourceNotFoundException::new);
    }
}
