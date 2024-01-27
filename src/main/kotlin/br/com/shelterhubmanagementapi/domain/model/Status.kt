package br.com.shelterhubmanagementapi.domain.model

import br.com.shelterhubmanagementapi.dto.response.StatusResponse
import java.util.UUID

data class Status(
    val id: UUID = UUID.randomUUID(),
    val name: String
)

fun Status.toResponse(): StatusResponse =
    StatusResponse(
        id = this.id,
        name = this.name
    )
