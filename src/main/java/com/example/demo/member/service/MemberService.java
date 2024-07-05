package com.example.demo.member.service;

import com.example.demo.config.exception.DataCrashException;
import com.example.demo.config.exception.NotFountDataException;
import com.example.demo.config.ValidationConfig;
import com.example.demo.member.domain.Member;
import com.example.demo.member.presentation.MemberSaveDto;
import com.example.demo.member.presentation.MemberShowDto;
import com.example.demo.member.repository.MemberRepository;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final ValidationConfig validationConfig;

    public List<MemberShowDto> findAll() {
        List<Member> savedMemberList = memberRepository.findAll();
        return savedMemberList.stream().map(this::toDto).toList();
    }

    public MemberShowDto findId(Long takenId) {
        Member savedMember = memberRepository.findById(takenId)
                .orElseThrow(() -> new NotFountDataException("유저를 찾을 수 없습니다."));
        return this.toDto(savedMember);
    }

    @Transactional
    public void save(MemberSaveDto takenMemberSaveDto) {
        String takenUsername = takenMemberSaveDto.getUsername();
        if(memberRepository.existsByUsername(takenUsername))
            throw new DataCrashException("아이디가 중복되었습니다.");

        Member takenMember = this.toEntity(takenMemberSaveDto);
        validationConfig.checkValid(takenMember);

        takenMember.passwordEncoding(passwordEncoder.encode(takenMember.getPassword()));
        memberRepository.save(takenMember);
    }

    public boolean checkUsername(String takenUsername) {
        return memberRepository.existsByUsername(takenUsername);
    }

    private MemberShowDto toDto(Member member) {
        return MemberShowDto.builder()
                .id(member.getId())
                .username(member.getUsername())
                .name(member.getName())
                .locked(member.getLocked())
                .role(member.getRole())
                .build();
    }

    private Member toEntity(MemberSaveDto memberSaveDto) {
        return Member.builder()
                .username(memberSaveDto.getUsername())
                .password(memberSaveDto.getPassword())
                .name(memberSaveDto.getName())
                .locked("false")
                .role(List.of("ROLE_USER"))
                .build();
    }
}
