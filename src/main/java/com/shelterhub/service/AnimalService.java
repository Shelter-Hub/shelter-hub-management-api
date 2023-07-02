package com.shelterhub.service;

import com.shelterhub.database.AnimalRepository;
import com.shelterhub.domain.model.Animal;
import com.shelterhub.dto.AnimalDTO;
import com.shelterhub.exception.PersistenceFailedException;
import com.shelterhub.exception.ResourceNotFoundException;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AnimalService {

    @Autowired
    private AnimalRepository animalRepository;

    public AnimalDTO create(AnimalDTO animalDTO) {
        try {
            var animalToBePersisted = AnimalDTO.toAnimal(animalDTO);
            var createdAnimal = animalRepository.save(animalToBePersisted);

            return Animal.toDTO(createdAnimal);
        } catch (Exception ex) {
            throw new PersistenceFailedException(ex.getLocalizedMessage());
        }
    }

    public Animal updateById(AnimalDTO animalDTO, UUID animalId) {
        Optional<Animal> animal = animalRepository.findById(animalId);

        if(animal.isPresent()) {
            Animal animalEntity = AnimalDTO.toAnimal(animalDTO);
            return animalRepository.save(animalEntity);
        }

        throw new ResourceNotFoundException();
    }

    public List<AnimalDTO> getAllAnimals() {
        List<AnimalDTO> animals = new ArrayList<AnimalDTO>();
        var animalEntities = animalRepository.findAll();

        animalEntities.forEach( animal ->
                animals.add(Animal.toDTO(animal))
        );

        return animals;
    }

    public Optional<AnimalDTO> getAnimalById(UUID animalId) {
        return animalRepository
                .findById(animalId)
                .map(Animal::toDTO);
    }

    public Optional<Animal> delete(UUID animalId) {
        Optional<Animal> animal = animalRepository.findById(animalId);

        animalRepository.deleteById(animalId);

        return animal;

    }
}
