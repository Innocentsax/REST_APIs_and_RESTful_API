package com.example.blogpost.repository;

import com.example.blogpost.entities.Admin;
import com.example.blogpost.entities.BlogUser;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BlogUserRepository extends JpaRepository<BlogUser, Long> {

    Optional<BlogUser> findBlogUserByEmail(String email);

    Optional<BlogUser> findBlogUserById(Long blogUserId);
}
