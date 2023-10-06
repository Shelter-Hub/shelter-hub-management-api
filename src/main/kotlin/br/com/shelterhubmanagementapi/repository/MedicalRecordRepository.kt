package br.com.shelterhubmanagementapi.repository

import br.com.shelterhubmanagementapi.domain.model.MedicalRecord
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Component
import java.util.UUID

@Component
interface MedicalRecordRepository : CoroutineCrudRepository<MedicalRecord, UUID>
