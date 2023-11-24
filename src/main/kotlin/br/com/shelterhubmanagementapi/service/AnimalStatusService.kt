package br.com.shelterhubmanagementapi.service

import br.com.shelterhubmanagementapi.dto.request.AnimalStatusRequest
import br.com.shelterhubmanagementapi.repository.AnimalStatusRepository
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import org.slf4j.LoggerFactory
import org.slf4j.event.Level
import org.springframework.stereotype.Service


@Service
class AnimalStatusService (
    private val animalStatusRepository: AnimalStatusRepository,
    private val animalStatusService: AnimalStatusService,
) {
    private val log = LoggerFactory.getLogger(AnimalStatusService::class.java)

    suspend fun create(animalStatusRequest: AnimalStatusRequest): Deferred<AnimalStatusResponse> =
        coroutineScope {
            async(Dispatchers.IO) {
                val createdAnimalStatus = animalStatusRepository.save(animalStatusRequest())
                log.makeLoggingEventBuilder(Level.INFO)
                    .setMessage("Animal Status was saved succesfully! ")
                    .addKeyValue("animalStatusId", createdAnimalStatus.id)
                    .log()
                createdAnimalStatus.toAnimalStatusRequest()
            }





        }







}