package br.com.shelterhubmanagementapi.exception

class PersistenceFailedException(override val message: String? = null) : RuntimeException(message)
