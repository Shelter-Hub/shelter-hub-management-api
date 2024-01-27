package br.com.shelterhubmanagementapi.dto.request

import br.com.shelterhubmanagementapi.domain.model.AnimalStatus
import java.time.LocalDate
import java.util.*

data class AnimalStatusRequest(
    val id: Long,
    val animalId: UUID,
    val createDate: LocalDate,
    val statusId: Int
)

fun AnimalStatusRequest.toAnimalStatus() =
    AnimalStatus(
        id = this.id,
        animalId = this.animalId,
        createDate = this.createDate
    )