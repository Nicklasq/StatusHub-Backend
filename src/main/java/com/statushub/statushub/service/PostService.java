package com.statushub.statushub.service;

import com.statushub.statushub.domain.Environment;
import com.statushub.statushub.domain.Post;
import com.statushub.statushub.dto.PostRequest;
import com.statushub.statushub.repository.EnvironmentRepository;
import com.statushub.statushub.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    private final PostRepository posts;
    private final EnvironmentRepository environments;

    public PostService(PostRepository posts, EnvironmentRepository environments) {
        this.posts = posts;
        this.environments = environments;
    }

    public List<Post> getAll() {
        return posts.findAll();
    }

    public List<Post> getByEnvironment(Long environmentId) {
        return posts.findByEnvironmentIdOrderByCreatedAtDesc(environmentId);
    }

    public Post create(PostRequest request) {
        Environment env = environments.findById(request.environmentId())
                .orElseThrow(() -> new IllegalArgumentException("Environment not found: " + request.environmentId()));

        Post post = Post.builder()
                .title(request.title())
                .description(request.description())
                .type(request.type())
                .environment(env)
                .build();

        return posts.save(post);
    }

    public Post update(Long id, PostRequest request) {
        Post existing = posts.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post not found: " + id));

        Environment env = environments.findById(request.environmentId())
                .orElseThrow(() -> new IllegalArgumentException("Environment not found: " + request.environmentId()));

        existing.setTitle(request.title());
        existing.setDescription(request.description());
        existing.setType(request.type());
        existing.setEnvironment(env);

        return posts.save(existing);
    }

    public void delete(Long id) {
        if (!posts.existsById(id)) {
            throw new IllegalArgumentException("Post not found: " + id);
        }
        posts.deleteById(id);
    }
}
