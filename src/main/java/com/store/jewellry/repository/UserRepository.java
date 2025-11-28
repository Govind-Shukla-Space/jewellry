package com.store.jewellry.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.store.jewellry.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
