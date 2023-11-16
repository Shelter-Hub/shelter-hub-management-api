package br.com.shelterhubmanagementapi.repository

import br.com.shelterhubmanagementapi.domain.model.AnimalStatus
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.data.repository.kotlin.CoroutineSortingRepository
import org.springframework.stereotype.Component

@Component
interface AnimalStatusRepository :
        CoroutineCrudRepository<AnimalStatus, Long>,
        CoroutineSortingRepository<AnimalStatus, Long>