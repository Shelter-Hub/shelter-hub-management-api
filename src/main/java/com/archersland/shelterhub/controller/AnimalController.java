package com.archersland.shelterhub.controller;

import com.archersland.shelterhub.database.AnimalRepository;
import com.archersland.shelterhub.domain.Animal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/animal")
public class AnimalController {

    @Autowired
    private AnimalRepository animalRepository;

    @GetMapping("{id}")
    public Optional<Animal> getAnimalById(@PathVariable UUID id) {
        return animalRepository.findById(id);
    }
    @GetMapping
    public List<Animal> getAllAnimals() {
        return animalRepository.findAll();
    }
    @PostMapping
    public void createAnimal(@RequestBody Animal animal){
        animalRepository.save(animal);
    }
    @PutMapping("{id}")
    public void updateAnimal(@PathVariable UUID id, @RequestBody Animal animal){
        if(id != null) animalRepository.save(animal);
    }
    @DeleteMapping("{id}")
    public void deleteAnimal(@PathVariable UUID id){
        animalRepository.deleteById(id);
    }

}
