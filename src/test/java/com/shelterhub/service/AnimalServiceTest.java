package com.shelterhub.service;

import com.shelterhub.database.AnimalRepository;
import com.shelterhub.domain.model.Animal;
import com.shelterhub.dto.AnimalDTO;
import com.shelterhub.dto.AnimalResponseDTO;
import com.shelterhub.exception.InvalidValueException;
import com.shelterhub.exception.PersistenceFailedException;
import com.shelterhub.exception.ResourceNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static com.shelterhub.utils.AnimalUtils.buildAnimalDTO;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        AnimalDTO animalDTO = buildAnimalDTO(false);

        when(animalRepository.save(any(Animal.class))).thenReturn(animalDTO.toAnimal());

        AnimalResponseDTO result = animalService.create(animalDTO);

        assertAnimalDTO(animalDTO, result);
        verify(animalRepository, times(1)).save(any(Animal.class));
    }


    @Test
    public void shouldThrowsInvalidValueExceptionWhenAnimalTypeIsNullOrEmpty() {
        AnimalDTO animalDTO = buildAnimalDTO(false, "");

        assertThrowsExactly(
                InvalidValueException.class,
                () -> animalService.create(animalDTO)
        );

        verify(animalRepository, times(0)).save(any(Animal.class));
    }

    @Test
    public void shouldThrowPersistenceFailedExceptionWhenCreatingAnimal() {
        AnimalDTO animalDTO = buildAnimalDTO(false);

        when(animalRepository.save(any(Animal.class))).thenThrow(RuntimeException.class);

        assertThrows(
                PersistenceFailedException.class,
                () -> animalService.create(animalDTO)
        );

        verify(animalRepository, times(1)).save(any(Animal.class));
    }

    @Test
    public void shouldReturnUpdatedAnimalDTO() {
        AnimalDTO substitutedAnimalDTO = buildAnimalDTO(true);
        AnimalDTO animalDTO = buildAnimalDTO(true);

        when(animalRepository.findById(animalDTO.getId())).thenReturn(Optional.of(substitutedAnimalDTO.toAnimal()));
        when(animalRepository.save(any(Animal.class))).thenReturn(animalDTO.toAnimal());

        AnimalResponseDTO result = animalService.updateById(animalDTO, animalDTO.getId());

        assertEquals(animalDTO.getId(), result.getId());
        assertAnimalDTO(animalDTO, result);
        verify(animalRepository, times(1)).save(any(Animal.class));
        verify(animalRepository, times(1)).findById(animalDTO.getId());
    }

    @Test
    public void shouldNotReturnUpdatedAnimalDTOIfAnimalNotFound() {
        AnimalDTO animalDTO = buildAnimalDTO(true);

        when(animalRepository.findById(animalDTO.getId())).thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> animalService.updateById(
                        animalDTO,
                        animalDTO.getId()
                )
        );
        verify(animalRepository, times(1)).findById(animalDTO.getId());
    }

    @Test
    public void shouldReturnListOfAnimalDTOs() {
        Animal animal1 = buildAnimalDTO(true).toAnimal();
        Animal animal2 = buildAnimalDTO(true).toAnimal();
        List<Animal> animals = Arrays.asList(animal1, animal2);

        when(animalRepository.findAll()).thenReturn(animals);

        List<AnimalResponseDTO> result = animalService.getAllAnimals();

        assertEquals(animals.size(), result.size());
        verify(animalRepository, times(1)).findAll();
    }

    @Test
    public void shouldReturnAnimalById() {
        AnimalDTO animalDTO = buildAnimalDTO(true);

        when(animalRepository.findById(animalDTO.getId())).thenReturn(Optional.of(animalDTO.toAnimal()));

        AnimalResponseDTO result = animalService.getAnimalById(animalDTO.getId());

        assertEquals(animalDTO.getId(), result.getId());
        assertAnimalDTO(animalDTO, result);
        verify(animalRepository, times(1)).findById(animalDTO.getId());
    }

    @Test
    public void shouldNotReturnAnimalByIdIfAnimalNotFound() {
        AnimalDTO animalDTO = buildAnimalDTO(true);

        when(animalRepository.findById(animalDTO.getId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> animalService.delete(animalDTO.getId()));
        verify(animalRepository, times(1)).findById(animalDTO.getId());
    }

    @Test
    public void shouldDeleteAnimal() {
        AnimalDTO animalDTO = buildAnimalDTO(true);

        when(animalRepository.findById(animalDTO.getId())).thenReturn(Optional.of(animalDTO.toAnimal()));

        AnimalResponseDTO result = animalService.delete(animalDTO.getId());

        verify(animalRepository, times(1)).deleteById(animalDTO.getId());
        verify(animalRepository, times(1)).findById(animalDTO.getId());
        assertNotNull(result);
        assertEquals(animalDTO.getId(), result.getId());
    }

    @Test
    public void shouldNotDeleteAnimalIfAnimalNotFound() {
        AnimalDTO animalDTO = buildAnimalDTO(true);

        when(animalRepository.findById(animalDTO.getId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> animalService.delete(animalDTO.getId()));

        verify(animalRepository, times(0)).deleteById(animalDTO.getId());
        verify(animalRepository, times(1)).findById(animalDTO.getId());
    }

    private static void assertAnimalDTO(AnimalDTO animalDTO, AnimalResponseDTO result) {
        assertAll(
                () -> assertEquals(animalDTO.getName(), result.getName()),
                () -> assertEquals(animalDTO.getEstimatedAgeDTO().toEstimatedAge(), result.getEstimatedAge()),
                () -> assertEquals(animalDTO.getAnimalType(), result.getAnimalType()),
                () -> assertEquals(animalDTO.getMedicalRecordId(), result.getMedicalRecordId())
        );
    }
}