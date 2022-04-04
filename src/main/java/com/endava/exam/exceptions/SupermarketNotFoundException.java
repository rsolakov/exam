package com.endava.exam.exceptions;

public class SupermarketNotFoundException extends RuntimeException {

    public SupermarketNotFoundException(String message) {
        super(message);
    }
}