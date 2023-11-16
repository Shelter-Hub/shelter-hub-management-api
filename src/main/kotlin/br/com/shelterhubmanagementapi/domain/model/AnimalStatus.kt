package br.com.shelterhubmanagementapi.domain.model

import java.util.UUID
import java.time.LocalDate


data class AnimalStatus(
    val id: Long,
    val animalId: UUID,
    val createDate: LocalDate,
    val statusId: UUID,
)