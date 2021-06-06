package com.attebjorner.hebara.controller;

import com.attebjorner.hebara.exception_handling.NoSentencesFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class ExceptionController
{
    @ExceptionHandler
    public ResponseEntity<Map<String, String>> handleException(NoSentencesFoundException e)
    {
        return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
