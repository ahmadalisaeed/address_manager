package com.addresses.manager.api.controller.addressesController;

import com.addresses.manager.api.data.Address;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;

/**
 *
 * @author ahmadalisaeed
 */
public class DestroyAddressTests extends BaseAddressesIT  {
	
    @Test
    public void shouldDestoryTheAddressById() {
        Address address = random.nextObject(Address.class);
        address = repository.save(address);
        
        given()
            .contentType(ContentType.JSON)
        .when()
            .delete(PATH+address.getId())
        .then()
            .statusCode(HttpStatus.OK.value());
    }
}
