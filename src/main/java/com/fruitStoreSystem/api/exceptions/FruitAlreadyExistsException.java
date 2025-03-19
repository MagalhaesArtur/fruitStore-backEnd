package com.fruitStoreSystem.api.exceptions;

public class FruitAlreadyExistsException extends RuntimeException {
    public FruitAlreadyExistsException(String message) {
        super(message);
    }
}
