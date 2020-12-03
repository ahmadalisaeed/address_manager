package com.addresses.manager.api.controller.addressesController;

import com.addresses.manager.api.data.Address;
import com.addresses.manager.api.data.AddressDto;
import com.addresses.manager.api.data.AddressIncomingDto;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 * @author ahmadalisaeed
 */
public class UpdateAddressTests extends BaseAddressesIT {

    @Test
    public void shouldUpdateAndReturnUpdatedAddress() {
        Address address = random.nextObject(Address.class);
        
        address = repository.save(address);

        AddressIncomingDto addressIncomingDto = random.nextObject(AddressIncomingDto.class);
        addressIncomingDto.setEmail(randomEmail());
        addressIncomingDto.setZipCode(addressIncomingDto.getZipCode().substring(0, 6));
        
        AddressDto result;
        result = given()
                .contentType(ContentType.JSON)
                .body(addressIncomingDto)
                .when()
                .put(PATH+address.getId())
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract().body()
                .jsonPath().getObject(".", AddressDto.class);

        assertThat(result.getId()).isNotNull();
        assertThat(result.getFirstName()).isEqualTo(addressIncomingDto.getFirstName());
        
        Address updatedAddress = repository.findById(address.getId()).orElse(null);
        
        assertThat(updatedAddress).isNotNull();
        assertThat(updatedAddress.getLastName()).isEqualTo(addressIncomingDto.getLastName());
        assertThat(updatedAddress.getEmail()).isEqualTo(addressIncomingDto.getEmail());
        assertThat(updatedAddress.getStreet()).isEqualTo(addressIncomingDto.getStreet());
        assertThat(updatedAddress.getZipCode()).isEqualTo(addressIncomingDto.getZipCode());
        assertThat(updatedAddress.getCountry()).isEqualTo(addressIncomingDto.getCountry());
    }
    
    @Test
    public void shouldReturnNotFoundIfIdIsNotValid() {

        AddressIncomingDto addressIncomingDto = random.nextObject(AddressIncomingDto.class);
        addressIncomingDto.setEmail(randomEmail());
        addressIncomingDto.setZipCode(addressIncomingDto.getZipCode().substring(0, 6));
        
        given()
            .contentType(ContentType.JSON)
            .body(addressIncomingDto)
            .when()
            .put(PATH+random.nextLong())
            .then()
            .statusCode(HttpStatus.NOT_FOUND.value());  
    }
    
    @Test
    public void shouldValidateEmailAddress() {
        
        Address address = random.nextObject(Address.class);
        
        address = repository.save(address);

        AddressIncomingDto addressIncomingDto = random.nextObject(AddressIncomingDto.class);
        addressIncomingDto.setZipCode(addressIncomingDto.getZipCode().substring(0, 6));
        
        given()
            .contentType(ContentType.JSON)
            .body(addressIncomingDto)
            .when()
            .put(PATH+address.getId())
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());  
    }
    @Test
    public void shouldValidateZipCodeLength() {
        
        Address address = random.nextObject(Address.class);
        
        address = repository.save(address);

        AddressIncomingDto addressIncomingDto = random.nextObject(AddressIncomingDto.class);
        addressIncomingDto.setEmail(randomEmail());
        addressIncomingDto.setZipCode("1233567898786");
        
        given()
            .contentType(ContentType.JSON)
            .body(addressIncomingDto)
            .when()
            .put(PATH+address.getId())
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());  
    }
}
