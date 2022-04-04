package com.endava.exam.exceptions;

public class PurchaseNotFoundException extends RuntimeException {

    public PurchaseNotFoundException(String message) {
        super(message);
    }
}