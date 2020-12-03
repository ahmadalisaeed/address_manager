package com.addresses.manager.api.service;

import com.addresses.manager.api.data.Address;
import com.addresses.manager.api.data.AddressDto;
import com.addresses.manager.api.data.AddressIncomingDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;

import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/**
 * The service layer interface, responsible for defining and isolating the business methods of the
 * application
 * 
 * @author ahmadalisaeed
 */
public interface AddressService {
    
    /**
     * Fetches a list with all {@link Address} entities in the DB
     * 
     * @param  Specification of type {@link Address}
     * @return the list with {@link Address} entities
     */
    public List<AddressDto> getAllAddress(Specification<Address> spec);
    
    /**
     * Fetches the {@link Address} entity From DB By addressId
     * 
     * @param addressId
     * @return the list with {@link Address} entities
     */
    public Address getAddressById(Long addressId);
    
    /**
     * Creates a new {@link Address} entity in the DB
     * 
     * @param address an instance of {@link AddressIncomingDto}
     * @return the newly created {@link AddressDto} entity
     */
    public AddressDto CreateAddress(AddressIncomingDto address);
    
    /**
     * Updates the {@link Address} entity in the DB
     * By addressId with {@link AddressIncomingDto}
     * 
     * @param addressId
     * @param address an instance of {@link AddressIncomingDto}
     * @return 
     */
    public AddressDto updateAddress(Long addressId, AddressIncomingDto address);
    
    /**
     * Patch the {@link Address} entity in the DB
     * By addressId with changes
     * 
     * @param addressId
     * @param patch an instance of {@link Map<String, Object>}
     * @return 
     * @throws JsonPatchException 
     * @throws JsonProcessingException 
     * @throws IllegalArgumentException 
     */
    public AddressDto patchAddress(Long addressId, JsonPatch patch) throws IllegalArgumentException, JsonProcessingException, JsonPatchException;
    
    /**
     * Destroy the {@link Address} entity from DB
     * By addressId
     * 
     * @param addressId
     */
    public void destoryAddress(Long addressId);
    
}
