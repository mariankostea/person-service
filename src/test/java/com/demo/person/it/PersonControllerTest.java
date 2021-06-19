package com.demo.person.it;

import com.demo.person.model.ErrorResponse;
import com.demo.person.model.Person;
import com.demo.person.model.PersonResponse;
import com.demo.person.model.PersonStatus;
import com.demo.person.service.dto.SystemPersonStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;

import java.util.UUID;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PersonControllerTest {

    private static final String URL = "http://localhost:%s/v1";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void createPerson() {

        var personId = UUID.randomUUID().toString();

        var personToCreate = Person.builder()
                .age(16)
                .email("testemail@email.com")
                .id(personId)
                .firstName("Johny")
                .lastName("D")
                .country("MD")
                .build();

        var personResponseEntity = testRestTemplate.postForEntity(format(URL, port) + "/person", personToCreate, PersonResponse.class);

        assertEquals(HttpStatus.OK, personResponseEntity.getStatusCode());
        assertNotNull(personResponseEntity.getBody());
        assertEquals(SystemPersonStatus.ADDED.getValue(), personResponseEntity.getBody().getStatus());
        assertEquals(personId, personResponseEntity.getBody().getId());
    }

    @Test
    void createPersonFailsWithSchemaValidation() {

        var personToCreate = Person.builder()
                .age(16)
                .email("testemail@email.com")
                .firstName("Johny")
                .lastName("D")
                .country("MD")
                .build();

        var personResponseEntity = testRestTemplate.postForEntity(format(URL, port) + "/person", personToCreate, ErrorResponse.class);

        assertEquals(HttpStatus.BAD_REQUEST, personResponseEntity.getStatusCode());
        assertNotNull(personResponseEntity.getBody());
        assertEquals("Bad Request", personResponseEntity.getBody().getErrorCode());
    }


    @Test
    void updatePersonStatus() {

        var personId = UUID.randomUUID().toString();
        var person = Person.builder()
                .age(16)
                .email("testemail@email.com")
                .id(personId)
                .firstName("Johny")
                .lastName("D")
                .country("MD")
                .build();
        testRestTemplate.postForEntity(format(URL, port) + "/person", person, PersonResponse.class);

        var personStatus = PersonStatus.builder()
                .status(PersonStatus.StatusEnum.IN_CHECK)
                .build();

        var statusUpdateResponseEntity = testRestTemplate.exchange(
                RequestEntity.put(format(URL, port) + format("/person/%s/status", personId))
                        .body(personStatus),
                Void.class);

        assertEquals(HttpStatus.OK, statusUpdateResponseEntity.getStatusCode());
    }
}