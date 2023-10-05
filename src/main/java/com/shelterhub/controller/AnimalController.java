package com.shelterhub.controller;

import com.shelterhub.dto.request.AnimalRequest;
import com.shelterhub.dto.response.AnimalResponse;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "v1/animal", produces = MediaType.APPLICATION_JSON_VALUE)
public class AnimalController {

    @Autowired
    private AnimalService animalService;

    @GetMapping("/{id}")
    @ResponseBody
    public AnimalResponse getById(@PathVariable UUID id) {
        return animalService.getAnimalById(id);
    }

    @GetMapping
    public ResponseEntity<List<AnimalResponse>> getAll() {
        var animals = animalService.getAll();

        if (animals.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(animals);
    }

    @PostMapping
    public ResponseEntity<AnimalResponse> create(
            @RequestBody AnimalRequest animalRequest
    ) {
        AnimalResponse animal = animalService.create(animalRequest);

        var id = animal.getId().toString();
        var location = URI.create(id);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(location)
                .body(animal);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateAnimal(
            @PathVariable UUID id,
            @RequestBody AnimalRequest animal
    ) {
        animalService.updateById(animal, id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnimal(@PathVariable UUID id) {
        animalService.delete(id);

        return ResponseEntity.noContent().build();
    }

}
