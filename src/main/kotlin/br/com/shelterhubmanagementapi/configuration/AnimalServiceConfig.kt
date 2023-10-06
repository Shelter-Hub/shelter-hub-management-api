package br.com.shelterhubmanagementapi.configuration

import br.com.shelterhubmanagementapi.repository.AnimalRepository
import br.com.shelterhubmanagementapi.service.AnimalService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AnimalServiceConfig(
    private val animalRepository: AnimalRepository,
) {
    @Bean
    fun createAnimalService(animalRepository: AnimalRepository) = AnimalService(animalRepository)
}
