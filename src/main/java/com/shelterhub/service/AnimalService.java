package com.shelterhub.service;

import com.shelterhub.database.AnimalRepository;
import com.shelterhub.domain.model.Animal;
import com.shelterhub.dto.AnimalDTO;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AnimalService {
    private final AnimalRepository animalRepository;
    private final AnimalMapper animalMapper;

    public AnimalService(AnimalRepository animalRepository, AnimalMapper animalMapper) {
        this.animalRepository = animalRepository;
        this.animalMapper = animalMapper;
    }

    @Validated
    public AnimalDTO create(AnimalDTO newAnimalDTO) {
        Animal animal = animalMapper.convertToAnimal(new AnimalDTO());
        animalRepository.save(animal);
        return newAnimalDTO;
    }

    public AnimalDTO update(AnimalDTO animalDTO, UUID animalId) {
        Optional<Animal> optionalAnimal = animalRepository.findById(animalId);
        if (optionalAnimal.isPresent()) {
            Animal existingAnimal = optionalAnimal.get();
            animalRepository.save(existingAnimal);
        }
        return animalDTO;
    }

    public List<AnimalDTO> getAllAnimals() {
        return animalRepository.findAll()
                .stream()
                .map(animalMapper::convertToDto)
                .collect(Collectors.toList());
    }

    public Optional<AnimalDTO> getAnimalById(UUID animalId) {
        return animalRepository.findById(animalId)
                .map(animalMapper::convertToDto);
    }

    public String delete(UUID animalId) {
        Optional<Animal> optionalAnimal = animalRepository.findById(animalId);
        if (optionalAnimal.isPresent()) {
            animalRepository.deleteById(animalId);
            return "Animal " + animalId + " was deleted successfully";
        }
        return null;
    }
}
