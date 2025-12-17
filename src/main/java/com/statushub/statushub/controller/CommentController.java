package com.statushub.statushub.controller;

import com.statushub.statushub.domain.Comment;
import com.statushub.statushub.dto.CommentRequest;
import com.statushub.statushub.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class CommentController {

    private final CommentService service;

    public CommentController(CommentService service) {
        this.service = service;
    }

    private boolean isAdmin(HttpServletRequest req) {
        String role = req.getHeader("X-Role");
        return role != null && role.equalsIgnoreCase("ADMIN");
    }

    @GetMapping("/posts/{postId}/comments")
    public List<Comment> getComments(@PathVariable Long postId) {
        return service.getForPost(postId);
    }

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<Comment> addComment(@PathVariable Long postId, @RequestBody CommentRequest req) {
        Comment created = service.addToPost(postId, req);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @DeleteMapping("/comments/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id, HttpServletRequest httpReq) {
        if (!isAdmin(httpReq)) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("ADMIN only");
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
