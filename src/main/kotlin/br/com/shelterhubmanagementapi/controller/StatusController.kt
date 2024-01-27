package br.com.shelterhubmanagementapi.controller

import br.com.shelterhubmanagementapi.dto.request.StatusRequest
import br.com.shelterhubmanagementapi.dto.response.StatusResponse
import br.com.shelterhubmanagementapi.service.StatusService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import java.net.URI
import java.util.UUID

@RestController
@RequestMapping(value = ["v1/status"], produces = [MediaType.APPLICATION_JSON_VALUE])
class StatusController(private val statusService: StatusService) {
    @GetMapping("/{id}")
    @ResponseBody
    suspend fun getById(
        @PathVariable id: UUID
    ): StatusResponse {
        return statusService.getStatusById(id).await()
    }

    @GetMapping
    suspend fun getAll(): ResponseEntity<List<StatusResponse>> {
        val statusList = statusService.getAll()
        return ResponseEntity.ofNullable(statusList.await())
    }

    @PostMapping
    suspend fun create(
        @RequestBody statusRequest: StatusRequest
    ): ResponseEntity<StatusResponse> {
        val status = statusService.create(statusRequest).await()
        val id = status.id.toString()
        val location = URI.create(id)

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .location(location)
            .body(status)
    }

    @PutMapping("/{id}")
    suspend fun updateStatus(
        @PathVariable id: UUID,
        @RequestBody statusRequest: StatusRequest
    ): ResponseEntity<Any> {
        val result = statusService.updateById(statusRequest)
        return ResponseEntity.ok(result.toString())
    }

    @DeleteMapping("/{id}")
    suspend fun deleteStatus(
        @PathVariable id: UUID
    ): ResponseEntity<Void> {
        statusService.deleteById(id)
        return ResponseEntity.noContent().build()
    }
}
