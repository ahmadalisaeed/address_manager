package com.addresses.manager.api.error;

public class MissingParameterException extends Exception {
	
    /**
     * 
     */
    private static final long serialVersionUID = 6504882937871579695L;

    public MissingParameterException(String errorMessage) {
        super(errorMessage);
    }

}
