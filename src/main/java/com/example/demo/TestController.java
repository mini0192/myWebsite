package com.example.demo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@Tag(name = "Test", description = "음냐")
public class TestController {
    @GetMapping
    @Operation(description = "해치웠나?")
    public String test() {
        return "제발 좀 깔끔하게 되라..123 날왜 괴롭혀.. 해치웠나?";
    }

    @PostMapping("/test")
    @Operation(summary = "tlqkf ehofk", description = "됐나..?")
    public ResponseEntity<String> test1(@RequestBody TestDto testDto) {
        return new ResponseEntity<>("test", HttpStatus.OK);
    }
}