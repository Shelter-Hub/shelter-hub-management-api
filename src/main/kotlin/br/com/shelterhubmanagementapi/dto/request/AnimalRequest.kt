package br.com.shelterhubmanagementapi.dto.request

import br.com.shelterhubmanagementapi.domain.enums.AnimalType
import br.com.shelterhubmanagementapi.domain.enums.Gender
import br.com.shelterhubmanagementapi.domain.enums.Size
import br.com.shelterhubmanagementapi.domain.model.Animal
import java.lang.NullPointerException
import java.util.UUID

data class AnimalRequest(
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val identification: String,
    val behavior: String,
    val breed: String,
    val history: String,
    val estimatedAge: EstimatedAgeRequest? = null,
    val medicalRecordId: UUID?,
    val animalType: String,
    val gender: Gender,
    val size: Size,
)

fun AnimalRequest.toAnimal() =
    Animal(
        id = this.id,
        name = this.name,
        identification = this.identification,
        behavior = this.behavior,
        breed = this.breed,
        history = this.history,
        estimatedAge = this.estimatedAge?.toLocalDate(),
        medicalRecordId = this.medicalRecordId ?: throw NullPointerException(),
        animalType = AnimalType.getOrDefault(this.animalType),
        gender = this.gender,
        size = this.size,
    )
