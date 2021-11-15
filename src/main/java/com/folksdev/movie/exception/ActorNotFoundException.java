package com.folksdev.movie.exception;

public class ActorNotFoundException extends RuntimeException {
    public ActorNotFoundException(String s) {
        super(s);
    }
}
