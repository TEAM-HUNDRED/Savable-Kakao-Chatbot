package com.management.chatbot.Exception;

public class AlreadyJoinedException extends RuntimeException{
    public AlreadyJoinedException(String message) {
        super(message);
    }
}
