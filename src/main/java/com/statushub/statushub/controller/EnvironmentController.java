package com.statushub.statushub.controller;

import com.statushub.statushub.domain.Environment;
import com.statushub.statushub.service.EnvironmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/environments")
@CrossOrigin(origins = "http://localhost:5173") // tillad frontend adgang
public class EnvironmentController {

    private final EnvironmentService service;

    public EnvironmentController(EnvironmentService service) {
        this.service = service;
    }

    @GetMapping
    public List<Environment> getAll() {
        return service.getAllEnvironments();
    }

    @PostMapping
    public ResponseEntity<Environment> create(@RequestBody Environment environment) {
        Environment saved = service.createEnvironment(environment);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteEnvironment(id);
        return ResponseEntity.noContent().build();
    }
}
