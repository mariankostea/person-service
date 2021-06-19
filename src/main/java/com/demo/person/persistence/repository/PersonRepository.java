package com.demo.person.persistence.repository;

import com.demo.person.persistence.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface PersonRepository extends JpaRepository<PersonEntity, Long> {

    Optional<PersonEntity> findByPersonId(String personId);

    boolean existsByPersonId(String personId);
}
