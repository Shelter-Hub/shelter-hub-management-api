package com.shelterhub.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import com.shelterhub.dto.AnimalDTO;
import com.shelterhub.service.AnimalFacade;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.shelterhub.utils.AnimalUtils.buildAnimalDTO;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AnimalController.class)
public class AnimalControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AnimalFacade animalFacade;

    @Test
    @SneakyThrows
    public void shouldReturnAnimalById() {
        AnimalDTO animalDTO = buildAnimalDTO(true);
        UUID animalId = animalDTO.getId();

        when(animalFacade.getAnimalById(animalId)).thenReturn(Optional.of(animalDTO));

        mockMvc.perform(MockMvcRequestBuilders.get("/animal/{id}", animalId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(animalId.toString()))
                .andExpect(jsonPath("$.animal_type").value(animalDTO.getAnimal_type()))
                .andExpect(jsonPath("$.name").value(animalDTO.getName()))
                .andExpect(jsonPath("$.age").value(animalDTO.getAge().intValue()));

        verify(animalFacade, times(1)).getAnimalById(animalId);
        verifyNoMoreInteractions(animalFacade);
    }

    @Test
    @SneakyThrows
    public void shouldReturnAllAnimals() {
        AnimalDTO firstAnimal = buildAnimalDTO(true);
        AnimalDTO secondAnimal = buildAnimalDTO(true);

        when(animalFacade.getAllAnimals()).thenReturn(List.of(firstAnimal, secondAnimal));

        mockMvc.perform(MockMvcRequestBuilders.get("/animal"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(firstAnimal.getId().toString()))
                .andExpect(jsonPath("$[0].animal_type").value(firstAnimal.getAnimal_type()))
                .andExpect(jsonPath("$[0].name").value(firstAnimal.getName()))
                .andExpect(jsonPath("$[0].age").value(firstAnimal.getAge().intValue()))
                .andExpect(jsonPath("$[1].id").value(secondAnimal.getId().toString()))
                .andExpect(jsonPath("$[1].animal_type").value(secondAnimal.getAnimal_type()))
                .andExpect(jsonPath("$[1].name").value(secondAnimal.getName()))
                .andExpect(jsonPath("$[1].age").value(secondAnimal.getAge().intValue()));

        verify(animalFacade, times(1)).getAllAnimals();
        verifyNoMoreInteractions(animalFacade);
    }

    @Test
    @SneakyThrows
    public void shouldReturnZeroAnimalsWhenRepositoryIsEmpty() {
        when(animalFacade.getAllAnimals()).thenReturn(List.of());

        mockMvc.perform(MockMvcRequestBuilders.get("/animal"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));

        verify(animalFacade, times(1)).getAllAnimals();
        verifyNoMoreInteractions(animalFacade);
    }

    @Test
    @SneakyThrows
    public void shouldCreateAnimal() {
        AnimalDTO animalDTO = buildAnimalDTO(false);
        when(animalFacade.create(animalDTO)).thenReturn(animalDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/animal")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsString(animalDTO)))
                .andExpect(status().isOk());

        verify(animalFacade, times(1)).create(animalDTO);
        verifyNoMoreInteractions(animalFacade);
    }

    @Test
    @SneakyThrows
    public void shouldNotCreateAnimalWithInvalidSize() {
        AnimalDTO animalDTO = buildAnimalDTO(false);

        String invalidSize = new Faker().lorem().characters(10);

        String invalidJsonPayload = new ObjectMapper()
                .writeValueAsString(animalDTO)
                .replace("\"size\":\"" + animalDTO.getSize() + "\"", "\"size\":\"" + invalidSize + "\"");

        mockMvc.perform(MockMvcRequestBuilders.post("/animal")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJsonPayload))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Invalid request parameter")))
                .andExpect(content().string(containsString("size")));
    }

    @Test
    @SneakyThrows
    public void shouldNotCreateAnimalWithInvalidGender() {
        AnimalDTO animalDTO = buildAnimalDTO(false);

        String invalidGender = new Faker().lorem().characters(10);

        String invalidJsonPayload = new ObjectMapper()
                .writeValueAsString(animalDTO)
                .replace("\"gender\":\"" + animalDTO.getGender() + "\"",
                        "\"gender\":\"" + invalidGender + "\""
                );

        mockMvc.perform(MockMvcRequestBuilders.post("/animal")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJsonPayload))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Invalid request parameter")))
                .andExpect(content().string(containsString("gender")));
    }

    @Test
    @SneakyThrows
    public void shouldUpdateAnimal() {
        AnimalDTO animalDTO = buildAnimalDTO(true);
        UUID animalId = animalDTO.getId();

        when(animalFacade.update(animalDTO, animalId)).thenReturn(animalDTO);

        mockMvc.perform(MockMvcRequestBuilders.put("/animal/{id}", animalId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(animalDTO)))
                .andExpect(status().isOk());

        verify(animalFacade, times(1)).update(animalDTO, animalId);
        verifyNoMoreInteractions(animalFacade);
    }

    @Test
    @SneakyThrows
    public void shouldDeleteAnimal() {
        AnimalDTO animalDTO = buildAnimalDTO(true);
        UUID animalId = animalDTO.getId();

        when(animalFacade.delete(animalId)).thenReturn("");

        mockMvc.perform(MockMvcRequestBuilders.delete("/animal/{id}", animalId))
                .andExpect(status().isOk());

        verify(animalFacade, times(1)).delete(animalId);
        verifyNoMoreInteractions(animalFacade);
    }
}
