package com.endava.exam.exceptions;

public class ItemDoesNotExist extends RuntimeException{

    public ItemDoesNotExist(String message) {
        super(message);
    }
}