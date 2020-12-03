package com.addresses.manager.api.converter;

import com.addresses.manager.api.data.Address;
import com.addresses.manager.api.data.AddressDto;
import com.addresses.manager.api.data.AddressIncomingDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author ahmadalisaeed
 */
@Component
public class AddressConvertor {
    
    @Autowired
    private ModelMapper modelMapper;
    
    public AddressDto toResponse(Address address) {
        return modelMapper.map(address, AddressDto.class);
    }
    
    public Address toModelObject(AddressIncomingDto addressIncomingDto){
        return modelMapper.map(addressIncomingDto, Address.class);
    }
    
    public AddressIncomingDto toFormObject(Address address){
        return modelMapper.map(address, AddressIncomingDto.class);
    }
    
}
