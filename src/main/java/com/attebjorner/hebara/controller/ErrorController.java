package com.attebjorner.hebara.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("error")
public class ErrorController
{
    @GetMapping
    public ResponseEntity<Map<String, String>> handleUnknownError()
    {
        return new ResponseEntity<>(Map.of("error", "unknown error"), HttpStatus.NOT_FOUND);
    }
}
