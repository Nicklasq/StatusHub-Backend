package com.statushub.statushub.service;

import com.statushub.statushub.domain.Environment;
import com.statushub.statushub.domain.Post;
import com.statushub.statushub.dto.PostRequest;
import com.statushub.statushub.repository.EnvironmentRepository;
import com.statushub.statushub.repository.PostRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostService {

    private final PostRepository posts;
    private final EnvironmentRepository environments;

    public PostService(PostRepository posts, EnvironmentRepository environments) {
        this.posts = posts;
        this.environments = environments;
    }

    public List<Post> getByEnvironment(Long environmentId) {
        return posts.findByEnvironmentIdOrderByCreatedAtDesc(environmentId);
    }

    public Post create(PostRequest request) {
        Environment env = environments.findById(request.environmentId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Environment not found: " + request.environmentId()
                ));

        Post post = Post.builder()
                .title(request.title())
                .description(request.description())
                .type(request.type())
                .environment(env)
                .createdAt(LocalDateTime.now())
                .build();

        return posts.save(post);
    }

    public Post update(Long id, PostRequest request) {
        Environment env = environments.findById(request.environmentId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Environment not found: " + request.environmentId()
                ));

        return posts.findById(id)
                .map(p -> {
                    p.setTitle(request.title());
                    p.setDescription(request.description());
                    p.setType(request.type());
                    p.setEnvironment(env);
                    return posts.save(p);
                })
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Post not found: " + id
                ));
    }

    public void delete(Long id) {
        if (!posts.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Post not found: " + id
            );
        }
        posts.deleteById(id);
    }

    public void deleteByEnvironment(Long environmentId) {
        posts.deleteByEnvironmentId(environmentId);
    }
}
