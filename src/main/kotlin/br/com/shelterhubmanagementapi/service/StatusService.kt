package br.com.shelterhubmanagementapi.service

import br.com.shelterhubmanagementapi.dto.request.StatusRequest
import br.com.shelterhubmanagementapi.dto.response.StatusResponse
import br.com.shelterhubmanagementapi.repository.AnimalRepository
import br.com.shelterhubmanagementapi.repository.StatusRepository
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import org.springframework.stereotype.Service
import org.slf4j.LoggerFactory
import org.slf4j.event.Level


@Service
class StatusService(
    private val StatusRepository: StatusRepository,
    private val AnimalReposity: AnimalRepository
    ) {
        private val log = loggerFactory.getLogger(StatusService::class.java)

        suspend fun create(statusRequest: StatusRequest): Deferred<StatusResponse> =
            coroutineScope {
                async(Dispatchers.IO) {
                    val createdStatus = statusRepository.save(statusRequest)
                    log.makeLoggingEventBuilder(Level.INFO)
                        .setMessage("Status was saved succesfully!")
                        .addKeyValue("statusId", createdStatus.id)
                        .log()
                    createdStatus.toResponse()

                }
            }
    }

















    }    }