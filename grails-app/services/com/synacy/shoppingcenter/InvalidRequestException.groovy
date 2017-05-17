package com.synacy.shoppingcenter

public class InvalidRequestException extends RuntimeException {
    public InvalidRequestException(String message) {
        super(message)
    }
}