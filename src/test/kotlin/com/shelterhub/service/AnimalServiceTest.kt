package com.shelterhub.service

import br.com.shelterhubmanagementapi.domain.model.Animal
import br.com.shelterhubmanagementapi.dto.request.AnimalRequest
import br.com.shelterhubmanagementapi.dto.request.toAnimal
import br.com.shelterhubmanagementapi.dto.request.toEstimatedAge
import br.com.shelterhubmanagementapi.dto.response.AnimalResponse
import br.com.shelterhubmanagementapi.exception.PersistenceFailedException
import br.com.shelterhubmanagementapi.exception.ResourceNotFoundException
import br.com.shelterhubmanagementapi.repository.AnimalRepository
import br.com.shelterhubmanagementapi.service.AnimalService
import br.com.shelterhubmanagementapi.service.MedicalRecordService
import com.shelterhub.utils.AnimalTestUtils.buildAnimalDTO
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.function.Executable
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
class AnimalServiceTest {
    @MockK
    private lateinit var animalRepository: AnimalRepository

    @MockK
    private lateinit var medicalRecordService: MedicalRecordService

    @InjectMockKs
    private lateinit var animalService: AnimalService

    @Test
    fun `should return created animal DTO`() {
        val animalRequest: AnimalRequest = buildAnimalDTO("Canine")
        coEvery { animalRepository.save(animalRequest.toAnimal()) } returns animalRequest.toAnimal()

        val result = runBlocking { animalService.create(animalRequest).await() }
        assertAnimalDTO(animalRequest, result)

        coVerify(exactly = 1) { animalRepository.save(any()) }
    }

    @Test
    fun `should throw persistence failed exception when creating animal`() {
        val animalRequest: AnimalRequest = buildAnimalDTO("Canine")

        coEvery { animalRepository.save(animalRequest.toAnimal()) } throws RuntimeException()

        assertThrows<PersistenceFailedException> {
            runBlocking { animalService.create(animalRequest) }
        }

        coVerify(exactly = 1) { animalRepository.save(any<Animal>()) }
    }

    @Test
    fun `should return updated animal dto`() {
        val animalRequest: AnimalRequest = buildAnimalDTO("Canine")

        coEvery { animalRepository.save(any<Animal>()) } returns animalRequest.toAnimal()

        val result = runBlocking { animalService.updateById(animalRequest, animalRequest.id).await() }
        Assertions.assertEquals(animalRequest.id.toString(), result.id)
        assertAnimalDTO(animalRequest, result)
        coVerify(exactly = 1) { animalRepository.save(any<Animal>()) }
    }

    @Test
    fun `should return list of animal dtos`() {
        val animal1: Animal = buildAnimalDTO("Canine").toAnimal()
        val animal2: Animal = buildAnimalDTO("Canine").toAnimal()
        val animals = listOf(animal1, animal2)

        coEvery { animalRepository.findAll() } returns animals.asFlow()
        val result = runBlocking { animalService.getAll().await() }
        Assertions.assertEquals(animals.size, result.size)
        coVerify(exactly = 1) { animalRepository.findAll() }
    }

    @Test
    fun `should return animal by id`() {
        val animalRequest: AnimalRequest = buildAnimalDTO("Canine")
        coEvery { animalRepository.findById(animalRequest.id) } returns animalRequest.toAnimal()
        val result = runBlocking { animalService.getById(animalRequest.id).await() }

        Assertions.assertEquals(animalRequest.id.toString(), result.id)
        assertAnimalDTO(animalRequest, result)
        coVerify(exactly = 1) { animalRepository.findById(animalRequest.id) }
    }

    @Test
    fun `should not return animal by id if animal not found`() {
        val animalRequest: AnimalRequest = buildAnimalDTO("Canine")
        coEvery { animalRepository.findById(animalRequest.id) } returns null

        assertThrows<ResourceNotFoundException> {
            runBlocking { animalService.deleteById(animalRequest.id) }
        }

        coVerify(exactly = 1) { animalRepository.findById(animalRequest.id) }
    }

    @Test
    fun `should delete animal`() {
        val animalRequest: AnimalRequest = buildAnimalDTO("Canine")
        coEvery { animalRepository.findById(animalRequest.id) } returns animalRequest.toAnimal()

        coJustRun { animalRepository.deleteById(animalRequest.id) }
        coJustRun { medicalRecordService.deleteById(animalRequest.medicalRecordId.toString()) }

        runBlocking { animalService.deleteById(animalRequest.id) }
    }

    @Test
    fun `should not delete animal if animal not found`() {
        val animalRequest: AnimalRequest = buildAnimalDTO("Canine")
        coEvery { animalRepository.findById(animalRequest.id) } returns null

        assertThrows<ResourceNotFoundException> {
            runBlocking { animalService.deleteById(animalRequest.id) }
        }

        coVerify(exactly = 0) { animalRepository.deleteById(animalRequest.id) }
        coVerify(exactly = 1) { animalRepository.findById(animalRequest.id) }
    }

    companion object {
        private fun assertAnimalDTO(
            animalRequest: AnimalRequest,
            result: AnimalResponse,
        ) {
            Assertions.assertAll(
                Executable { Assertions.assertEquals(animalRequest.name, result.name) },
                Executable { Assertions.assertEquals(animalRequest.estimatedAge?.toEstimatedAge().toString(), result.estimatedAge) },
                Executable { Assertions.assertEquals(animalRequest.animalType, result.animalType) },
                Executable { Assertions.assertEquals(animalRequest.medicalRecordId, result.medicalRecordId) },
            )
        }
    }
}
