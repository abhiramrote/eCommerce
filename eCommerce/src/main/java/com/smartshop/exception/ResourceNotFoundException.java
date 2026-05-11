// src/main/java/com/smartshop/exception/ResourceNotFoundException.java
package com.smartshop.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) { super(message); }
}