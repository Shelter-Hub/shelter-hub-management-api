package br.com.shelterhubmanagementapi.exception

class ResourceNotFoundException(override val message: String? = null) : RuntimeException(message)
