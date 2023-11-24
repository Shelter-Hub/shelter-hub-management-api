package br.com.shelterhubmanagementapi.domain.model


import kotlinx.datetime.LocalDate
import java.util.UUID


data class AnimalStatus(
    val id: Long,
    val animalId: UUID,
    val createDate: LocalDate,
    val statusId: Int,
)