package com.addresses.manager.api.controller.addressesController;

import com.addresses.manager.api.data.Address;
import java.util.List;
import java.util.stream.Stream;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.addresses.manager.api.data.AddressDto;


import static io.restassured.RestAssured.given;
import java.util.ArrayList;
import java.util.stream.Collectors;
import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 * @author ahmadalisaeed
 */
public class GetAllAddressesTests extends BaseAddressesIT  {
	
    @Test
    public void shouldReturnAnEmptyArray() {

        List<AddressDto> result =
        given()
            .contentType(ContentType.JSON)
        .when()
            .get(PATH)
        .then()
            .statusCode(HttpStatus.OK.value())
            .extract().body()
            .jsonPath().getList(".", AddressDto.class);

        assertThat(result).hasSize(0);
    }

    @Test
    public void shouldReturnAnArrayOfSizeOne() {

        Address address = random.nextObject(Address.class);
        address = repository.save(address);

        List<AddressDto> result =
        given()
            .contentType(ContentType.JSON)
        .when()
            .get(PATH)
        .then()
            .statusCode(HttpStatus.OK.value())
            .extract().body()
            .jsonPath().getList(".", AddressDto.class);

        AddressDto addressDto = result.get(0);

        assertThat(result).hasSize(1);
        assertThat(addressDto.getId()).isEqualTo(address.getId());
        assertThat(addressDto.getFirstName()).isEqualTo(address.getFirstName());
        assertThat(addressDto.getLastName()).isEqualTo(address.getLastName());
        assertThat(addressDto.getEmail()).isEqualTo(address.getEmail());
        assertThat(addressDto.getStreet()).isEqualTo(address.getStreet());
        assertThat(addressDto.getZipCode()).isEqualTo(address.getZipCode());
        assertThat(addressDto.getCountry()).isEqualTo(address.getCountry());

    }

    @Test
    public void shouldReturnAllSavedAddresses() {
        final int count = 20;

        Stream<Address> addressesStream = random.objects(Address.class, count);

        addressesStream.forEach(address -> {
            address.setEmail(randomEmail());
            repository.save(address);
        });

        List<AddressDto> result =
        given()
            .contentType(ContentType.JSON)
        .when()
            .get(PATH)
        .then()
            .statusCode(HttpStatus.OK.value())
            .extract().body()
            .jsonPath().getList(".", AddressDto.class);

        assertThat(result).hasSize(count);

    }

    @Test
    public void shouldReturnMatchingSearchByFirstName() {
        final int count = 20;

        Stream<Address> addressesStream = random.objects(Address.class, count);

        addressesStream.forEach(address -> {
            address.setEmail(randomEmail());
            repository.save(address);
        });

        Address address = random.nextObject(Address.class);
        address.setEmail(randomEmail());
        address.setFirstName(random.nextObject(String.class));
        address = repository.save(address);


        List<AddressDto> result =
        given()
            .queryParam("search", "firstName:"+address.getFirstName())
            .contentType(ContentType.JSON)
        .when()
            .get(PATH)
        .then()
            .statusCode(HttpStatus.OK.value())
            .extract().body()
            .jsonPath().getList(".", AddressDto.class);

        assertThat(result).hasSize(1);
        AddressDto addressDto = result.get(0);
        assertThat(addressDto.getFirstName()).isEqualTo(address.getFirstName());

    }

    @Test
    public void shouldReturnMatchingSearchByLastName() {
        final int count = 20;

        Stream<Address> addressesStream = random.objects(Address.class, count);

        addressesStream.forEach(address -> {
            address.setEmail(randomEmail());
            repository.save(address);
        });

        Address address = random.nextObject(Address.class);
        address.setEmail(randomEmail());
        address.setLastName(random.nextObject(String.class));
        address = repository.save(address);


        List<AddressDto> result =
        given()
            .queryParam("search", "lastName:"+address.getLastName())
            .contentType(ContentType.JSON)
        .when()
            .get(PATH)
        .then()
            .statusCode(HttpStatus.OK.value())
            .extract().body()
            .jsonPath().getList(".", AddressDto.class);

        assertThat(result).hasSize(1);
        AddressDto addressDto = result.get(0);
        assertThat(addressDto.getLastName()).isEqualTo(address.getLastName());

    }

    @Test
    public void shouldReturnMatchingSearchByEmail() {
        final int count = 20;

        Stream<Address> addressesStream = random.objects(Address.class, count);

        addressesStream.forEach(address -> {
            address.setEmail(randomEmail());
            repository.save(address);
        });

        Address address = random.nextObject(Address.class);
        address.setEmail("this_should_be_a@uniq.email");
        address = repository.save(address);

        List<AddressDto> result;
        result = given()
                .queryParam("search", "email:"+address.getEmail())
                .contentType(ContentType.JSON).log().all()
                .when()
                .get(PATH)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract().body()
                .jsonPath().getList(".", AddressDto.class);

        assertThat(result).hasSize(1);
        AddressDto addressDto = result.get(0);
        assertThat(addressDto.getEmail()).isEqualTo(address.getEmail());

    }

    @Test
    public void shouldReturnMatchingSearchByCompositeAttributes() {
        final int count = 15;

        Stream<Address> addressesStream = random.objects(Address.class, count);

        List<Address> addresses = addressesStream.map(address -> {
            address.setEmail(randomEmail());
            return repository.save(address);
        }).collect(Collectors.toList());

        Address address1 = addresses.get(3);
        Address address2 = addresses.get(5);
        Address address3 = addresses.get(7);
        Address address4 = addresses.get(8);
        Address address5 = addresses.get(9);

        List<String> filters = new ArrayList<>();
        filters.add("email:"+address1.getEmail());
        filters.add("firstName:"+address2.getFirstName());
        filters.add("lastName:"+address3.getLastName());
        filters.add("street:"+address4.getStreet());
        filters.add("zipCode:"+address5.getZipCode());

        List<AddressDto> result;
        result = given()
                .params("search", String.join(",", filters))
                .contentType(ContentType.JSON)
                .log().all()
                .when()
                .get(PATH)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract().body()
                .jsonPath().getList(".", AddressDto.class);

        assertThat(result).hasSize(5);
    }

    @Test
    public void shouldReturnEmptyArrayifNoMatchingAddressFound() {
        final int count = 20;

        Stream<Address> addressesStream = random.objects(Address.class, count);

        addressesStream.map(address -> {
            address.setEmail(randomEmail());
            return repository.save(address);
        });

        List<String> filters = new ArrayList<>();
        filters.add("email:"+"NO_EMAIL_SHOULD_BE_LIKE_THIS@@ASD>>S");
        filters.add("firstName:"+"IDONTTHINKSO");
        filters.add("lastName:"+"AQuickbrownFox");

        List<AddressDto> result;
        result = given()
                .params("search", String.join(",", filters))
                .contentType(ContentType.JSON)
                .log().all()
                .when()
                .get(PATH)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract().body()
                .jsonPath().getList(".", AddressDto.class);

        assertThat(result).hasSize(0);
    }

	
}
