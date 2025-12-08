package com.statushub.statushub.repository;

import com.statushub.statushub.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByEnvironmentIdOrderByCreatedAtDesc(Long environmentId);

    void deleteByEnvironmentId(Long environmentId);
}
