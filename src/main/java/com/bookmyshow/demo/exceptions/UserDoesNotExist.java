package com.bookmyshow.demo.exceptions;

public class UserDoesNotExist extends Exception {

    public UserDoesNotExist(String message) {
        super(message);
    }
}