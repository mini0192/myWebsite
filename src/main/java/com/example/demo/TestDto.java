package com.example.demo;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "djasd")
public class TestDto {
    @Schema(name = "이게 되야 되는데")
    private String test;
    @Schema(name = "이게 되야 되는데123")
    private String test1;
}
