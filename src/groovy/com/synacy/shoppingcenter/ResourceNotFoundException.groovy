package com.synacy.shoppingcenter

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message)
    }
}