package com.example.demo.config;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class ValidationConfig {
    public <T> void checkValid(T validactionTarget) {}
}
