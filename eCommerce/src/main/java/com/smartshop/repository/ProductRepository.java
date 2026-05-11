// src/main/java/com/smartshop/repository/ProductRepository.java
package com.smartshop.repository;

import com.smartshop.entity.Product;
import com.smartshop.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {

    // Pagination built-in — interview: how does Spring Data generate the LIMIT/OFFSET?
    Page<Product> findBySeller(User seller, Pageable pageable);

    // Named query: avoids N+1 by JOIN FETCHing seller in one query
    // Interview: explain why we need this instead of findAll()
    @Query("SELECT p FROM Product p JOIN FETCH p.seller WHERE p.id = :id")
    java.util.Optional<Product> findByIdWithSeller(UUID id);

    // Search by name (case-insensitive)
    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);
}