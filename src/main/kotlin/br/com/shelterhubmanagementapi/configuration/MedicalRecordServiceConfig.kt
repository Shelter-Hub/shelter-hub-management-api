package br.com.shelterhubmanagementapi.configuration

import br.com.shelterhubmanagementapi.repository.MedicalRecordRepository
import br.com.shelterhubmanagementapi.service.MedicalRecordService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MedicalRecordServiceConfig(
    private val medicalRecordRepository: MedicalRecordRepository,
) {
    @Bean
    fun createMedicalRecordService(medicalRecordRepository: MedicalRecordRepository) = MedicalRecordService(medicalRecordRepository)
}
