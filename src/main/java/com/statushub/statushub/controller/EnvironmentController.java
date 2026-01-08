package com.statushub.statushub.controller;

import com.statushub.statushub.domain.Environment;
import com.statushub.statushub.service.EnvironmentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/environments")
@CrossOrigin(origins = "*")
public class EnvironmentController {

    private final EnvironmentService service;

    public EnvironmentController(EnvironmentService service) {
        this.service = service;
    }

    private boolean isAdmin(HttpServletRequest req) {
        String role = req.getHeader("X-Role");
        return role != null && role.equalsIgnoreCase("ADMIN");
    }

    @GetMapping
    public List<Environment> getAll() {
        return service.getAllEnvironments();
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Environment environment, HttpServletRequest httpReq) {
        if (!isAdmin(httpReq)) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("ADMIN only");
        Environment saved = service.createEnvironment(environment);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Environment updated, HttpServletRequest httpReq) {
        if (!isAdmin(httpReq)) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("ADMIN only");
        return ResponseEntity.ok(service.updateEnvironment(id, updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, HttpServletRequest httpReq) {
        if (!isAdmin(httpReq)) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("ADMIN only");
        service.deleteEnvironment(id);
        return ResponseEntity.noContent().build();
    }
}
