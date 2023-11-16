package br.com.shelterhubmanagementapi.dto.response

import java.time.LocalDate
import java.util.UUID

class AnimalStatusResponse {
    val id: Long,
    val animalId: UUID,
    val createDate: LocalDate,
    val statusId: UUID,
}