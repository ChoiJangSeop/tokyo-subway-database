package com.jangseop.tokyosubwaydatabase.exception.notfound;

public class FarePolicyNotFoundException extends DataNotFoundException {

    public FarePolicyNotFoundException(Long id) {
        super(String.format("fare policy of id (%s) is not found", id), id);
    }
}
