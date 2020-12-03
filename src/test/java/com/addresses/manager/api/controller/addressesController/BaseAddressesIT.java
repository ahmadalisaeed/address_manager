package com.addresses.manager.api.controller.addressesController;

import com.addresses.manager.api.config.AbstractTestConfiguration;
import com.addresses.manager.api.repository.AddressRepository;

import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.LocalServerPort;


import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;

/**
 *
 * @author ahmadalisaeed
 */
public class BaseAddressesIT extends AbstractTestConfiguration {
	
    @LocalServerPort
    protected int localPort;

    @Autowired
    protected AddressRepository repository;
    
    protected final String PATH = "/api/v1/addresses/";
    protected final EasyRandom random = new EasyRandom();

    
    @BeforeEach
    public void setUp() throws Exception {
    	RestAssured.port = localPort;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        repository.deleteAll();
    }
    
    @AfterEach
    public void cleanup(){
        repository.deleteAll();
    }
    
    protected String randomEmail(){
        return random.nextObject(String.class) 
                + "@" + 
                random.nextObject(String.class) 
                + "." +
                random.nextObject(String.class);
    }

}
