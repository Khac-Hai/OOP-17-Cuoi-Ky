package com.example.Exception;

public class EmailNotValidException extends Exception{
    public EmailNotValidException(String message){
        super(message);
    }
}
