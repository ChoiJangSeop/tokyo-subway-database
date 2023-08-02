package com.jangseop.tokyosubwaydatabase.exception;

public class FarePolicyNotFoundException extends RuntimeException {

    public FarePolicyNotFoundException(Long id) {
        super(String.format("fare policy of id (%s) is not found", id));
    }
}
