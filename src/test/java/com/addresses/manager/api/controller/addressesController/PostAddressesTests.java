package com.addresses.manager.api.controller.addressesController;

import com.addresses.manager.api.data.Address;
import com.addresses.manager.api.data.AddressDto;
import com.addresses.manager.api.data.AddressIncomingDto;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import io.restassured.http.ContentType;

/**
 *
 * @author ahmadalisaeed
 */
public class PostAddressesTests extends BaseAddressesIT {
	
    @Test
    public void shouldCreateAndReturnCreatedAddress() {

        AddressIncomingDto addressIncomingDto = random.nextObject(AddressIncomingDto.class);
        addressIncomingDto.setEmail(randomEmail());
        addressIncomingDto.setZipCode(addressIncomingDto.getZipCode().substring(0, 6));
        
        AddressDto result = given()
            .contentType(ContentType.JSON)
            .body(addressIncomingDto)
            .when()
            .post(PATH)
            .then()
            .statusCode(HttpStatus.CREATED.value())
            .extract().body()
            .jsonPath().getObject(".", AddressDto.class);

        assertThat(result.getId()).isNotNull();
        assertThat(result.getFirstName()).isEqualTo(addressIncomingDto.getFirstName());
        
        Address address = repository.findById(result.getId()).orElse(null);
        assertThat(address).isNotNull();
        assertThat(address.getLastName()).isEqualTo(result.getLastName());
        assertThat(address.getEmail()).isEqualTo(result.getEmail());
        assertThat(address.getStreet()).isEqualTo(result.getStreet());
        assertThat(address.getZipCode()).isEqualTo(result.getZipCode());   
    }
    
    @Test
    public void shouldValidateEmailAddress() {

        AddressIncomingDto addressIncomingDto = random.nextObject(AddressIncomingDto.class);
        
        given()
            .contentType(ContentType.JSON)
            .body(addressIncomingDto)
            .when()
            .post(PATH)
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());  
    }
	
	
}
