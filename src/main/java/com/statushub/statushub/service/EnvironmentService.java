package com.statushub.statushub.service;

import com.statushub.statushub.domain.Environment;
import com.statushub.statushub.repository.EnvironmentRepository;
import com.statushub.statushub.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnvironmentService {

    private final EnvironmentRepository repository;
    private final PostRepository posts;

    public EnvironmentService(EnvironmentRepository repository,
                              PostRepository posts) {
        this.repository = repository;
        this.posts = posts;
    }

    public List<Environment> getAllEnvironments() {
        // sorter evt. pænt efter solutionName + name
        return repository.findAll();
    }

    public Environment createEnvironment(Environment environment) {
        // ingen unik constraint på name længere, så vi kan have
        // samme env-navn i flere løsninger
        return repository.save(environment);
    }

    public Environment updateEnvironment(Long id, Environment updated) {
        return repository.findById(id)
                .map(env -> {
                    env.setName(updated.getName());
                    env.setStatus(updated.getStatus());
                    env.setSolutionName(updated.getSolutionName());
                    return repository.save(env);
                })
                .orElseThrow(() -> new RuntimeException("Environment not found: " + id));
    }

    public void deleteEnvironment(Long id) {
        // 1) slet alle posts der peger på environment
        posts.deleteByEnvironmentId(id);
        // 2) slet selve environment
        repository.deleteById(id);
    }

    // Hvis du senere vil have "slet hele solution",
    // kan du lave noget i denne stil:
    //
    // public void deleteSolution(String solutionName) {
    //     List<Environment> envs = repository.findBySolutionName(solutionName);
    //     for (Environment env : envs) {
    //         posts.deleteByEnvironmentId(env.getId());
    //     }
    //     repository.deleteBySolutionName(solutionName);
    // }
}
