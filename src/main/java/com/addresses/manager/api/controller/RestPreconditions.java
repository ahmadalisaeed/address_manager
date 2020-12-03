package com.addresses.manager.api.controller;

import com.addresses.manager.api.error.NotFoundException;

/**
 *
 * @author ahmadalisaeed
 */
public final class RestPreconditions {
    
    /**
     * Check if resource was found, otherwise throw exception.
     * 
     * @param resource
     *            has value true if found, otherwise false
     * @throws NotFoundException
     *             if expression is false, means value not found.
     */
    
    public static <T> T checkFound(final T resource) throws NotFoundException {
        if (resource == null) {
            throw new NotFoundException("Resource Not found");
        }

        return resource;
    }
    
}
