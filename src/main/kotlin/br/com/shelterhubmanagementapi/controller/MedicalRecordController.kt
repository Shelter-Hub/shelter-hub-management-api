package br.com.shelterhubmanagementapi.controller

import br.com.shelterhubmanagementapi.dto.request.MedicalRecordRequest
import br.com.shelterhubmanagementapi.dto.response.MedicalRecordResponse
import br.com.shelterhubmanagementapi.service.MedicalRecordService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping(value = ["/v1/medical-record"], produces = [MediaType.APPLICATION_JSON_VALUE])
class MedicalRecordController(private val medicalRecordService: MedicalRecordService) {
    @GetMapping
    suspend fun getAll(): ResponseEntity<List<MedicalRecordResponse>> {
        return withContext(Dispatchers.IO) {
            val medicalRecords = medicalRecordService.getAll()

            if (medicalRecords.isEmpty()) {
                ResponseEntity.notFound().build()
            } else {
                ResponseEntity.ok(medicalRecords)
            }
        }
    }

    @GetMapping("/{id}")
    suspend fun getById(
        @PathVariable id: UUID,
    ): ResponseEntity<MedicalRecordResponse> {
        return withContext(Dispatchers.IO) {
            val medicalRecord = medicalRecordService.getById(id)
            ResponseEntity.ok(medicalRecord)
        }
    }

    @PostMapping
    suspend fun create(
        @RequestBody medicalRecord: MedicalRecordRequest,
    ): ResponseEntity<MedicalRecordResponse> {
        val medicalResponse = medicalRecordService.create(medicalRecord)
        return ResponseEntity.ofNullable(medicalResponse)
    }

    @PutMapping("/{id}")
    suspend fun update(
        @PathVariable id: UUID,
        @RequestBody medicalRecord: MedicalRecordRequest,
    ): ResponseEntity<MedicalRecordResponse> {
        val medicalRecordPersisted = medicalRecordService.update(medicalRecord, id)
        return ResponseEntity.ofNullable(medicalRecordPersisted)
    }

    @DeleteMapping("/{id}")
    suspend fun delete(
        @PathVariable id: UUID,
    ): ResponseEntity<Void> {
        medicalRecordService.deleteById(id)
        return ResponseEntity.accepted().build()
    }
}
