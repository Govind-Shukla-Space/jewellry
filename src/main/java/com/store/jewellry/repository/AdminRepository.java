package com.store.jewellry.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.store.jewellry.entity.Admin;

public interface AdminRepository extends JpaRepository<Admin,Long> {
    Optional<Admin> findByEmail(String email);
}
