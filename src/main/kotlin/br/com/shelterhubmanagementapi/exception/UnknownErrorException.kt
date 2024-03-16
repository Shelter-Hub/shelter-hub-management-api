package br.com.shelterhubmanagementapi.exception

class UnknownErrorException(override val message: String? = null) : RuntimeException(message)
