package com.addresses.manager.api.error;

public class NotFoundException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 3359542750025747743L;

    public NotFoundException(String errorMessage) {
        super(errorMessage);
    }

}
