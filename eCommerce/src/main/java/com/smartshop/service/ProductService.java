// src/main/java/com/smartshop/service/ProductService.java
package com.smartshop.service;

import com.smartshop.dto.request.CreateProductRequest;
import com.smartshop.dto.response.ProductResponse;
import com.smartshop.entity.*;
import com.smartshop.exception.ResourceNotFoundException;
import com.smartshop.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) // default: read-only (no flush, faster)
public class ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Transactional                  // overrides readOnly for writes
    public ProductResponse create(CreateProductRequest request, String sellerEmail) {
        User seller = userRepository.findByEmail(sellerEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Product product = Product.builder()
                .name(request.name())
                .description(request.description())
                .price(request.price())
                .stockQuantity(request.stockQuantity())
                .seller(seller)
                .build();

        return toResponse(productRepository.save(product));
    }

    public Page<ProductResponse> getAll(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size,
                Sort.by(Sort.Direction.DESC, sort));
        return productRepository.findAll(pageable).map(this::toResponse);
    }

    public ProductResponse getById(UUID id) {
        return productRepository.findByIdWithSeller(id)
                .map(this::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + id));
    }

    @Transactional
    public ProductResponse update(UUID id, CreateProductRequest request, String requesterEmail) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        // Authorization check: only the seller can update their product
        if (!product.getSeller().getEmail().equals(requesterEmail)) {
            throw new AccessDeniedException("You can only update your own products");
        }

        product.setName(request.name());
        product.setDescription(request.description());
        product.setPrice(request.price());
        product.setStockQuantity(request.stockQuantity());

        // No explicit save() needed — JPA dirty-checking handles it within @Transactional
        return toResponse(product);
    }

    @Transactional
    public void delete(UUID id, String requesterEmail) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        if (!product.getSeller().getEmail().equals(requesterEmail)) {
            throw new AccessDeniedException("You can only delete your own products");
        }

        productRepository.delete(product);
    }

    private ProductResponse toResponse(Product p) {
        return new ProductResponse(
                p.getId(), p.getName(), p.getDescription(),
                p.getPrice(), p.getStockQuantity(),
                p.getSeller().getEmail(), p.getCreatedAt()
        );
    }
}