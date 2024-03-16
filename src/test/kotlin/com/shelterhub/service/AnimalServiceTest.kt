package com.shelterhub.service;

import br.com.shelterhubmanagementapi.repository.AnimalRepository;
import br.com.shelterhubmanagementapi.domain.model.Animal;
import br.com.shelterhubmanagementapi.dto.request.AnimalRequest;
import br.com.shelterhubmanagementapi.dto.response.AnimalResponse;
import br.com.shelterhubmanagementapi.exception.InvalidValueException;
import br.com.shelterhubmanagementapi.exception.PersistenceFailedException;
import br.com.shelterhubmanagementapi.exception.ResourceNotFoundException;
import br.com.shelterhubmanagementapi.service.AnimalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.shelterhub.utils.AnimalTestUtils.buildAnimalDTO;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AnimalServiceTest {
    @Mock
    private AnimalRepository animalRepository;

    @InjectMocks
    private AnimalService animalService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void shouldReturnCreatedAnimalDTO() {
        AnimalRequest animalRequest = buildAnimalDTO(true);

        when(animalRepository.save(any(Animal.class))).thenReturn(animalRequest.toAnimal());

        AnimalResponse result = animalService.create(animalRequest);

        assertAnimalDTO(animalRequest, result);
        verify(animalRepository, times(1)).save(any(Animal.class));
    }


    @Test
    public void shouldThrowsInvalidValueExceptionWhenAnimalTypeIsNullOrEmpty() {
        AnimalRequest animalRequest = buildAnimalDTO(false, "");

        assertThrowsExactly(
                InvalidValueException.class,
                () -> animalService.create(animalRequest)
        );

        verify(animalRepository, times(0)).save(any(Animal.class));
    }

    @Test
    public void shouldThrowPersistenceFailedExceptionWhenCreatingAnimal() {
        AnimalRequest animalRequest = buildAnimalDTO(false);

        when(animalRepository.save(any(Animal.class))).thenThrow(RuntimeException.class);

        assertThrows(
                PersistenceFailedException.class,
                () -> animalService.create(animalRequest)
        );

        verify(animalRepository, times(1)).save(any(Animal.class));
    }

    @Test
    public void shouldReturnUpdatedAnimalDTO() {
        AnimalRequest substitutedAnimalRequest = buildAnimalDTO(true);
        AnimalRequest animalRequest = buildAnimalDTO(true);

        when(animalRepository.existsById(animalRequest.getId())).thenReturn(true);
        when(animalRepository.save(any(Animal.class))).thenReturn(animalRequest.toAnimal());

        AnimalResponse result = animalService.updateById(animalRequest, animalRequest.getId());

        assertEquals(animalRequest.getId(), result.getId());
        assertAnimalDTO(animalRequest, result);
        verify(animalRepository, times(1)).save(any(Animal.class));
        verify(animalRepository, times(1)).existsById(animalRequest.getId());
    }

    @Test
    public void shouldNotReturnUpdatedAnimalDTOIfAnimalNotFound() {
        AnimalRequest animalRequest = buildAnimalDTO(true);

        when(animalRepository.existsById(animalRequest.getId())).thenReturn(false);

        assertThrows(
                ResourceNotFoundException.class,
                () -> animalService.updateById(
                        animalRequest,
                        animalRequest.getId()
                )
        );
        verify(animalRepository, times(1)).existsById(animalRequest.getId());
    }

    @Test
    public void shouldReturnListOfAnimalDTOs() {
        Animal animal1 = buildAnimalDTO(true).toAnimal();
        Animal animal2 = buildAnimalDTO(true).toAnimal();
        List<Animal> animals = Arrays.asList(animal1, animal2);

        when(animalRepository.findAll()).thenReturn(animals);

        List<AnimalResponse> result = animalService.getAll();

        assertEquals(animals.size(), result.size());
        verify(animalRepository, times(1)).findAll();
    }

    @Test
    public void shouldReturnAnimalById() {
        AnimalRequest animalRequest = buildAnimalDTO(true);

        when(animalRepository.findById(animalRequest.getId())).thenReturn(Optional.of(animalRequest.toAnimal()));

        AnimalResponse result = animalService.getById(animalRequest.getId());

        assertEquals(animalRequest.getId(), result.getId());
        assertAnimalDTO(animalRequest, result);
        verify(animalRepository, times(1)).findById(animalRequest.getId());
    }

    @Test
    public void shouldNotReturnAnimalByIdIfAnimalNotFound() {
        AnimalRequest animalRequest = buildAnimalDTO(true);

        when(animalRepository.findById(animalRequest.getId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> animalService.deleteById(animalRequest.getId()));
        verify(animalRepository, times(1)).findById(animalRequest.getId());
    }

    @Test
    public void shouldDeleteAnimal() {
        AnimalRequest animalRequest = buildAnimalDTO(true);

        when(animalRepository.findById(animalRequest.getId())).thenReturn(Optional.of(animalRequest.toAnimal()));

        AnimalResponse result = animalService.deleteById(animalRequest.getId());

        verify(animalRepository, times(1)).deleteById(animalRequest.getId());
        verify(animalRepository, times(1)).findById(animalRequest.getId());
        assertNotNull(result);
        assertEquals(animalRequest.getId(), result.getId());
    }

    @Test
    public void shouldNotDeleteAnimalIfAnimalNotFound() {
        AnimalRequest animalRequest = buildAnimalDTO(true);

        when(animalRepository.findById(animalRequest.getId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> animalService.deleteById(animalRequest.getId()));

        verify(animalRepository, times(0)).deleteById(animalRequest.getId());
        verify(animalRepository, times(1)).findById(animalRequest.getId());
    }

    private static void assertAnimalDTO(AnimalRequest animalRequest, AnimalResponse result) {
        assertAll(
                () -> assertEquals(animalRequest.getName(), result.getName()),
                () -> assertEquals(animalRequest.getEstimatedAge().toEstimatedAge(), result.getEstimatedAge()),
                () -> assertEquals(animalRequest.getAnimalType(), result.getAnimalType()),
                () -> assertEquals(animalRequest.getMedicalRecordId(), result.getMedicalRecordId())
        );
    }
}