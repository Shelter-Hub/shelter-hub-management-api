//package com.shelterhub.service
//
//import br.com.shelterhubmanagementapi.domain.model.Animal
//import br.com.shelterhubmanagementapi.dto.request.AnimalRequest
//import br.com.shelterhubmanagementapi.dto.request.toAnimal
//import br.com.shelterhubmanagementapi.exception.InvalidValueException
//import br.com.shelterhubmanagementapi.repository.AnimalRepository
//import br.com.shelterhubmanagementapi.service.AnimalService
//import com.ninjasquad.springmockk.MockkBean
//import com.shelterhub.utils.AnimalTestUtils.buildAnimalDTO
//import io.kotest.common.runBlocking
//import io.mockk.coEvery
//import io.mockk.coVerify
//import io.mockk.impl.annotations.InjectMockKs
//import org.junit.jupiter.api.Assertions
//import org.junit.jupiter.api.Test
//import org.junit.jupiter.api.function.Executable
//
//class AnimalServiceTest(
//
//) {
//    @MockkBean
//    private lateinit var animalRepository: AnimalRepository
//
//    @InjectMockKs
//    private lateinit var animalService: AnimalService
//
//    @Test
//    fun `should return created animal DTO`() {
//        val animalRequest: AnimalRequest = buildAnimalDTO(true)
//        coEvery { animalRepository.save(animalRequest.toAnimal()) } returns animalRequest.toAnimal()
//
//        val result = runBlocking { animalService.create(animalRequest) }
//        assertAnimalDTO(animalRequest, result)
//
//        coVerify(exactly = 1) { animalRepository.save(any()) }
//    }
//
//    @Test
//    fun shouldThrowsInvalidValueExceptionWhenAnimalTypeIsNullOrEmpty() {
//        val animalRequest: AnimalRequest = buildAnimalDTO(false)
//
//        coEvery { animalService.create(animalRequest) } throws(InvalidValueException())
//
//        coVerify(exactly = 0) { animalRepository.save(any()) }
//    }
//
//    @Test
//    fun shouldThrowPersistenceFailedExceptionWhenCreatingAnimal() {
//        val animalRequest: AnimalRequest = buildAnimalDTO(false)
//
//        coEvery { animalRepository.save(any()) } throws RuntimeException()
//
//        coVerify(exactly = 1) { animalService.create(animalRequest) }
//        coVerify(exactly = 0) { animalRepository.save(any<Animal>()) }
//    }

//    @Test
//    fun shouldReturnUpdatedAnimalDTO() {
//        val substitutedAnimalRequest: AnimalRequest = buildAnimalDTO(true)
//        val animalRequest: AnimalRequest = buildAnimalDTO(true)
//        `when`(animalRepository.existsById(animalRequest.id)).thenReturn(true)
//        `when`(animalRepository.save(any(Animal::class.java))).thenReturn(animalRequest.toAnimal())
//        val result: AnimalResponse = animalService.updateById(animalRequest, animalRequest.id)
//        Assertions.assertEquals(animalRequest.id, result.id)
//        assertAnimalDTO(animalRequest, result)
//        verify(animalRepository, times(1)).save(any(Animal::class.java))
//        verify(animalRepository, times(1)).existsById(animalRequest.id)
//    }
//
//    @Test
//    fun shouldNotReturnUpdatedAnimalDTOIfAnimalNotFound() {
//        val animalRequest: AnimalRequest = buildAnimalDTO(true)
//        `when`(animalRepository.existsById(animalRequest.id)).thenReturn(false)
//        Assertions.assertThrows<ResourceNotFoundException>(
//            ResourceNotFoundException::class.java,
//            Executable {
//                animalService.updateById(
//                    animalRequest,
//                    animalRequest.id
//                )
//            }
//        )
//        verify(animalRepository, times(1)).existsById(animalRequest.id)
//    }
//
//    @Test
//    fun shouldReturnListOfAnimalDTOs() {
//        val animal1: Animal = buildAnimalDTO(true).toAnimal()
//        val animal2: Animal = buildAnimalDTO(true).toAnimal()
//        val animals = Arrays.asList(animal1, animal2)
//        `when`(animalRepository.findAll()).thenReturn(animals)
//        val result: List<AnimalResponse> = animalService.getAll()
//        Assertions.assertEquals(animals.size, result.size)
//        verify(animalRepository, times(1)).findAll()
//    }
//
//    @Test
//    fun shouldReturnAnimalById() {
//        val animalRequest: AnimalRequest = buildAnimalDTO(true)
//        `when`(animalRepository.findById(animalRequest.id)).thenReturn(Optional.of(animalRequest.toAnimal()))
//        val result: AnimalResponse = animalService.getById(animalRequest.id)
//        Assertions.assertEquals(animalRequest.id, result.id)
//        assertAnimalDTO(animalRequest, result)
//        verify(animalRepository, times(1)).findById(animalRequest.id)
//    }
//
//    @Test
//    fun shouldNotReturnAnimalByIdIfAnimalNotFound() {
//        val animalRequest: AnimalRequest = buildAnimalDTO(true)
//        `when`(animalRepository.findById(animalRequest.id)).thenReturn(Optional.empty())
//        Assertions.assertThrows<ResourceNotFoundException>(
//            ResourceNotFoundException::class.java,
//            Executable { animalService.deleteById(animalRequest.id) })
//        verify(animalRepository, times(1)).findById(animalRequest.id)
//    }
//
//    @Test
//    fun shouldDeleteAnimal() {
//        val animalRequest: AnimalRequest = buildAnimalDTO(true)
//        `when`(animalRepository.findById(animalRequest.id)).thenReturn(Optional.of(animalRequest.toAnimal()))
//        val result: AnimalResponse = animalService.deleteById(animalRequest.id)
//        verify(animalRepository, times(1)).deleteById(animalRequest.id)
//        verify(animalRepository, times(1)).findById(animalRequest.id)
//        Assertions.assertNotNull(result)
//        Assertions.assertEquals(animalRequest.id, result.id)
//    }
//
//    @Test
//    fun shouldNotDeleteAnimalIfAnimalNotFound() {
//        val animalRequest: AnimalRequest = buildAnimalDTO(true)
//        `when`(animalRepository.findById(animalRequest.id)).thenReturn(Optional.empty())
//        Assertions.assertThrows<ResourceNotFoundException>(
//            ResourceNotFoundException::class.java,
//            Executable { animalService.deleteById(animalRequest.id) })
//        verify(animalRepository, times(0)).deleteById(animalRequest.id)
//        verify(animalRepository, times(1)).findById(animalRequest.id)
//    }

//    companion object {
//        private fun assertAnimalDTO(animalRequest: AnimalRequest, result: AnimalResponse) {
//            Assertions.assertAll(
//                Executable { Assertions.assertEquals(animalRequest.name, result.name) },
//                Executable { assertEquals(animalRequest.estimatedAge.toEstimatedAge(), result.estimatedAge) },
//                Executable { Assertions.assertEquals(animalRequest.animalType, result.animalType) },
//                Executable { Assertions.assertEquals(animalRequest.medicalRecordId, result.medicalRecordId) }
//            )
//        }
//    }
//}