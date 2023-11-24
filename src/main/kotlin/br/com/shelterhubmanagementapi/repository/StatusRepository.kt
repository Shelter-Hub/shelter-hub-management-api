package br.com.shelterhubmanagementapi.repository

import br.com.shelterhubmanagementapi.domain.model.Status
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.data.repository.kotlin.CoroutineSortingRepository
import org.springframework.stereotype.Component
import java.util.*

@Component
interface StatusRepository :
        CoroutineCrudRepository<Status, UUID>,
        CoroutineSortingRepository<Status, UUID>