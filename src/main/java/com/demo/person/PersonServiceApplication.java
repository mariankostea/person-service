package com.demo.person;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication(scanBasePackages = {"com.demo.person.configuration",
        "com.demo.person.persistence.entity",
        "com.demo.person.persistence.repository",
        "com.demo.person.service",
        "com.demo.person.controller"})
public class PersonServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersonServiceApplication.class, args);
    }

}
