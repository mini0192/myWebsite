package com.example.demo.member.presentation;

import com.example.demo.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
@Tag(name = "맴버 API 목록(권한 없음)")
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    @Operation(summary = "전체 맴버 목록 확인")
    public ResponseEntity<List<MemberShowDto>> findAll() {
        System.out.println(UUID.randomUUID().toString());
        List<MemberShowDto> retnMemberShowDtoList = memberService.findAll();
        return new ResponseEntity<>(retnMemberShowDtoList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "특정 맴버 확인")
    public ResponseEntity<MemberShowDto> findId(@RequestParam("id") Long id) {
        MemberShowDto retnMemberShowDto = memberService.findId(id);
        return new ResponseEntity<>(retnMemberShowDto, HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "맴버 생성")
    public ResponseEntity<String> save(@Parameter(content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE))
                                              MemberSaveDto memberSaveDto) {
        memberService.save(memberSaveDto);
        return new ResponseEntity<>("CREATED", HttpStatus.CREATED);
    }
}
