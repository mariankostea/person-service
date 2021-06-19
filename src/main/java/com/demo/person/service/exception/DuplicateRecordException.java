package com.demo.person.service.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DuplicateRecordException extends RuntimeException {
    private final String personId;
}
