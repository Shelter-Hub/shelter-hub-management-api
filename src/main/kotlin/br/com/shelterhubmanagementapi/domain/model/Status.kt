package br.com.shelterhubmanagementapi.domain.model

import java.util.UUID

class Status (
   val id: UUID = UUID.randomUUID(),
   val name: String,
)
