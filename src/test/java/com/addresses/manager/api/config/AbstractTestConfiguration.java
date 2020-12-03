package com.addresses.manager.api.config;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.springframework.test.context.junit4.SpringRunner;


import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)

@ContextConfiguration(initializers = AbstractTestConfiguration.Initializer.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("urlShortenerTest")
public class AbstractTestConfiguration {
	
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres");

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        private static void startContainers() {
            postgres.start();
        }


        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            startContainers();

            ConfigurableEnvironment environment = applicationContext.getEnvironment();

            environment.getSystemProperties().put("spring.jpa.hibernate.ddl-auto", "create-drop");
            environment.getSystemProperties().put("spring.datasource.url", postgres.getJdbcUrl());
            environment.getSystemProperties().put("spring.datasource.username", postgres.getUsername());
            environment.getSystemProperties().put("spring.datasource.password", postgres.getPassword());

        }
    }
}