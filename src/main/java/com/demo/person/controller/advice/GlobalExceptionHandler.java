package com.demo.person.controller.advice;

import com.atlassian.oai.validator.report.ValidationReport;
import com.atlassian.oai.validator.springmvc.InvalidRequestException;
import com.demo.person.model.ErrorResponse;
import com.demo.person.service.exception.DuplicateRecordException;
import com.demo.person.service.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInvalidSchemaRequest(InvalidRequestException invalidRequestException) {

        String errorMessages = invalidRequestException.getValidationReport().getMessages()
                .stream()
                .map(ValidationReport.Message::getMessage)
                .collect(Collectors.joining("\n"));

        return ErrorResponse.builder()
                .errorCode(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .errorMessage(errorMessages)
                .build();
    }

    @ExceptionHandler(DuplicateRecordException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleRequestFail(DuplicateRecordException duplicateRecordException) {

        return ErrorResponse.builder()
                .errorCode(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .errorMessage(String.format("User with id {%s} already exists", duplicateRecordException.getPersonId()))
                .build();
    }


    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleGenericException(RuntimeException exception) {
        log.error("Caught exception:{}", exception.getMessage());

        return ErrorResponse.builder()
                .errorCode(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .errorMessage("Something went wrong")
                .build();
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleResourceNotFound(ResourceNotFoundException exception) {
        var errorMessage = String.format("Requested resource {%s} not found ", exception.getResource());
        log.error(errorMessage, exception);

        return ErrorResponse.builder()
                .errorMessage(errorMessage)
                .errorCode(HttpStatus.NOT_FOUND.getReasonPhrase())
                .build();
    }
}
