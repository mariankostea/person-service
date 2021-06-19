package com.demo.person.service;

import com.demo.person.persistence.entity.PersonEntity;
import com.demo.person.persistence.repository.PersonRepository;
import com.demo.person.service.dto.PersonDto;
import com.demo.person.service.dto.SystemPersonStatus;
import com.demo.person.service.exception.DuplicateRecordException;
import com.demo.person.service.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.demo.person.service.dto.SystemPersonStatus.ADDED;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    @Transactional
    public PersonDto createPerson(final PersonDto person) {

        if (personRepository.existsByPersonId(person.getPersonId()))
            throw new DuplicateRecordException(person.getPersonId());

        PersonEntity entity = new PersonEntity();
        entity.setPersonId(person.getPersonId());
        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAge(person.getAge());
        entity.setEmail(person.getEmail());
        entity.setCountry(person.getCountry());
        entity.setStatus(ADDED);

        PersonEntity savedPerson = personRepository.save(entity);

        return PersonDto.builder()
                .personId(savedPerson.getPersonId())
                .status(savedPerson.getStatus())
                .build();

    }

    @Transactional
    public void updatePersonStatus(final String personId, final SystemPersonStatus status) {

        var personEntity = personRepository.findByPersonId(personId)
                .orElseThrow(() -> new ResourceNotFoundException(personId));

        personEntity.setStatus(status);
    }

}
