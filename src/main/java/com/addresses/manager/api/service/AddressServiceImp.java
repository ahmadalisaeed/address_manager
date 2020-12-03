package com.addresses.manager.api.service;

import com.addresses.manager.api.converter.AddressConvertor;
import com.addresses.manager.api.data.Address;
import com.addresses.manager.api.data.AddressDto;
import com.addresses.manager.api.data.AddressIncomingDto;
import com.addresses.manager.api.repository.AddressRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 *
 * @author ahmadalisaeed
 */
@Service
public class AddressServiceImp implements AddressService {
    
    @Autowired
    private AddressRepository addressRepository;
    
    @Autowired AddressConvertor addressConvertor;

    @Override
    public List<AddressDto> getAllAddress(Specification<Address> spec) {
    	List<Address> addresses = addressRepository.findAll(spec);

    	var addressDtos = addresses.stream()
	        .map(address-> addressConvertor.toResponse(address))
	        .collect(Collectors.toList());
    	
    	return addressDtos;
    }

    @Transactional
    @Override
    public AddressDto CreateAddress(AddressIncomingDto addressIncomingDto) {
    	Address address = addressConvertor.toModelObject(addressIncomingDto);
    	address = addressRepository.saveAndFlush(address);
    	return addressConvertor.toResponse(address);
    }

    @Transactional
    @Override
    public AddressDto updateAddress(Long addressId, AddressIncomingDto addressIncomingDto) {
        Address updatedAddress = addressConvertor.toModelObject(addressIncomingDto);
        updatedAddress.setId(addressId);
        updatedAddress = addressRepository.saveAndFlush(updatedAddress);
        return addressConvertor.toResponse(updatedAddress);
    }

    @Transactional
    @Override
    public void destoryAddress(Long addressId) {
        addressRepository.deleteById(addressId);
    }

    @Override
    public Address getAddressById(Long addressId) {
        Optional<Address> addressOp = addressRepository.findById(addressId);
        return addressOp.orElse(null);
    }

    @Override
    public AddressDto patchAddress(Long addressId, JsonPatch patch) throws IllegalArgumentException, 
        JsonProcessingException, JsonPatchException {	

        Address address = getAddressById(addressId);
        
        AddressIncomingDto addressIncomingDto = addressConvertor.toFormObject(address);
        
        AddressIncomingDto patchedAddress = applyPatchToAddress(patch, addressIncomingDto);
        
        validateIncomingAddress(patchedAddress);
        
        return updateAddress(addressId, patchedAddress);

    }
    
    private void validateIncomingAddress(AddressIncomingDto patchedAddress){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<AddressIncomingDto>> violations = validator.validate(patchedAddress);
        if(!violations.isEmpty()){
            throw new ConstraintViolationException(violations);
        }
    }
	
    private AddressIncomingDto applyPatchToAddress(JsonPatch patch, AddressIncomingDto address) throws IllegalArgumentException, 
        JsonPatchException, JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode patched = patch.apply(objectMapper.convertValue(address, JsonNode.class));
        return objectMapper.treeToValue(patched, AddressIncomingDto.class);
    }
    
}
