package com.example.exceptions;

public class KeyNotFoundException extends JourneyException {

    public KeyNotFoundException(final String keyName, final String placeholder) {
        super(String.format("Key '%s' not found in '%s'.", keyName, placeholder));
    }
}
