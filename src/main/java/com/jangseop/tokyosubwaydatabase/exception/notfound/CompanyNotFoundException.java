package com.jangseop.tokyosubwaydatabase.exception.notfound;

public class CompanyNotFoundException extends DataNotFoundException {
    public CompanyNotFoundException(Long id) {
        super(String.format("Company (%s) is not found.", id), id);
    }

    public CompanyNotFoundException(String name) {
        super(String.format("Company %s is not found", name), name);
    }
}
