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
    private final EnvironmentRepository envs;

    public PostService(PostRepository posts, EnvironmentRepository envs) {
        this.posts = posts;
        this.envs = envs;
    }

    public List<Post> getAll() {
        return posts.findAll();
    }

    public List<Post> getByEnvironment(Long environmentId) {
        return posts.findByEnvironmentId(environmentId);
    }

    public Post create(PostRequest req) {
        Environment env = envs.findById(req.environmentId())
                .orElseThrow(() -> new RuntimeException("Environment not found: " + req.environmentId()));

        Post post = Post.builder()
                .title(req.title())
                .description(req.description())
                .type(req.type())
                .createdBy(req.createdBy())
                .environment(env)
                .build();

        return posts.save(post);
    }

    public Post update(Long id, PostRequest req) {
        Environment env = envs.findById(req.environmentId())
                .orElseThrow(() -> new RuntimeException("Environment not found: " + req.environmentId()));

        return posts.findById(id)
                .map(p -> {
                    p.setTitle(req.title());
                    p.setDescription(req.description());
                    p.setType(req.type());
                    p.setCreatedBy(req.createdBy());
                    p.setEnvironment(env);
                    return posts.save(p);
                })
                .orElseThrow(() -> new RuntimeException("Post not found: " + id));
    }

    public void delete(Long id) {
        posts.deleteById(id);
    }
}
