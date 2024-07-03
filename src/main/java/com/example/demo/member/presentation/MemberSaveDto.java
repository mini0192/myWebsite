package com.example.demo.member.presentation;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "맴버 회원가입 정보 입력폼")
public class MemberSaveDto {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String name;
}
