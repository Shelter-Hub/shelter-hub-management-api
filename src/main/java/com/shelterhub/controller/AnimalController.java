package com.shelterhub.controller;

import com.shelterhub.dto.AnimalDTO;
import com.shelterhub.service.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/animal", produces = MediaType.APPLICATION_JSON_VALUE)
public class AnimalController {

    @Autowired
    private AnimalService animalService;

    @GetMapping("/{id}")
    public Optional<AnimalDTO> getAnimalById(@PathVariable UUID id) {
        return animalService.getAnimalById(id);
    }

    @GetMapping
    public List<AnimalDTO> getAllAnimals() {
        return animalService.getAllAnimals();
    }

    @PostMapping
    public ResponseEntity<AnimalDTO> createAnimal(@RequestBody AnimalDTO animal){
        AnimalDTO createdAnimal = animalService.create(animal);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAnimal);
    }
    @PutMapping("/{id}")
    public void updateAnimal(@PathVariable UUID id, @RequestBody AnimalDTO animal){
        if(id != null) animalService.update(animal, id);
    }
    @DeleteMapping("/{id}")
    public void deleteAnimal(@PathVariable UUID id){
        animalService.delete(id);
    }

}
