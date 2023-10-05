package com.shelterhub.service;

import com.shelterhub.database.AnimalRepository;
import com.shelterhub.domain.model.Animal;
import com.shelterhub.dto.request.AnimalRequest;
import com.shelterhub.dto.response.AnimalResponse;
import com.shelterhub.exception.InvalidValueException;
import com.shelterhub.exception.PersistenceFailedException;
import com.shelterhub.exception.ResourceNotFoundException;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class AnimalService {

    @Autowired
    private AnimalRepository animalRepository;

    private static final Logger log = LoggerFactory.getLogger(AnimalService.class);

    public AnimalResponse create(AnimalRequest animalRequest) {
        if (ObjectUtils.isEmpty(animalRequest.getAnimalType())) {
            throw new InvalidValueException("animalType cannot be empty or null.");
        }

        try {
            var createdAnimal = animalRepository.save(animalRequest.toAnimal());

            log.makeLoggingEventBuilder(Level.INFO)
                    .setMessage("Animal was saved with success.")
                    .addKeyValue("animalId", createdAnimal.getId().toString())
                    .log();

            return createdAnimal.toResponse();
        } catch (Exception ex) {
            throw new PersistenceFailedException(ex.getLocalizedMessage());
        }
    }

    public AnimalResponse updateById(AnimalRequest animalRequest, UUID animalId) {
        var exists = animalRepository.existsById(animalId);

        if (!exists)
            throw new ResourceNotFoundException();

        Animal animal = animalRequest.toAnimal();
        var updatedAnimal = animalRepository.save(animal);
        return updatedAnimal.toResponse();
    }

    public List<AnimalResponse> getAll() {
        return animalRepository
                .findAll()
                .stream()
                .map(Animal::toResponse)
                .toList();
    }

    public AnimalResponse getAnimalById(UUID animalId) {
        var animal = animalRepository
                .findById(animalId);

        return getAnimalIfPresent(animal).toResponse();
    }

    public AnimalResponse delete(UUID animalId) {
        var animal = getAnimalIfPresent(animalRepository.findById(animalId));
        animalRepository.deleteById(animal.getId());

        log.makeLoggingEventBuilder(Level.INFO)
                .setMessage("Animal was deleted with success.")
                .addKeyValue("animalId", animal.getId().toString())
                .log();

        return animal.toResponse();
    }

    private Animal getAnimalIfPresent(Optional<Animal> animal) {
        return animal.orElseThrow(ResourceNotFoundException::new);
    }
}
