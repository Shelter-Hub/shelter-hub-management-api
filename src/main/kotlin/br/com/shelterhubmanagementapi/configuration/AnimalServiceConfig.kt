package br.com.shelterhubmanagementapi.configuration

import br.com.shelterhubmanagementapi.repository.AnimalRepository
import br.com.shelterhubmanagementapi.service.AnimalService
import br.com.shelterhubmanagementapi.service.MedicalRecordService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AnimalServiceConfig(
    private val animalRepository: AnimalRepository,
    private val medicalRecordService: MedicalRecordService,
) {
    @Bean
    fun createAnimalService(
        animalRepository: AnimalRepository,
        medicalRecordService: MedicalRecordService,
    ) = AnimalService(animalRepository, medicalRecordService)
}
