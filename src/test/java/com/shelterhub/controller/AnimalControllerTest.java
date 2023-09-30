package com.shelterhub.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shelterhub.domain.enums.Gender;
import com.shelterhub.domain.enums.Size;
import com.shelterhub.dto.request.AnimalRequest;
import com.shelterhub.dto.response.AnimalResponse;
import com.shelterhub.exception.ResourceNotFoundException;
import com.shelterhub.service.AnimalService;
import lombok.SneakyThrows;
import net.datafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.UUID;

import static com.shelterhub.utils.AnimalTestUtils.buildAnimalDTO;
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

    private final String PATH_URL = "/v1/animal";

    @Test
    @SneakyThrows
    public void shouldReturnAnimalById() {
        AnimalRequest animalRequest = buildAnimalDTO(true);
        UUID animalId = animalRequest.getId();
        AnimalResponse animalResponse = animalRequest.toAnimal().toResponse();

        when(animalService.getAnimalById(animalId)).thenReturn(animalResponse);

        mockMvc.perform(MockMvcRequestBuilders.get(PATH_URL + "/{id}", animalId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(animalRequest.getName()))
                .andExpect(jsonPath("$.identification").value(animalRequest.getIdentification()))
                .andExpect(jsonPath("$.behavior").value(animalRequest.getBehavior()))
                .andExpect(jsonPath("$.breed").value(animalRequest.getBreed()))
                .andExpect(jsonPath("$.history").value(animalRequest.getHistory()))
                .andExpect(jsonPath("$.estimatedAge").value(animalRequest.getEstimatedAge().toEstimatedAge()
                        .toString()))
                .andExpect(jsonPath("$.medicalRecordId").value(animalRequest.getMedicalRecordId().toString()))
                .andExpect(jsonPath("$.animalType").value(animalRequest.getAnimalType()))
                .andExpect(jsonPath("$.gender").value(Gender.FEMALE.toString()))
                .andExpect(jsonPath("$.size").value(Size.SMALL.toString()));


        verify(animalService, times(1)).getAnimalById(animalId);
        verifyNoMoreInteractions(animalService);
    }

    @Test
    @SneakyThrows
    public void shouldNotGetAnimalByIdIfAnimalNotFound() {
        var animalId = UUID.randomUUID();
        when(animalService.getAnimalById(animalId)).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get(PATH_URL + "/{id}", animalId))
                .andExpect(status().isNotFound());

        verify(animalService, times(1)).getAnimalById(animalId);
        verifyNoMoreInteractions(animalService);
    }

    @Test
    @SneakyThrows
    public void shouldNotGetAnimalByIdIfUUIDIsNull() {

        mockMvc.perform(MockMvcRequestBuilders.get(PATH_URL + "/{id}", "42d7caba-4869-42b2-af86-f340dd461882888"))
                .andExpect(status().isBadRequest());

    }

    @Test
    @SneakyThrows
    public void shouldReturnAllAnimals() {
        AnimalResponse firstAnimal = buildAnimalDTO(true).toAnimal().toResponse();
        AnimalResponse secondAnimal = buildAnimalDTO(true).toAnimal().toResponse();

        when(animalService.getAll()).thenReturn(List.of(firstAnimal, secondAnimal));

        mockMvc.perform(MockMvcRequestBuilders.get(PATH_URL))
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

        verify(animalService, times(1)).getAll();
        verifyNoMoreInteractions(animalService);
    }

    @Test
    @SneakyThrows
    public void shouldReturnNotFoundResponseWhenRepositoryIsEmpty() {
        when(animalService.getAll()).thenReturn(List.of());

        mockMvc.perform(MockMvcRequestBuilders.get(PATH_URL))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").doesNotExist());


        verify(animalService, times(1)).getAll();
        verifyNoMoreInteractions(animalService);
    }

    @Test
    @SneakyThrows
    public void shouldCreateAnimal() {
        AnimalRequest animalRequest = buildAnimalDTO(false);
        AnimalResponse animalResponse = animalRequest.toAnimal().toResponse();
        animalResponse.setId(UUID.randomUUID());
        when(animalService.create(animalRequest)).thenReturn(animalResponse);

        mockMvc.perform(MockMvcRequestBuilders.post(PATH_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(animalRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(animalResponse.getId().toString()))
                .andExpect(jsonPath("$.animalType").value(animalRequest.getAnimalType()))
                .andExpect(jsonPath("$.name").value(animalRequest.getName()))
                .andExpect(jsonPath("$.estimatedAge").value(animalRequest.getEstimatedAge().toEstimatedAge().toString()));

        verify(animalService, times(1)).create(animalRequest);
        verifyNoMoreInteractions(animalService);
    }

    @Test
    @SneakyThrows
    public void shouldNotCreateAnimalWithInvalidSize() {
        AnimalRequest animalRequest = buildAnimalDTO(false);

        String invalidSize = new Faker().lorem().characters(10);

        String invalidJsonPayload = new ObjectMapper()
                .writeValueAsString(animalRequest)
                .replace("\"size\":\"" + animalRequest.getSize() + "\"", "\"size\":\"" + invalidSize + "\"");

        mockMvc.perform(MockMvcRequestBuilders.post(PATH_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJsonPayload))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Invalid request parameter")))
                .andExpect(content().string(containsString("size")));
    }

    @Test
    @SneakyThrows
    public void shouldNotCreateAnimalWithInvalidGender() {
        AnimalRequest animalRequest = buildAnimalDTO(false);

        String invalidGender = new Faker().lorem().characters(10);

        String invalidJsonPayload = new ObjectMapper()
                .writeValueAsString(animalRequest)
                .replace("\"gender\":\"" + animalRequest.getGender() + "\"",
                        "\"gender\":\"" + invalidGender + "\""
                );

        mockMvc.perform(MockMvcRequestBuilders.post(PATH_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJsonPayload))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Invalid request parameter")))
                .andExpect(content().string(containsString("gender")));
    }

    @Test
    @SneakyThrows
    public void shouldUpdateAnimal() {
        AnimalRequest animalRequest = buildAnimalDTO(true);
        UUID animalId = animalRequest.getId();

        when(animalService.updateById(animalRequest, animalId)).thenReturn(animalRequest.toAnimal().toResponse());

        mockMvc.perform(MockMvcRequestBuilders.put(PATH_URL + "/{id}", animalId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(animalRequest)))
                .andExpect(status().isNoContent());

        verify(animalService, times(1)).updateById(animalRequest, animalId);
        verifyNoMoreInteractions(animalService);
    }

    @Test
    @SneakyThrows
    public void shouldNotUpdateAnimalIfAnimalNotFound() {
        AnimalRequest animalRequest = buildAnimalDTO(true);
        UUID animalId = animalRequest.getId();

        when(animalService.updateById(animalRequest, animalId)).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.put(PATH_URL + "/{id}", animalId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(animalRequest))
        ).andExpect(status().isNotFound());

        verify(animalService, times(1)).updateById(animalRequest, animalId);
        verifyNoMoreInteractions(animalService);
    }

    @Test
    @SneakyThrows
    public void shouldDeleteAnimal() {
        AnimalRequest animalRequest = buildAnimalDTO(true);
        UUID animalId = animalRequest.getId();
        var animalResponse = animalRequest.toAnimal().toResponse();
        when(animalService.delete(animalId)).thenReturn(animalResponse);

        mockMvc.perform(MockMvcRequestBuilders.delete(PATH_URL + "/{id}", animalId))
                .andExpect(status().isNoContent());

        verify(animalService, times(1)).delete(animalId);
        verifyNoMoreInteractions(animalService);
    }

    @Test
    @SneakyThrows
    public void shouldNotDeleteAnimalIfAnimalNotFound() {
        AnimalRequest animalRequest = buildAnimalDTO(true);
        UUID animalId = animalRequest.getId();

        when(animalService.delete(animalId)).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.delete(PATH_URL + "/{id}", animalId))
                .andExpect(status().isNotFound());

        verify(animalService, times(1)).delete(animalId);
        verifyNoMoreInteractions(animalService);
    }

}
