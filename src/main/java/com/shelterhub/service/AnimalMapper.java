package com.shelterhub.service;

import com.shelterhub.domain.model.Animal;
import com.shelterhub.dto.AnimalDTO;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class AnimalMapper {
    private Animal animal;

    public Animal convertToAnimal(AnimalDTO  animalDTO) {
        return new Animal();
    }

    public AnimalDTO convertToDto(Animal animal) {
        this.animal = animal;
        return new AnimalDTO();
    }
}



