package com.attebjorner.hebara.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController
{
    private static final String PATH = "/error";

    @RequestMapping(PATH)
    public ResponseEntity<Map<String, String>> handleUnknownError()
    {
        return new ResponseEntity<>(Map.of("error", "unknown error"), HttpStatus.NOT_FOUND);
    }

    @Override
    public String getErrorPath()
    {
        return PATH;
    }
}
