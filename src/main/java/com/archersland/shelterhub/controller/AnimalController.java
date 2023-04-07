package com.archersland.shelterhub.controller;

import com.archersland.shelterhub.database.AnimalRepository;
import com.archersland.shelterhub.domain.Animal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class AnimalController {
    private final AnimalRepository animalRepository;
    @Autowired
    public AnimalController(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }
    @GetMapping
    public List<Animal> getAllAnimals(@RequestBody Animal animal) {
        return animalRepository.findAll();
    }
    @PostMapping
    public void createAnimal(Animal animal){
        animalRepository.save(animal);
    }
    @PutMapping
    public void updateAnimal(Animal animal){
        if(animal.getId() != null) animalRepository.save(animal);
    }
    @DeleteMapping
    public void deleteAnimal(Animal animal){
        animalRepository.delete(animal);
    }



//    @PostMapping("/animal")
//    public Animal createAnimal(@RequestBody Animal animal) {
//
//    }
//
//    @GetMapping("animal/{id}")
//    public Animal getAnimalById(@PathVariable UUID id) {
//
//    }
//
//    @PutMapping("/animal/{id}")
//    public Animal updateAnimal(@PathVariable UUID id, @RequestBody Animal animal) {
//
//    }
//
//    @DeleteMapping("/animal/{id}")
//    public void deleteAnimal(@PathVariable UUID id) {
//
//    }

}
