package br.com.shelterhubmanagementapi.controller

import br.com.shelterhubmanagementapi.dto.request.AnimalStatusRequest
import br.com.shelterhubmanagementapi.dto.response.AnimalStatusResponse
import br.com.shelterhubmanagementapi.service.AnimalStatusService
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

@RestController
@RequestMapping(value = ["v1/animalStatus"], produces = [MediaType.APPLICATION_JSON_VALUE])
class AnimalStatusController(private val animalStatusService: AnimalStatusService) {

    @GetMapping("/{id}")
    @ResponseBody
    suspend fun getById(
        @PathVariable id: Long
    ): AnimalStatusResponse {
        return animalStatusService.getAnimalStatusById(id).await()
    }

    @GetMapping
    suspend fun getAll(): ResponseEntity<List<AnimalStatusResponse>> {
        val animalStatus = animalStatusService.getAll()
        return ResponseEntity.ofNullable(animalStatus.await())
    }

    @PostMapping
    suspend fun create(
        @RequestBody animalStatusRequest: AnimalStatusRequest
    ): ResponseEntity<AnimalStatusResponse> {
        val animalStatus = animalStatusService.create(animalStatusRequest).await()
        val id = animalStatus.id.toString()
        val location = URI.create(id)

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .location(location)
            .body(animalStatus)
    }

    @PutMapping("/{id}")
    suspend fun updateAnimalStatus(
        @PathVariable id: Long,
        @RequestBody animalStatus: AnimalStatusRequest
    ): ResponseEntity<Any> {
        val (savedAnimalStatus) = animalStatusService.updateById(animalStatus, id)
        return ResponseEntity.ok(savedAnimalStatus.await())
    }

    @DeleteMapping("/{id}")
    suspend fun deleteAnimal(
        @PathVariable id: Long
    ): ResponseEntity<Void> {
        animalStatusService.deleteById(id)
        return ResponseEntity.noContent().build()
    }
}
