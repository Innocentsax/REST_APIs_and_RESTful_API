package com.example.blogpost.repository;

import com.example.blogpost.entities.Post;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long>{
    Optional<Post>findPostById(Long postId);
}
