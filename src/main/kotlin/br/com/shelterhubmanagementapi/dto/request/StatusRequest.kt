package br.com.shelterhubmanagementapi.dto.request

import br.com.shelterhubmanagementapi.domain.model.Status
import java.util.*

data class StatusRequest(
    val id: UUID ?= null,
    val name: String,
)

fun StatusRequest.toStatus() =
    this.id?.let{
        Status (
            id = it,
            name = this.name,
        )
    } ?: Status(
        name = this.name,
    )






