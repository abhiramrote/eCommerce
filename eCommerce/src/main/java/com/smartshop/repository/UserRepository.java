// src/main/java/com/smartshop/repository/UserRepository.java
package com.smartshop.repository;

import com.smartshop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

// Spring Data generates all SQL from method names — no implementation needed
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}