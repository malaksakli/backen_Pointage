package com.Blacher.Blacher.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Blacher.Blacher.models.User;


public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
