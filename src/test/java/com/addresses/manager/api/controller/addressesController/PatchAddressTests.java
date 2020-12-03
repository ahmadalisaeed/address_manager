package com.addresses.manager.api.controller.addressesController;

import com.addresses.manager.api.data.Address;
import com.addresses.manager.api.data.AddressDto;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 * @author ahmadalisaeed
 */
public class PatchAddressTests extends BaseAddressesIT {

    @Test
    public void shouldPatchAndReturnUpdatedAddress() {
        
        Address address = random.nextObject(Address.class);
        
        address.setEmail(randomEmail());
        address.setZipCode(address.getZipCode().substring(0, 6));
        
        address = repository.save(address);
        
        String newEmail = "abc@xyz.com";
        String newStreet = "Prenzlauer Allee";


        String patchBody = "[ { \"op\": \"replace\", \"path\": \"/email\", \"value\": \""+ newEmail +"\" },\n"
                           + "{\"op\": \"add\", \"path\": \"/street\", \"value\": \""+ newStreet +"\" }]";
        
        AddressDto result;
        result = given()
                .contentType(ContentType.JSON)
                .body(patchBody)
                .when()
                .patch(PATH+address.getId())
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract().body()
                .jsonPath().getObject(".", AddressDto.class);

        assertThat(result.getEmail()).isEqualTo(newEmail);
        assertThat(result.getCountry()).isEqualTo(address.getCountry());
        assertThat(result.getStreet()).isEqualTo(newStreet);
        
        Address updatedAddress = repository.findById(address.getId()).orElse(null);
        
        assertThat(updatedAddress).isNotNull();
        assertThat(updatedAddress.getEmail()).isEqualTo(newEmail);
        assertThat(updatedAddress.getStreet()).isEqualTo(newStreet);
        assertThat(updatedAddress.getFirstName()).isEqualTo(address.getFirstName());
        
    }
    
    @Test
    public void shouldReturnNotFoundIfIdIsNotValid() {
        
        String newEmail = "abc@xyz";
        String newStreet = "Prenzlauer Allee";


        String patchBody = "[ { \"op\": \"replace\", \"path\": \"/email\", \"value\": \""+ newEmail +"\" },\n"
                           + "{\"op\": \"add\", \"path\": \"/street\", \"value\": \""+ newStreet +"\" }]";
        
        given()
            .contentType(ContentType.JSON)
            .body(patchBody)
            .when()
            .patch(PATH+random.nextLong())
            .then()
            .statusCode(HttpStatus.NOT_FOUND.value());  
    }
    
    @Test
    public void shouldReturnBadRequestIfObjectNotValid() {
        
        String newEmail = "abc";
        
        Address address = random.nextObject(Address.class);
        
        address = repository.save(address);


        String patchBody = "[ { \"op\": \"replace\", \"path\": \"/email\", \"value\": \""+ newEmail +"\" },\n"
                           + "{\"op\": \"remove\", \"path\": \"/firstName\" }]";
        
        given()
            .contentType(ContentType.JSON)
            .body(patchBody)
            .when()
            .patch(PATH+address.getId())
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());  
    }
}
