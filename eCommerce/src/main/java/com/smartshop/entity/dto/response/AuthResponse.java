// src/main/java/com/smartshop/dto/response/AuthResponse.java
package com.smartshop.dto.response;

public record AuthResponse(
    String token,
    String email,
    String role
) {}