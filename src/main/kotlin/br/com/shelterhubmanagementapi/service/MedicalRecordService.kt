package br.com.shelterhubmanagementapi.service

import br.com.shelterhubmanagementapi.domain.model.MedicalRecord
import br.com.shelterhubmanagementapi.domain.model.toResponse
import br.com.shelterhubmanagementapi.dto.request.MedicalRecordRequest
import br.com.shelterhubmanagementapi.dto.request.toMedicalRecord
import br.com.shelterhubmanagementapi.dto.response.MedicalRecordResponse
import br.com.shelterhubmanagementapi.exception.PersistenceFailedException
import br.com.shelterhubmanagementapi.exception.ResourceNotFoundException
import br.com.shelterhubmanagementapi.exception.UnknownErrorException
import br.com.shelterhubmanagementapi.repository.MedicalRecordRepository
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import org.slf4j.LoggerFactory
import org.slf4j.event.Level
import org.springframework.dao.DataAccessException
import java.util.UUID

class MedicalRecordService(
    private val medicalRecordRepository: MedicalRecordRepository,
) {
    private val log = LoggerFactory.getLogger(MedicalRecordService::class.java)

    suspend fun create(medicalRecordRequest: MedicalRecordRequest): Deferred<MedicalRecordResponse> =
        coroutineScope {
            async(Dispatchers.IO) {
                try {
                    val medicalRecord: MedicalRecord =
                        medicalRecordRepository.save(medicalRecordRequest.toMedicalRecord())

                    log.makeLoggingEventBuilder(Level.INFO)
                        .setMessage("Medical record was saved with success.")
                        .addKeyValue("medicalRecordId", medicalRecord.id.toString())
                        .log()

                    medicalRecord.toResponse()
                } catch (ex: DataAccessException) {
                    throw PersistenceFailedException(ex.localizedMessage)
                } catch (ex: Exception) {
                    throw UnknownErrorException(ex.localizedMessage)
                }
            }
        }

    suspend fun update(
        medicalRecordRequest: MedicalRecordRequest,
        uuid: UUID,
    ): Deferred<MedicalRecordResponse> =
        coroutineScope {
            async {
                try {
                    val medicalRecord: MedicalRecord = medicalRecordRequest.toMedicalRecord()
                    val medicalRecordResult = medicalRecordRepository.save(medicalRecord.copy(uuid))
                    log.makeLoggingEventBuilder(Level.INFO)
                        .setMessage("Medical record was updated with success.")
                        .addKeyValue("medicalRecordId", medicalRecord.id.toString())
                        .log()
                    medicalRecordResult.toResponse()
                } catch (ex: DataAccessException) {
                    throw PersistenceFailedException(ex.localizedMessage)
                } catch (ex: Exception) {
                    throw UnknownErrorException(ex.localizedMessage)
                }
            }
        }

    suspend fun getAll(): Deferred<List<MedicalRecordResponse>> =
        coroutineScope {
            async(Dispatchers.IO) {
                medicalRecordRepository
                    .findAll()
                    .mapNotNull { it.toResponse() }
                    .toList()
            }
        }

    suspend fun getById(medicalRecordId: UUID): Deferred<MedicalRecordResponse> =
        coroutineScope {
            async {
                val medicalRecord =
                    medicalRecordRepository
                        .findById(medicalRecordId)
                medicalRecord?.toResponse() ?: throw ResourceNotFoundException()
            }
        }

    suspend fun deleteById(id: UUID) =
        coroutineScope {
            launch(Dispatchers.IO) {
                medicalRecordRepository.deleteById(id)
                log.makeLoggingEventBuilder(Level.INFO)
                    .setMessage("Medical record was deleted with success.")
                    .addKeyValue("medicalRecordId", id)
                    .log()
            }
        }
}
