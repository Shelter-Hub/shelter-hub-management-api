package com.shelterhub.controller

import br.com.shelterhubmanagementapi.controller.MedicalRecordController
import br.com.shelterhubmanagementapi.domain.model.toResponse
import br.com.shelterhubmanagementapi.dto.request.toMedicalRecord
import br.com.shelterhubmanagementapi.dto.response.AnimalResponse
import br.com.shelterhubmanagementapi.dto.response.MedicalRecordResponse
import br.com.shelterhubmanagementapi.exception.GlobalExceptionHandler
import br.com.shelterhubmanagementapi.service.MedicalRecordService
import com.ninjasquad.springmockk.MockkBean
import com.shelterhub.utils.MedicalRecordUtils
import io.mockk.Called
import io.mockk.coEvery
import io.mockk.coVerify
import kotlinx.coroutines.CompletableDeferred
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody

@WebFluxTest(controllers = [MedicalRecordController::class])
@ContextConfiguration(classes = [MedicalRecordController::class, MedicalRecordService::class, GlobalExceptionHandler::class])
class MedicalRecordControllerTest(
    @Autowired private val webTestClient: WebTestClient,
) {
    private val PATH_URL = "/v1/medical-record"

    @MockkBean
    private lateinit var medicalRecordService: MedicalRecordService

    @Test
    fun `should return all medical records`() {
        val firstMedicalRecord: MedicalRecordResponse =
            MedicalRecordUtils.buildMedicalRecordResponse()

        val secondMedicalRecord: MedicalRecordResponse =
            MedicalRecordUtils.buildMedicalRecordResponse()

        coEvery { medicalRecordService.getAll() } returns CompletableDeferred(listOf(firstMedicalRecord, secondMedicalRecord))

        webTestClient.get()
            .uri { it.path(PATH_URL).build() }
            .exchange()
            .expectStatus().isOk
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody<List<MedicalRecordResponse>>()

        coVerify(exactly = 1) { medicalRecordService.getAll() }
        coVerify { medicalRecordService.getAll() wasNot Called }
    }

    @Test
    fun `should return not found when all medical records does not exist`() {
        coEvery { medicalRecordService.getAll() } returns CompletableDeferred(emptyList())

        webTestClient.get()
            .uri { it.path(PATH_URL).build() }
            .exchange()
            .expectStatus().isNotFound

        coVerify(exactly = 1) { medicalRecordService.getAll() }
        coVerify { medicalRecordService.getAll() wasNot Called }
    }

    @Test
    fun `should return medical record by id`() {
        val medicalRecordRequest = MedicalRecordUtils.buildMedicalRecordRequest()
        val medicalRecordId = medicalRecordRequest.id
        val medicalResponse = MedicalRecordUtils.buildMedicalRecordResponse()

        coEvery { medicalRecordService.getById(medicalRecordId) } returns CompletableDeferred(medicalResponse)

        webTestClient.get().uri { uriBuilder ->
            uriBuilder
                .path("$PATH_URL/{id}")
                .build(medicalRecordId)
        }.exchange()
            .expectStatus().isOk
            .expectBody<MedicalRecordResponse>()

        coVerify { medicalRecordService.getById(medicalRecordId) wasNot Called }
    }

    @Test
    fun `should return not found when medical record does not exist`() {
        coEvery { medicalRecordService.getAll() } returns CompletableDeferred(emptyList())

        webTestClient.get()
            .uri { it.path(PATH_URL).build() }
            .exchange()
            .expectStatus().isNotFound

        coVerify(exactly = 1) { medicalRecordService.getAll() }
    }

    @Test
    fun `should create medical record successfully`() {
        val medicalRecordRequest = MedicalRecordUtils.buildMedicalRecordRequest()
        val medicalRecord = medicalRecordRequest.toMedicalRecord()
        val medicalRecordResponse = medicalRecord.toResponse()

        coEvery { medicalRecordService.create(medicalRecordRequest) } returns CompletableDeferred(medicalRecordResponse)

        webTestClient.post()
            .uri { it.path(PATH_URL).build() }
            .bodyValue(medicalRecordRequest)
            .exchange()
            .expectStatus().isCreated
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody<AnimalResponse>()

        coVerify(exactly = 1) { medicalRecordService }
    }

    @Test
    fun `should update medical record successfully`() {
        val medicalRecordRequest = MedicalRecordUtils.buildMedicalRecordRequest()
        val medicalRecordId = medicalRecordRequest.id
        val medicalRecord = medicalRecordRequest.toMedicalRecord()
        val medicalRecordResponse = medicalRecord.toResponse()

        coEvery { medicalRecordService.update(medicalRecordRequest, medicalRecordId) } returns CompletableDeferred(medicalRecordResponse)

        webTestClient.post()
            .uri { it.path(PATH_URL).build() }
            .bodyValue(medicalRecordResponse)
            .exchange()
            .expectStatus().isCreated
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody<AnimalResponse>()

        coVerify { medicalRecordService.update(medicalRecordRequest, medicalRecordId) }
    }

    @Test
    fun `should delete medical record successfully`() {
        val medicalRecordId = MedicalRecordUtils.buildMedicalRecord().id

        webTestClient.delete()
            .uri { uriBuilder ->
                uriBuilder
                    .path("$PATH_URL/{id}")
                    .build(medicalRecordId)
            }.exchange()
            .expectStatus().isNotFound

        coVerify(exactly = 1) { medicalRecordService.deleteById(medicalRecordId) }
    }
}
