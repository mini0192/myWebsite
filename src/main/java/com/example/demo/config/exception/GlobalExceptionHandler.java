package com.example.demo.config.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NotFountDataException.class)
    public ResponseEntity<ErrorMessage> handlerNotFountDataException(NotFountDataException ex) {
        List<String> errorMessage = new ArrayList<>();
        errorMessage.add(ex.getMessage());
        errorMessage.forEach(s -> log.error("Worked NotFountDataException -> " + s));
        return new ResponseEntity<>(new ErrorMessage(errorMessage), HttpStatus.BAD_REQUEST);
    }
}
