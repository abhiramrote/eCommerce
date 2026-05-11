// src/main/java/com/smartshop/dto/request/CreateProductRequest.java
package com.smartshop.dto.request;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record CreateProductRequest(
    @NotBlank @Size(max = 255)
    String name,

    String description,

    @NotNull @DecimalMin("0.01")
    BigDecimal price,

    @NotNull @Min(0)
    Integer stockQuantity
) {}