package com.jangseop.tokyosubwaydatabase.exception;

public class CompanyNotFoundException extends RuntimeException {
    public CompanyNotFoundException(Long id) {
        super(String.format("Company (%s) is not found.", id));
    }

    public CompanyNotFoundException(String name) {
        super(String.format("Company %s is not found", name));
    }
}
