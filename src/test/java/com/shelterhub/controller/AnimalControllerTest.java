package com.shelterhub.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import com.shelterhub.dto.AnimalDTO;
import com.shelterhub.dto.AnimalResponseDTO;
import com.shelterhub.exception.ResourceNotFoundException;
import com.shelterhub.service.AnimalService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.UUID;

import static com.shelterhub.utils.AnimalUtils.buildAnimalDTO;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AnimalController.class)
public class AnimalControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AnimalService animalService;

    @Test
    @SneakyThrows
    public void shouldReturnAnimalById() {
        AnimalDTO animalDTO = buildAnimalDTO(true);
        UUID animalId = animalDTO.getId();
        AnimalResponseDTO animalResponse = animalDTO.toAnimal().toResponse();

        when(animalService.getAnimalById(animalId)).thenReturn(animalResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/animal/{id}", animalId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(animalId.toString()))
                .andExpect(jsonPath("$.animalType").value(animalDTO.getAnimalType()))
                .andExpect(jsonPath("$.name").value(animalDTO.getName()))
                .andExpect(jsonPath("$.estimatedAge").value(animalDTO
                        .getEstimatedAgeDTO()
                        .toEstimatedAge()
                        .toString())
                );

        verify(animalService, times(1)).getAnimalById(animalId);
        verifyNoMoreInteractions(animalService);
    }

    @Test
    @SneakyThrows
    public void shouldNotGetAnimalByIdIfAnimalNotFound() {
        var animalId = UUID.randomUUID();
        when(animalService.getAnimalById(animalId)).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/animal/{id}", animalId))
                .andExpect(status().isNotFound());

        verify(animalService, times(1)).getAnimalById(animalId);
        verifyNoMoreInteractions(animalService);
    }

    @Test
    @SneakyThrows
    public void shouldNotGetAnimalByIdIfUUIDIsNull() {

        mockMvc.perform(MockMvcRequestBuilders.get("/animal/{id}", "42d7caba-4869-42b2-af86-f340dd461882888"))
                .andExpect(status().isBadRequest());

    }

    @Test
    @SneakyThrows
    public void shouldReturnAllAnimals() {
        AnimalResponseDTO firstAnimal = buildAnimalDTO(true).toAnimal().toResponse();
        AnimalResponseDTO secondAnimal = buildAnimalDTO(true).toAnimal().toResponse();

        when(animalService.getAllAnimals()).thenReturn(List.of(firstAnimal, secondAnimal));

        mockMvc.perform(MockMvcRequestBuilders.get("/animal"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(firstAnimal.getId().toString()))
                .andExpect(jsonPath("$[0].animalType").value(firstAnimal.getAnimalType()))
                .andExpect(jsonPath("$[0].name").value(firstAnimal.getName()))
                .andExpect(jsonPath("$[0].estimatedAge").value(firstAnimal.getEstimatedAge().toString()))
                .andExpect(jsonPath("$[1].id").value(secondAnimal.getId().toString()))
                .andExpect(jsonPath("$[1].animalType").value(secondAnimal.getAnimalType()))
                .andExpect(jsonPath("$[1].name").value(secondAnimal.getName()))
                .andExpect(jsonPath("$[1].estimatedAge").value(secondAnimal.getEstimatedAge().toString()));

        verify(animalService, times(1)).getAllAnimals();
        verifyNoMoreInteractions(animalService);
    }

    @Test
    @SneakyThrows
    public void shouldReturnZeroAnimalsWhenRepositoryIsEmpty() {
        when(animalService.getAllAnimals()).thenReturn(List.of());

        mockMvc.perform(MockMvcRequestBuilders.get("/animal"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));

        verify(animalService, times(1)).getAllAnimals();
        verifyNoMoreInteractions(animalService);
    }

    @Test
    @SneakyThrows
    public void shouldCreateAnimal() {
        AnimalDTO animalDTO = buildAnimalDTO(false);
        AnimalResponseDTO animalResponseDTO = animalDTO.toAnimal().toResponse();
        animalResponseDTO.setId(UUID.randomUUID());
        when(animalService.create(animalDTO)).thenReturn(animalResponseDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/animal")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsString(animalDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(animalResponseDTO.getId().toString()))
                .andExpect(jsonPath("$.animalType").value(animalDTO.getAnimalType()))
                .andExpect(jsonPath("$.name").value(animalDTO.getName()))
                .andExpect(jsonPath("$.estimatedAge").value(animalDTO.getEstimatedAgeDTO().toEstimatedAge().toString()));

        verify(animalService, times(1)).create(animalDTO);
        verifyNoMoreInteractions(animalService);
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

        when(animalService.updateById(animalDTO, animalId)).thenReturn(animalDTO.toAnimal().toResponse());

        mockMvc.perform(MockMvcRequestBuilders.put("/animal/{id}", animalId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(animalDTO)))
                .andExpect(status().isNoContent());

        verify(animalService, times(1)).updateById(animalDTO, animalId);
        verifyNoMoreInteractions(animalService);
    }

    @Test
    @SneakyThrows
    public void shouldNotUpdateAnimalIfAnimalNotFound() {
        AnimalDTO animalDTO = buildAnimalDTO(true);
        UUID animalId = animalDTO.getId();

        when(animalService.updateById(animalDTO, animalId)).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.put("/animal/{id}", animalId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsString(animalDTO))
                ).andExpect(status().isNotFound());

        verify(animalService, times(1)).updateById(animalDTO, animalId);
        verifyNoMoreInteractions(animalService);
    }

    @Test
    @SneakyThrows
    public void shouldDeleteAnimal() {
        AnimalDTO animalDTO = buildAnimalDTO(true);
        UUID animalId = animalDTO.getId();
        var animalResponse = animalDTO.toAnimal().toResponse();
        when(animalService.delete(animalId)).thenReturn(animalResponse);

        mockMvc.perform(MockMvcRequestBuilders.delete("/animal/{id}", animalId))
                .andExpect(status().isNoContent());

        verify(animalService, times(1)).delete(animalId);
        verifyNoMoreInteractions(animalService);
    }

    @Test
    @SneakyThrows
    public void shouldNotDeleteAnimalIfAnimalNotFound() {
        AnimalDTO animalDTO = buildAnimalDTO(true);
        UUID animalId = animalDTO.getId();

        when(animalService.delete(animalId)).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.delete("/animal/{id}", animalId))
                .andExpect(status().isNotFound());

        verify(animalService, times(1)).delete(animalId);
        verifyNoMoreInteractions(animalService);
    }

}
