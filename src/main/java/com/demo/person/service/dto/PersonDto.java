package com.demo.person.service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class PersonDto {

    private final String personId;
    private final String firstName;
    private final String lastName;
    private final int age;
    private final String email;
    private final String country;
    private final SystemPersonStatus status;

}
