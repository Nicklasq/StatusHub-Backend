package com.statushub.statushub.web;

import com.statushub.statushub.domain.Environment;
import com.statushub.statushub.dto.EnvironmentRequest;
import com.statushub.statushub.repository.EnvironmentRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/environments")
public class EnvironmentController {

    private final EnvironmentRepository repo;

    public EnvironmentController(EnvironmentRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Environment> all() {
        return repo.findAll();
    }

    @PostMapping
    public ResponseEntity<Environment> create(@Valid @RequestBody EnvironmentRequest req) {
        var env = Environment.builder()
                .name(req.name())
                .status(req.status())
                .build();
        env = repo.save(env);
        return ResponseEntity.created(URI.create("/api/environments/" + env.getId())).body(env);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Environment> update(@PathVariable Long id, @Valid @RequestBody EnvironmentRequest req) {
        return repo.findById(id)
                .map(e -> {
                    e.setName(req.name());
                    e.setStatus(req.status());
                    return ResponseEntity.ok(repo.save(e));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!repo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
