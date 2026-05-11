// src/main/java/com/smartshop/dto/request/RegisterRequest.java
package com.smartshop.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
    @NotBlank @Email
    String email,

    @NotBlank @Size(min = 8, message = "Password must be at least 8 characters")
    String password,

    // if null, defaults to BUYER in service layer
    String role
) {}