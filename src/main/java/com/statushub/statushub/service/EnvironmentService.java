package com.statushub.statushub.service;

import com.statushub.statushub.domain.Environment;
import com.statushub.statushub.repository.EnvironmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnvironmentService {

    private final EnvironmentRepository repository;

    public EnvironmentService(EnvironmentRepository repository) {
        this.repository = repository;
    }

    public List<Environment> getAllEnvironments() {
        return repository.findAll();
    }

    public Environment createEnvironment(Environment environment) {
        return repository.save(environment);
    }

    public void deleteEnvironment(Long id) {
        repository.deleteById(id);
    }

    // 🔹 NY METODE: UPDATE LIGGER HERINDE
    public Environment updateEnvironment(Long id, Environment updated) {
        return repository.findById(id)
                .map(env -> {
                    env.setName(updated.getName());
                    env.setStatus(updated.getStatus());
                    return repository.save(env);
                })
                .orElseThrow(() -> new RuntimeException("Environment not found: " + id));
    }
}
