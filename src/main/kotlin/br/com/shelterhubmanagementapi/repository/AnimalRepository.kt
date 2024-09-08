package br.com.shelterhubmanagementapi.repository

import br.com.shelterhubmanagementapi.domain.model.Animal
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.data.repository.kotlin.CoroutineSortingRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface AnimalRepository :
    CoroutineCrudRepository<Animal, UUID>,
    CoroutineSortingRepository<Animal, UUID>
