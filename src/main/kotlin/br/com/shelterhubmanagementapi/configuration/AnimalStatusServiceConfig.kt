package br.com.shelterhubmanagementapi.configuration

import br.com.shelterhubmanagementapi.repository.AnimalStatusRepository
import br.com.shelterhubmanagementapi.service.AnimalStatusService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AnimalStatusServiceConfig(
    private val animalStatusRepository: AnimalStatusRepository,
    private val animalStatusService: AnimalStatusService
) {

    @Bean
    fun createAnimalStatusService(
        animalStatusRepository: AnimalStatusRepository,
        animalStatusService: AnimalStatusService
    ) = AnimalStatusService(animalStatusRepository)
}
