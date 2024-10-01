package com.example.Exception;

public class BookOutOfStockException extends Exception {
    public BookOutOfStockException(String message) {
        super(message);
    }
}
