package com.shelterhub.controller;

import com.shelterhub.domain.Animal;
import com.shelterhub.dto.AnimalDTO;
import com.shelterhub.service.AnimalFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/animal", produces = MediaType.APPLICATION_JSON_VALUE)
public class AnimalController {

    @Autowired
    private AnimalFacade animalFacade;

    @GetMapping("{id}")
    public Optional<AnimalDTO> getAnimalById(@PathVariable UUID id) {
        return animalFacade.getAnimalById(id);
    }
    @GetMapping
    public List<AnimalDTO> getAllAnimals() {
        return animalFacade.getAllAnimals();
    }
    @PostMapping
    public void createAnimal(@RequestBody AnimalDTO animal){
        animalFacade.create(animal);
    }
    @PutMapping("{id}")
    public void updateAnimal(@PathVariable UUID id, @RequestBody AnimalDTO animal){
        if(id != null) animalFacade.update(animal, id);
    }
    @DeleteMapping("{id}")
    public void deleteAnimal(@PathVariable UUID id){
        animalFacade.delete(id);
    }

}
