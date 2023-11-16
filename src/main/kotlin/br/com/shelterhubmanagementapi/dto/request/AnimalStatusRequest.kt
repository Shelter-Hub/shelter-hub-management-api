package br.com.shelterhubmanagementapi.dto.request

import br.com.shelterhubmanagementapi.domain.model.AnimalStatus
import com.fasterxml.jackson.databind.deser.std.UUIDDeserializer
import java.util.UUID

data class AnimalStatusRequest(
    val id: UUID = UUID.randomUUID(),
    val animalId: UUID.randomUUID(),
    val createDate:
    val statusId: UUID,
)

fun AnimalStatusRequest.toAnimalStatusRequest() =
    AnimalStatus(
        id = this.id,
        animalId = this.id,
        createDate = this.createDate,
        statusId = this.id,
    )

