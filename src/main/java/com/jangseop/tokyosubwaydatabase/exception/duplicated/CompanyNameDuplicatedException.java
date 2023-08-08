package com.jangseop.tokyosubwaydatabase.exception.duplicated;

public class CompanyNameDuplicatedException extends ObjectDuplicatedException {
    public CompanyNameDuplicatedException(String name) {
        super(String.format("Company name (%s) is already existed.", name), name);
    }
}
