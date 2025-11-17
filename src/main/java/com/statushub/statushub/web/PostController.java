package com.statushub.statushub.web;

import com.statushub.statushub.domain.Environment;
import com.statushub.statushub.domain.Post;
import com.statushub.statushub.dto.PostRequest;
import com.statushub.statushub.repository.EnvironmentRepository;
import com.statushub.statushub.repository.PostRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostRepository posts;
    private final EnvironmentRepository envs;

    public PostController(PostRepository posts, EnvironmentRepository envs) {
        this.posts = posts;
        this.envs = envs;
    }

    @GetMapping
    public List<Post> all() {
        return posts.findAll();
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody PostRequest req) {
        var envOpt = envs.findById(req.environmentId());
        if (envOpt.isEmpty()) {
            // 400 Bad Request hvis environment ikke findes
            return ResponseEntity.badRequest().body("Environment not found");
        }

        Environment env = envOpt.get();

        var post = Post.builder()
                .title(req.title())
                .description(req.description())
                .type(req.type())
                .environment(env)
                .build();

        post = posts.save(post);
        return ResponseEntity
                .created(URI.create("/api/posts/" + post.getId()))
                .body(post);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @Valid @RequestBody PostRequest req) {

        var envOpt = envs.findById(req.environmentId());
        if (envOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Environment not found");
        }
        Environment env = envOpt.get();

        return posts.findById(id)
                .map(p -> {
                    p.setTitle(req.title());
                    p.setDescription(req.description());
                    p.setType(req.type());
                    p.setEnvironment(env);
                    Post saved = posts.save(p);
                    return ResponseEntity.ok(saved);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!posts.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        posts.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
