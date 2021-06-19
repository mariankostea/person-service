package com.demo.person.controller;

import com.demo.person.api.PersonApi;
import com.demo.person.model.Person;
import com.demo.person.model.PersonResponse;
import com.demo.person.model.PersonStatus;
import com.demo.person.model.PersonStatusResponse;
import com.demo.person.service.PersonService;
import com.demo.person.service.dto.PersonDto;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.demo.person.service.dto.SystemPersonStatus.valueOf;


@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
@Api(tags = "Person")
public class PersonController implements PersonApi {

    private final PersonService personService;

    @Override
    public ResponseEntity<PersonResponse> createPerson(Person person) {
        final var personDto = PersonDto.builder()
                .personId(person.getId())
                .age(person.getAge())
                .firstName(person.getFirstName())
                .lastName(person.getLastName())
                .country(person.getCountry())
                .email(person.getEmail())
                .build();

        var createdPerson = personService.createPerson(personDto);

        final var personResponse = PersonResponse.builder()
                .id(createdPerson.getPersonId())
                .status(createdPerson.getStatus().toString())
                .build();

        return ResponseEntity.ok(personResponse);
    }

    @Override
    public ResponseEntity<PersonStatusResponse> updatePersonStatus(String id, PersonStatus personStatus) {
        personService.updatePersonStatus(id, valueOf(personStatus.getStatus().name()));

        return ResponseEntity.ok(new PersonStatusResponse("Status successfully updated"));
    }

}
