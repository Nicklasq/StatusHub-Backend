package com.statushub.statushub.repository;

import com.statushub.statushub.domain.Environment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnvironmentRepository extends JpaRepository<Environment, Long> {

    // hvis du vil bruge dem til grouping / delete solution:
    List<Environment> findBySolutionName(String solutionName);

    void deleteBySolutionName(String solutionName);
}
