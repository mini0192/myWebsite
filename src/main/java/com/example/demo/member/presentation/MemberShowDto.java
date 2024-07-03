package com.example.demo.member.presentation;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "맴버 정보 출력폼")
public class MemberShowDto {
    private Long id;
    @NotBlank
    private String username;
    @NotBlank
    private String name;
    private String locked;
    private List<String> role;
}
