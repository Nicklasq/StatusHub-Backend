package com.statushub.statushub.repository;

import com.statushub.statushub.domain.Environment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EnvironmentRepository extends JpaRepository<Environment, Long> {
    Optional<Environment> findByName(String name);
}
