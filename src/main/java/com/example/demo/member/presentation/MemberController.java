package com.example.demo.member.presentation;

import com.example.demo.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
@Tag(name = "맴버 API 목록(권한 없음)")
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    @Operation(summary = "전체 맴버 목록 확인")
    @ApiResponses(
    value = {
        @ApiResponse(
            responseCode = "200",
            description = "맴버 목록 확인 완료"
        )
    })
    public ResponseEntity<List<MemberShowDto>> findAll(HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        log.info("{}: 전체 맴버 목록 확인", ip);
        List<MemberShowDto> retnMemberShowDtoList = memberService.findAll();
        return new ResponseEntity<>(retnMemberShowDtoList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "특정 맴버 확인")
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "맴버 목록 확인 완료"
            )
        }
    )
    public ResponseEntity<MemberShowDto> findId(@PathVariable("id") Long id, HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        log.info("{}: {}번 맴버 확인", ip, id);
        MemberShowDto retnMemberShowDto = memberService.findId(id);
        return new ResponseEntity<>(retnMemberShowDto, HttpStatus.OK);
    }

    @GetMapping("/check/{username}")
    @Operation(summary = "아이디 중복 확인")
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "아이디가 중복되지 않음"
            ),
            @ApiResponse(
                responseCode = "409",
                description = "아이디가 중복됨"
            )
        }
    )
    public ResponseEntity<String> checkUsername(@PathVariable("username") String username, HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        boolean check = memberService.checkUsername(username);
        if(check) {
            log.info("{}: {} 아이디 중복", ip, username);
            return new ResponseEntity<>("아이디가 중복되었습니다.", HttpStatus.CONFLICT);
        } else {
            log.info("{}: {} 아이디 중복되지 않음", ip, username);
            return new ResponseEntity<>("아이디가 중복되지 않았습니다.", HttpStatus.OK);
        }
    }


    @PostMapping
    @Operation(summary = "맴버 생성")
    @GetMapping("/check/{username}")
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "201",
                description = "맴버 생성 완료"
            ),
            @ApiResponse(
                responseCode = "409",
                description = "아이디가 중복됨"
            )
        }
    )
    public ResponseEntity<String> save(@Parameter(content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
                                        @Valid @RequestBody MemberSaveDto memberSaveDto,
                                        HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        log.info("{}: 맴버 생성1", ip);
        memberService.save(memberSaveDto);
        return new ResponseEntity<>("생성 완료", HttpStatus.CREATED);
    }
}