package com.statushub.statushub.repository;

import com.statushub.statushub.domain.Environment;
import com.statushub.statushub.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    // Senere kan vi bruge den til at filtrere posts pr. miljø
    List<Post> findByEnvironmentOrderByCreatedAtDesc(Environment environment);
}
