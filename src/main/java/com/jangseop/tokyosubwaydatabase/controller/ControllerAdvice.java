package com.jangseop.tokyosubwaydatabase.controller;

import com.jangseop.tokyosubwaydatabase.controller.dto.response.ErrorResponse;
import com.jangseop.tokyosubwaydatabase.exception.duplicated.ObjectDuplicatedException;
import com.jangseop.tokyosubwaydatabase.exception.illegalformat.IllegalFormatException;
import com.jangseop.tokyosubwaydatabase.exception.notfound.DataNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ControllerAdvice {

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleDataNotFoundException(DataNotFoundException e) {
        log.error("handleDataNotFoundException", e);
        return new ResponseEntity<>(new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND.value(), e.getIdentifier()),
                HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ObjectDuplicatedException.class)
    public ResponseEntity<ErrorResponse> handleObjectDuplicatedException(ObjectDuplicatedException e) {
        log.error("handleObjectDuplicatedException {}", e.getDuplicated(), e);
        return new ResponseEntity<>(new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value(), e.getDuplicated()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalFormatException.class)
    public ResponseEntity<ErrorResponse> handleIllegalFormatException(IllegalFormatException e) {
        log.error("handleIllegalException", e);
        return new ResponseEntity<>(new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value(), e.getIllegalState()), HttpStatus.BAD_REQUEST);
    }
}
