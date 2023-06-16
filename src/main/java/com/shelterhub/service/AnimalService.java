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

    public AnimalDTO create(AnimalDTO animalDTO) {
        var animalToBePersisted = AnimalDTO.toAnimal(animalDTO);
        var createdAnimal = animalRepository.save(animalToBePersisted);

        return Animal.toDTO(createdAnimal);
    }

    public AnimalDTO update(AnimalDTO animalDTO, UUID animalId) {
        Animal animal = animalRepository.getReferenceById(animalId);

        Animal animalToBePersisted = AnimalDTO.toAnimalWithSameAnimalId(animalDTO, animalId);
        animalRepository.save(animalToBePersisted);

        return animalDTO;
    }

    public List<AnimalDTO> getAllAnimals() {
        return animalRepository
                .findAll()
                .stream()
                .map(Animal::toDTO)
                .collect(Collectors.toList());
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
