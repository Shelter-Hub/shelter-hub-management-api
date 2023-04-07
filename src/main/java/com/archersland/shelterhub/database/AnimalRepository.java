package com.archersland.shelterhub.database;

import com.archersland.shelterhub.domain.Animal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AnimalRepository extends JpaRepository<Animal, UUID> {
}
