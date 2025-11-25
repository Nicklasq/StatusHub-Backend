package com.statushub.statushub.repository;

import com.statushub.statushub.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    // Hent posts for et bestemt Environment, nyeste først
    List<Post> findByEnvironmentIdOrderByCreatedAtDesc(Long environmentId);
}
