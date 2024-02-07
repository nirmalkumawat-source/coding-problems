package com.example.exceptions;

public class KeyAlreadyFoundException extends JourneyException {

    public KeyAlreadyFoundException(final String keyName, final String placeholder) {
        super(String.format("Key '%s' already found in '%s'.", keyName, placeholder));
    }
}
