package br.com.shelterhubmanagementapi.service

import br.com.shelterhubmanagementapi.domain.model.Animal
import br.com.shelterhubmanagementapi.domain.model.toResponse
import br.com.shelterhubmanagementapi.dto.request.AnimalRequest
import br.com.shelterhubmanagementapi.dto.request.toAnimal
import br.com.shelterhubmanagementapi.dto.response.AnimalResponse
import br.com.shelterhubmanagementapi.exception.InvalidValueException
import br.com.shelterhubmanagementapi.exception.PersistenceFailedException
import br.com.shelterhubmanagementapi.exception.ResourceNotFoundException
import br.com.shelterhubmanagementapi.repository.AnimalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull
import org.slf4j.LoggerFactory
import org.slf4j.event.Level
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class AnimalService(private val animalRepository: AnimalRepository) {
    private val log = LoggerFactory.getLogger(AnimalService::class.java)

    suspend fun create(animalRequest: AnimalRequest): AnimalResponse {
        if (animalRequest.animalType.isBlank()) {
            throw InvalidValueException("animalType cannot be empty or null.")
        }
        return try {
            val createdAnimal = animalRepository.save(animalRequest.toAnimal())
            log.makeLoggingEventBuilder(Level.INFO)
                .setMessage("Animal was saved with success.")
                .addKeyValue("animalId", createdAnimal?.id.toString())
                .log()
            createdAnimal!!.toResponse()
        } catch (ex: Exception) {
            throw PersistenceFailedException(ex.localizedMessage)
        }
    }

    suspend fun updateById(
        animalRequest: AnimalRequest,
        animalId: UUID,
    ): AnimalResponse {
        val exists = animalRepository.existsById(animalId)
        if (!exists) throw ResourceNotFoundException()
        val animal: Animal = animalRequest.toAnimal()
        val updatedAnimal = animalRepository.save(animal)
        return updatedAnimal.toResponse()
    }

    suspend fun getAll(): Flow<AnimalResponse> =
        animalRepository
            .findAll()
            .mapNotNull { it.toResponse() }

    suspend fun getAnimalById(animalId: UUID): AnimalResponse {
        val animal = animalRepository.findById(animalId)

        return animal?.toResponse() ?: throw ResourceNotFoundException()
    }

    suspend fun deleteById(animalId: UUID): AnimalResponse {
        val animal = animalRepository.findById(animalId)
        animal?.let {
            animalRepository.deleteById(it.id)

            log.makeLoggingEventBuilder(Level.INFO)
                .setMessage("Animal was deleted with success.")
                .addKeyValue("animalId", animal.id.toString())
                .log()

            return it.toResponse()
        } ?: throw ResourceNotFoundException()
    }
}
