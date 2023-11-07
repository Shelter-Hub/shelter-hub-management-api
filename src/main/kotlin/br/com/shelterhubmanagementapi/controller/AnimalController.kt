package br.com.shelterhubmanagementapi.controller

import br.com.shelterhubmanagementapi.dto.request.AnimalRequest
import br.com.shelterhubmanagementapi.dto.response.AnimalResponse
import br.com.shelterhubmanagementapi.service.AnimalService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.http.server.reactive.ServerHttpRequest
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
@RequestMapping(value = ["v1/animal"], produces = [MediaType.APPLICATION_JSON_VALUE])
class AnimalController(private val animalService: AnimalService) {
    @GetMapping("/{id}")
    @ResponseBody
    suspend fun getById(
        @PathVariable id: UUID,
    ): AnimalResponse {
        return animalService.getAnimalById(id).await()
    }

    @GetMapping
    suspend fun getAll(): ResponseEntity<List<AnimalResponse>> {
        val animals = animalService.getAll().await()

        if (animals.isEmpty()) {
            return ResponseEntity.notFound().build()
        }

        return ResponseEntity
            .ok(animals)
    }

    @PostMapping
    suspend fun create(
        @RequestBody animalRequest: AnimalRequest,
    ): ResponseEntity<AnimalResponse> {
        val animal = animalService.create(animalRequest).await()
        val id = animal.id.toString()
        val location = URI.create(id)

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .location(location)
            .body(animal)
    }

    @PutMapping("/{id}")
    suspend fun updateAnimal(
        @PathVariable id: UUID,
        @RequestBody animal: AnimalRequest,
        serverHttpRequest: ServerHttpRequest,
    ): ResponseEntity<Any> {
        val (animalExistsBefore, savedAnimal) = animalService.updateById(animal, id)
        return ResponseEntity.ok(savedAnimal.await())
    }

    @DeleteMapping("/{id}")
    suspend fun deleteAnimal(
        @PathVariable id: UUID,
    ): ResponseEntity<Void> {
        animalService.deleteById(id)
        return ResponseEntity.noContent().build()
    }
}
