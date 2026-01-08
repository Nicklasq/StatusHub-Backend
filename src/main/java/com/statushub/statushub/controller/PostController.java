package com.statushub.statushub.controller;

import com.statushub.statushub.domain.Post;
import com.statushub.statushub.dto.PostRequest;
import com.statushub.statushub.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = "*")
public class PostController {

    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    // --- Role helpers ---

    private String role(HttpServletRequest req) {
        String role = req.getHeader("X-Role");
        return role == null ? "VIEWER" : role.toUpperCase();
    }

    private boolean isAdmin(HttpServletRequest req) {
        return "ADMIN".equalsIgnoreCase(role(req));
    }

    private boolean canWritePost(HttpServletRequest req) {
        // VIEWER + ADMIN må oprette (og evt. redigere) posts
        String r = role(req);
        return "ADMIN".equals(r) || "VIEWER".equals(r);
    }

    // --- Endpoints ---

    @GetMapping
    public List<Post> getAll() {
        return service.getAll();
    }

    @GetMapping("/environment/{environmentId}")
    public List<Post> getByEnvironment(@PathVariable Long environmentId) {
        return service.getByEnvironment(environmentId);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody PostRequest request, HttpServletRequest httpReq) {
        if (!canWritePost(httpReq)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Only VIEWER or ADMIN can create posts");
        }

        Post created = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @RequestBody PostRequest request,
                                    HttpServletRequest httpReq) {
        if (!canWritePost(httpReq)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Only VIEWER or ADMIN can update posts");
        }

        Post updated = service.update(id, request);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, HttpServletRequest httpReq) {
        if (!isAdmin(httpReq)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("ADMIN only");
        }

        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
