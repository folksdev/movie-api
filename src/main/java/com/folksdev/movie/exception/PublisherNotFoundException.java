package com.folksdev.movie.exception;

public class PublisherNotFoundException extends RuntimeException{

    public PublisherNotFoundException(String message) {
        super(message);
    }
}
