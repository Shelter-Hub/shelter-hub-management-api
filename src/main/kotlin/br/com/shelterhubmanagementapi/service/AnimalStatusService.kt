package br.com.shelterhubmanagementapi.service

import br.com.shelterhubmanagementapi.domain.model.AnimalStatus
import br.com.shelterhubmanagementapi.domain.model.toResponse
import br.com.shelterhubmanagementapi.dto.request.AnimalStatusRequest
import br.com.shelterhubmanagementapi.dto.request.toAnimalStatus
import br.com.shelterhubmanagementapi.dto.response.AnimalStatusResponse
import br.com.shelterhubmanagementapi.exception.PersistenceFailedException
import br.com.shelterhubmanagementapi.exception.ResourceNotFoundException
import br.com.shelterhubmanagementapi.repository.AnimalStatusRepository
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import org.slf4j.LoggerFactory
import org.slf4j.event.Level
import org.springframework.stereotype.Service

@Service
class AnimalStatusService(
    private val animalStatusRepository: AnimalStatusRepository
) {
    private val log = LoggerFactory.getLogger(AnimalStatusService::class.java)

    suspend fun create(animalStatusRequest: AnimalStatusRequest): Deferred<AnimalStatusResponse> =
        coroutineScope {
            async(Dispatchers.IO) {
                try {
                    val createdAnimalStatus = animalStatusRepository.save(animalStatusRequest.toAnimalStatus())

                    log.makeLoggingEventBuilder(Level.INFO)
                        .setMessage("Animal Status was saved succesfully! ")
                        .addKeyValue("animalStatusId", createdAnimalStatus.id)
                        .log()
                    createdAnimalStatus.toResponse()
                } catch (ex: Exception) {
                    throw PersistenceFailedException(ex.message)
                }
            }
        }

    suspend fun getAnimalStatusById(id: Long): Deferred<AnimalStatusResponse> =
        coroutineScope {
            async(Dispatchers.IO) {
                val animalStatus = animalStatusRepository.findById(id)
                animalStatus?.toResponse() ?: throw ResourceNotFoundException()
            }
        }
    suspend fun updateById(
        animalStatusRequest: AnimalStatusRequest,
        id: Long
    ): Pair<Deferred<Boolean>, Deferred<AnimalStatusResponse>> {
        return coroutineScope {
            val animalStatusExistsBerfore =
                async(Dispatchers.IO) {
                    animalStatusRepository.existsById(id)
                }
            val savedAnimalStatus =
                async(Dispatchers.IO) {
                    val animalStatus: AnimalStatus = animalStatusRequest.toAnimalStatus()
                    animalStatusRepository.save(animalStatus).toResponse()
                }
            Pair(animalStatusExistsBerfore, savedAnimalStatus)
        }
    }

    suspend fun getAll(): Deferred<List<AnimalStatusResponse>> =
        coroutineScope {
            async(Dispatchers.IO) {
                animalStatusRepository
                    .findAll()
                    .mapNotNull { it.toResponse() }
                    .toList()
            }
        }

    suspend fun deleteById(id: Long) =
        coroutineScope {
            launch(Dispatchers.IO) {
                val animalStatus = animalStatusRepository.findById(id)
                animalStatus?.let {
                    animalStatusRepository.deleteById(it.id)

                    log.makeLoggingEventBuilder(Level.INFO)
                        .setMessage("AnimalStatus was deleted with success")
                        .log()

                    it.toResponse()
                } ?: throw ResourceNotFoundException()
            }
        }
}
