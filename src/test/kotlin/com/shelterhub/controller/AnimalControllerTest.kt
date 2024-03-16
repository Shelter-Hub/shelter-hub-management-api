package com.shelterhub.controller

import br.com.shelterhubmanagementapi.controller.AnimalController
import br.com.shelterhubmanagementapi.domain.model.toResponse
import br.com.shelterhubmanagementapi.dto.request.toAnimal
import br.com.shelterhubmanagementapi.dto.response.AnimalResponse
import br.com.shelterhubmanagementapi.exception.GlobalExceptionHandler
import br.com.shelterhubmanagementapi.exception.ResourceNotFoundException
import br.com.shelterhubmanagementapi.service.AnimalService
import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import com.shelterhub.utils.AnimalTestUtils
import io.mockk.Called
import io.mockk.coEvery
import io.mockk.coVerify
import kotlinx.coroutines.CompletableDeferred
import net.datafaker.Faker
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody
import java.util.UUID

@WebFluxTest(controllers = [AnimalController::class])
@ContextConfiguration(classes = [AnimalController::class, AnimalService::class, GlobalExceptionHandler::class])
class AnimalControllerTest(
        @Autowired private val webTestClient: WebTestClient,
) {
    @MockkBean
    private lateinit var animalService: AnimalService

    private val PATH_URL = "/v1/animal"

    @Test
    fun `should return animal by id`() {
        val animalRequest = AnimalTestUtils.buildAnimalDTO()
        val animalId = animalRequest.id
        val animalResponse: AnimalResponse =
            animalRequest.toAnimal()
                .toResponse()

        coEvery { animalService.getById(animalId) } returns CompletableDeferred(animalResponse)

        webTestClient.get().uri { uriBuilder -> uriBuilder.path("$PATH_URL/{id}").build(animalId) }
            .exchange()
            .expectStatus().isOk
            .expectBody<AnimalResponse>()

        coVerify { animalService.getById(animalId) wasNot Called }
    }

    @Test
    fun `should not get animal by id if animal was not found`() {
        val animalId = UUID.randomUUID()

        coEvery { animalService.getById(animalId) } throws ResourceNotFoundException()

        webTestClient.get()
            .uri { uriBuilder ->
                uriBuilder
                    .path("$PATH_URL/{id}")
                    .build(animalId)
            }
            .exchange()
            .expectStatus().value { HttpStatus.NOT_FOUND }

        coVerify { animalService.getById(animalId) wasNot Called }
    }

    @Test
    fun `should not get animal by id if UUID is null`() {
        webTestClient.get()
            .uri { uriBuilder ->
                uriBuilder
                    .path("$PATH_URL/{id}")
                    .build(null)
            }
            .exchange()
            .expectStatus().isNotFound
    }

    @Test
    fun `should return all animals`() {
        val firstAnimal: AnimalResponse = AnimalTestUtils.buildAnimalDTO().toAnimal().toResponse()
        val secondAnimal: AnimalResponse = AnimalTestUtils.buildAnimalDTO().toAnimal().toResponse()

        coEvery { animalService.getAll() } returns CompletableDeferred(listOf(firstAnimal, secondAnimal))

        webTestClient.get()
            .uri { it.path(PATH_URL).build() }
            .exchange()
            .expectStatus().isOk
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody<List<AnimalResponse>>()

        coVerify(exactly = 1) { animalService.getAll() }
        coVerify { animalService.getAll() wasNot Called }
    }

    @Test
    fun `should return not found when all animals does not exist`() {
        coEvery { animalService.getAll() } returns CompletableDeferred(emptyList())

        webTestClient.get()
            .uri { it.path(PATH_URL).build() }
            .exchange()
            .expectStatus().isNotFound

        coVerify(exactly = 1) { animalService.getAll() }
        coVerify { animalService.getAll() wasNot Called }
    }

    @Test
    fun `should create animal successfully`() {
        val animalRequest = AnimalTestUtils.buildAnimalDTO().copy(id = UUID.randomUUID())
        val animalEntity = animalRequest.toAnimal()
        val animalResponse = animalEntity.toResponse()

        coEvery { animalService.create(animalRequest) } returns CompletableDeferred(animalResponse)

        webTestClient.post()
            .uri { it.path(PATH_URL).build() }
            .bodyValue(animalRequest)
            .exchange()
            .expectStatus().isCreated
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody<AnimalResponse>()

        coVerify(exactly = 1) { animalService.create(animalRequest) }
    }

    @Test
    fun `should not create animal with invalid size`() {
        val animalRequest = AnimalTestUtils.buildAnimalDTO().copy(id = UUID.randomUUID())
        val invalidSize = Faker().lorem().characters(10)
        val invalidJsonPayload =
            ObjectMapper()
                .writeValueAsString(animalRequest)
                .replace("\"size\":\"" + animalRequest.size + "\"", "\"size\":\"$invalidSize\"")

        webTestClient.post()
            .uri { it.path(PATH_URL).build() }
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(invalidJsonPayload)
            .exchange()
            .expectStatus().isBadRequest
    }

    @Test
    fun `should not create animal with invalid gender`() {
        val animalRequest = AnimalTestUtils.buildAnimalDTO().copy(id = UUID.randomUUID())
        val invalidGender = Faker().lorem().characters(10)
        val invalidJsonPayload =
            ObjectMapper()
                .writeValueAsString(animalRequest)
                .replace(
                    "\"gender\":\"" + animalRequest.gender + "\"",
                    "\"gender\":\"$invalidGender\"",
                )
        webTestClient.post()
            .uri { it.path(PATH_URL).build() }
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(invalidJsonPayload)
            .exchange()
            .expectStatus().isBadRequest
    }

    @Test
    fun `should update animal`() {
        val id = UUID.randomUUID()
        val animalRequest = AnimalTestUtils.buildAnimalDTO().copy(id = id)
        val animalEntity = animalRequest.toAnimal()
        val animalResponse = animalEntity.toResponse()

        coEvery { animalService.updateById(animalRequest, id) } returns
            Pair(
                CompletableDeferred(true),
                CompletableDeferred(animalResponse),
            )

        webTestClient.put()
            .uri { uriBuilder ->
                uriBuilder
                    .path("$PATH_URL/{id}")
                    .build(id)
            }
            .bodyValue(animalRequest)
            .exchange()
            .expectStatus().isOk
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody<AnimalResponse>()

        coVerify(exactly = 1) { animalService.updateById(animalRequest, id) }
    }

    @Test
    fun `should not update animal if animal was not found`() {
        val id = UUID.randomUUID()
        val animalRequest = AnimalTestUtils.buildAnimalDTO().copy(id = id)

        coEvery { animalService.updateById(animalRequest, id) } throws ResourceNotFoundException()

        webTestClient.put()
            .uri { uriBuilder ->
                uriBuilder
                    .path("$PATH_URL/{id}")
                    .build(id)
            }
            .bodyValue(animalRequest)
            .exchange()
            .expectStatus().isNotFound
            .expectBody<AnimalResponse>()

        coVerify(exactly = 1) { animalService.updateById(animalRequest, id) }
    }

    @Test
    fun `should delete animal`() {
        val id = UUID.randomUUID()
        val animalRequest = AnimalTestUtils.buildAnimalDTO().copy(id = id)
        val animalEntity = animalRequest.toAnimal()
        val animalResponse = animalEntity.toResponse()

        coEvery { animalService.deleteById(id) } returns CompletableDeferred(animalResponse)

        webTestClient.delete()
            .uri { uriBuilder ->
                uriBuilder
                    .path("$PATH_URL/{id}")
                    .build(id)
            }
            .exchange()
            .expectStatus().isNoContent

        coVerify(exactly = 1) { animalService.deleteById(id) }
    }

    @Test
    fun `should not delete animal if animal was not found`() {
        val id = UUID.randomUUID()

        coEvery { animalService.deleteById(id) } throws ResourceNotFoundException()

        webTestClient.delete()
            .uri { uriBuilder ->
                uriBuilder
                    .path("$PATH_URL/{id}")
                    .build(id)
            }
            .exchange()
            .expectStatus().isNotFound

        coVerify(exactly = 1) { animalService.deleteById(id) }
    }
}
