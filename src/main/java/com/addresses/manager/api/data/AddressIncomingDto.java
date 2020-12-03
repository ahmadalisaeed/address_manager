package com.addresses.manager.api.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
/**
 *
 * @author ahmadalisaeed
 */

public class AddressIncomingDto {
    
    @JsonProperty(required = true)
    private String firstName;
    
    @JsonProperty(required = false)
    private String lastName;
    
    @JsonProperty(required = false)
    @Email(message = "Email should be valid")
    private String email;
    
    @JsonProperty(required = false)
    private Date dateOfBirth;
    
    @JsonProperty(required = false)
    private String street;
    
    @JsonProperty(required = false)
    @Size(max = 10)
    private String zipCode;
    
    @JsonProperty(required = false)
    private String country;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
    
}
