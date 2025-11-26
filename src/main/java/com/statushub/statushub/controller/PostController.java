package com.statushub.statushub.controller;

import com.statushub.statushub.domain.Post;
import com.statushub.statushub.dto.PostRequest;
import com.statushub.statushub.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = "*") //
public class PostController {

    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    @GetMapping
    public List<Post> getAll() {
        return service.getAll();
    }

    @GetMapping("/environment/{environmentId}")
    public List<Post> getByEnvironment(@PathVariable Long environmentId) {
        return service.getByEnvironment(environmentId);
    }

    @PostMapping
    public ResponseEntity<Post> create(@RequestBody PostRequest request) {
        Post created = service.create(request);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Post> update(@PathVariable Long id, @RequestBody PostRequest request) {
        Post updated = service.update(id, request);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
