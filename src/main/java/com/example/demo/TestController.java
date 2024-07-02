package com.example.demo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@Tag(name = "Test", description = "음냐")
public class TestController {
    @GetMapping
    @Operation(description = "해치웠나?")
    public String test() {
        return "제발 좀 깔끔하게 되라..123";
    }

    @GetMapping("/test")
    @Operation(description = "됐나..?")
    public String test1() {
        return "제발 좀 깔끔하게 되라..123";
    }
}