package br.com.shelterhubmanagementapi.service

import br.com.shelterhubmanagementapi.domain.model.toResponse
import br.com.shelterhubmanagementapi.dto.request.StatusRequest
import br.com.shelterhubmanagementapi.dto.request.toStatus
import br.com.shelterhubmanagementapi.dto.response.StatusResponse
import br.com.shelterhubmanagementapi.exception.PersistenceFailedException
import br.com.shelterhubmanagementapi.exception.ResourceNotFoundException
import br.com.shelterhubmanagementapi.repository.StatusRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.toList
import org.slf4j.LoggerFactory
import org.slf4j.event.Level
import org.springframework.stereotype.Service
import java.util.*


@Service
class StatusService(
    private val statusRepository: StatusRepository,
) {
    private val log = LoggerFactory.getLogger(StatusService::class.java)

    suspend fun create(statusRequest: StatusRequest) =
        coroutineScope {
            async(Dispatchers.IO) {
                try {
                    val createdStatus = statusRepository.save(statusRequest.toStatus())
                    log.makeLoggingEventBuilder(Level.INFO)
                        .setMessage("Status was saved succesfully!")
                        .addKeyValue("statusId", createdStatus.id)
                        .log()
                    createdStatus.toResponse()
                } catch (ex: Exception) {
                    throw PersistenceFailedException(ex.message)
                }
            }
        }

    suspend fun updateById(statusRequest: StatusRequest): StatusResponse? {
        statusRequest.id?.let {
            val hasId = statusRepository.existsById(it)
            val status = statusRequest.toStatus()
            return statusRepository.save(status).toResponse()
        } ?: {
            throw ResourceNotFoundException("There's no such ID")
        }
        return null
    }

    suspend fun getAll(): Deferred<List<StatusResponse>> =
        coroutineScope {
            async(Dispatchers.IO) {
                statusRepository
                    .findAll()
                    .mapNotNull { it.toResponse() }
                    .toList()
            }
        }

    suspend fun getStatusById(statusId: UUID): Deferred<StatusResponse> =
        coroutineScope {
            async(Dispatchers.IO) {
                val status = statusRepository.findById(statusId)
                status?.toResponse() ?: throw ResourceNotFoundException()
            }
        }

    suspend fun  deleteById(statusId: UUID) =
        coroutineScope {
            launch(Dispatchers.IO) {
                val status = statusRepository.findById(statusId)
                status?.let {
                    statusRepository.deleteById(it.id)

                    log.makeLoggingEventBuilder(Level.INFO)
                        .setMessage("Status was deleted.")
                        .addKeyValue("statusId", status.id.toString())
                        .log()

                    it.toResponse()
                } ?: throw ResourceNotFoundException()
            }
        }
}







































