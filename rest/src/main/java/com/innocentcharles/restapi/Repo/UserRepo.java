package com.innocentcharles.restapi.Repo;

import com.innocentcharles.restapi.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
}
