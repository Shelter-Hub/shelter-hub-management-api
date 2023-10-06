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
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.toList
import org.slf4j.LoggerFactory
import org.slf4j.event.Level
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class AnimalService(
    private val animalRepository: AnimalRepository,
    private val medicalRecordService: MedicalRecordService,
) {
    private val log = LoggerFactory.getLogger(AnimalService::class.java)

    suspend fun create(animalRequest: AnimalRequest): AnimalResponse {
        if (animalRequest.animalType.isBlank()) {
            throw InvalidValueException("animalType cannot be empty or null.")
        }
        return try {
            val createdAnimal = animalRepository.save(animalRequest.toAnimal())
            log.makeLoggingEventBuilder(Level.INFO)
                .setMessage("Animal was saved with success.")
                .addKeyValue("animalId", createdAnimal.id)
                .log()
            createdAnimal.toResponse()
        } catch (ex: Exception) {
            throw PersistenceFailedException(ex.localizedMessage)
        }
    }

    suspend fun updateById(
        animalRequest: AnimalRequest,
        animalId: UUID,
    ): Pair<Deferred<Boolean>, Deferred<AnimalResponse>> =
        coroutineScope {
            val animalExistsBefore =
                async(Dispatchers.IO) {
                    animalRepository.existsById(animalId)
                }
            val savedAnimal =
                async(Dispatchers.IO) {
                    val animal: Animal = animalRequest.toAnimal()
                    animalRepository.save(animal).toResponse()
                }
            return@coroutineScope Pair(animalExistsBefore, savedAnimal)
        }

    suspend fun getAll(): Deferred<List<AnimalResponse>> =
        coroutineScope {
            async(Dispatchers.IO) {
                animalRepository
                    .findAll()
                    .mapNotNull { it.toResponse() }
                    .toList()
            }
        }

    suspend fun getAnimalById(animalId: UUID): Deferred<AnimalResponse> =
        coroutineScope {
            return@coroutineScope async(Dispatchers.IO) {
                val animal = animalRepository.findById(animalId)
                animal?.toResponse() ?: throw ResourceNotFoundException()
            }
        }

    suspend fun deleteById(animalId: UUID): AnimalResponse {
        val animal = animalRepository.findById(animalId)
        animal?.let {
            animalRepository.deleteById(it.id)
            medicalRecordService.deleteById(it.medicalRecordId)

            log.makeLoggingEventBuilder(Level.INFO)
                .setMessage("Animal was deleted with success with its medicalRecord.")
                .addKeyValue("animalId", animal.id.toString())
                .log()

            return it.toResponse()
        } ?: throw ResourceNotFoundException()
    }
}
