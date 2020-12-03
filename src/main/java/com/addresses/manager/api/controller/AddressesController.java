package com.addresses.manager.api.controller;

import com.addresses.manager.api.data.AddressDto;
import com.addresses.manager.api.data.AddressIncomingDto;
import com.addresses.manager.api.error.NotFoundException;
import com.addresses.manager.api.service.AddressService;
import com.addresses.manager.api.spec.AddressSpecificationsBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ahmadalisaeed
 */

@RestController
@RequestMapping(value = "/api/v1/addresses", produces=APPLICATION_JSON_VALUE)
public class AddressesController {
    
    
    @Autowired
    private AddressService addressService;
    
    @GetMapping
    public ResponseEntity<List<AddressDto>> get(@RequestParam(value = "search", required = false) String search) {

        List<AddressDto> addresses;

        AddressSpecificationsBuilder builder = new AddressSpecificationsBuilder();

        Pattern pattern = Pattern.compile("(\\w+?)(:)(\\S+?),");
        Matcher matcher = pattern.matcher(search + ",");

        while (matcher.find()) {
            builder.with(matcher.group(1), matcher.group(3));
        }
        
        addresses = addressService.getAllAddress(builder.build());

        return ResponseEntity.status(HttpStatus.OK).body(addresses);
    }
    
    
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<AddressDto> create(
                    @Valid @RequestBody AddressIncomingDto addressIncomingDto) {

        final AddressDto addressDto = addressService.CreateAddress(addressIncomingDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(addressDto);
    }

    @PutMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<AddressDto> update(
                    @PathVariable Long id,
                    @Valid @RequestBody AddressIncomingDto addressIncomingDto) throws NotFoundException {

        RestPreconditions.checkFound(addressService.getAddressById(id));
        final AddressDto updatedAddressDto = addressService.updateAddress(id, addressIncomingDto);

        return ResponseEntity.status(HttpStatus.OK).body(updatedAddressDto);
    }

    @PatchMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE )
    public ResponseEntity<AddressDto> patch(
                    @PathVariable Long id,
                    @RequestBody JsonPatch patch) throws IllegalArgumentException, JsonProcessingException, JsonPatchException, NotFoundException {

        RestPreconditions.checkFound(addressService.getAddressById(id));
        final AddressDto updatedAddressDto = addressService.patchAddress(id, patch);
        return ResponseEntity.status(HttpStatus.OK).body(updatedAddressDto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> destory(@PathVariable Long id) throws NotFoundException {

        RestPreconditions.checkFound(addressService.getAddressById(id));

        addressService.destoryAddress(id);

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
    
}
