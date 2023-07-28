package com.jangseop.tokyosubwaydatabase.exception;

public class CompanyNameDuplicatedException extends RuntimeException {
    public CompanyNameDuplicatedException(String name) {
        super(String.format("Company name (%s) is already existed.", name));
    }
}
