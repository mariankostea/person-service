package com.demo.person.service.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum SystemPersonStatus {

    ADDED("ADDED"),
    IN_CHECK("IN-CHECK"),
    APPROVED("APPROVED"),
    ACTIVE("ACTIVE");

    private final String value;
}
