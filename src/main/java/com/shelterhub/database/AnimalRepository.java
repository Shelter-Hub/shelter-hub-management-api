package com.shelterhub.database;

import com.shelterhub.domain.model.Animal;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AnimalRepository extends CrudRepository<Animal, UUID> {
}
