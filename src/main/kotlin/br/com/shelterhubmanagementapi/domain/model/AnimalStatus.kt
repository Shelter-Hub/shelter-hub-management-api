package br.com.shelterhubmanagementapi.domain.model

import br.com.shelterhubmanagementapi.dto.response.AnimalStatusResponse
import java.time.LocalDate
import java.util.UUID

data class AnimalStatus(
    val id: Long,
    val animalId: UUID,
    val createDate: LocalDate
)

fun AnimalStatus.toResponse(): AnimalStatusResponse =
    AnimalStatusResponse(
        id = this.id,
        animalId = this.animalId,
        createDate = this.createDate
    )
