package com.statushub.statushub.service;

import com.statushub.statushub.domain.Comment;
import com.statushub.statushub.domain.Post;
import com.statushub.statushub.dto.CommentRequest;
import com.statushub.statushub.repository.CommentRepository;
import com.statushub.statushub.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository comments;
    private final PostRepository posts;

    public CommentService(CommentRepository comments, PostRepository posts) {
        this.comments = comments;
        this.posts = posts;
    }

    public List<Comment> getForPost(Long postId) {
        return comments.findByPostIdOrderByCreatedAtAsc(postId);
    }

    public Comment addToPost(Long postId, CommentRequest req) {
        Post post = posts.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found: " + postId));

        Comment c = Comment.builder()
                .text(req.text())
                .author(req.author())
                .post(post)
                .build();

        return comments.save(c);
    }

    public void delete(Long commentId) {
        comments.deleteById(commentId);
    }
}
