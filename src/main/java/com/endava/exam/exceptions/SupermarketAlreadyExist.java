package com.endava.exam.exceptions;

public class SupermarketAlreadyExist extends RuntimeException{

    public SupermarketAlreadyExist(String message) {
        super(message);
    }
}