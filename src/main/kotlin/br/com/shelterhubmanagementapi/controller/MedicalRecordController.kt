package br.com.shelterhubmanagementapi.controller

import br.com.shelterhubmanagementapi.dto.request.MedicalRecordRequest
import br.com.shelterhubmanagementapi.dto.response.MedicalRecordResponse
import br.com.shelterhubmanagementapi.service.MedicalRecordService
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
        val medicalRecords = medicalRecordService.getAll()
        return if (medicalRecords.isEmpty()) {
            ResponseEntity.notFound()
                .build()
        } else {
            ResponseEntity.ok(
                medicalRecords,
            )
        }
    }

    @GetMapping("/{id}")
    suspend fun getById(
        @PathVariable id: UUID,
    ): ResponseEntity<MedicalRecordResponse> {
        val medicalRecordOptional = medicalRecordService.getById(id)
        return ResponseEntity
            .of(medicalRecordOptional)
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
        @PathVariable id: UUID?,
    ): ResponseEntity<*> {
        val medicalRecord = medicalRecordService.deleteById(id)
        return if (medicalRecord.isPresent) {
            ResponseEntity.noContent()
                .build<Unit>()
        } else {
            ResponseEntity.notFound()
                .build<Unit>()
        }
    }
}
