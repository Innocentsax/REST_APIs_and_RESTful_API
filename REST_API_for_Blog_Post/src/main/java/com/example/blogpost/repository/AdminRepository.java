package com.example.blogpost.repository;

import com.example.blogpost.entities.Admin;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    Optional<Admin> findByEmail(String email);

    Optional<Admin> findAdminById(Long id);

    Optional<Admin> findById(Long adminId);
}
