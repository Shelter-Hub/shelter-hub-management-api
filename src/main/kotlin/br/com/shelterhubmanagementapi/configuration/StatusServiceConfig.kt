package br.com.shelterhubmanagementapi.configuration

import br.com.shelterhubmanagementapi.repository.StatusRepository
import br.com.shelterhubmanagementapi.service.AnimalStatusService
import br.com.shelterhubmanagementapi.service.StatusService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class StatusServiceConfig(
    private val statusRepository: StatusRepository,
    private val animalStatusService: AnimalStatusService
) {
    @Bean
    fun createStatusService(
        statusRepository: StatusRepository,
        animalStatusService: AnimalStatusService
    ) = StatusService(statusRepository)
}

