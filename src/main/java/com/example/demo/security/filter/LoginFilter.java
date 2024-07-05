package com.example.demo.security.filter;

import com.example.demo.security.JwtProvider;
import com.example.demo.member.domain.MemberDetails;
import com.example.demo.security.refreshToken.domain.RefreshToken;
import com.example.demo.security.refreshToken.service.RefreshTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
    private final JwtProvider jwtProvider;
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        String username = obtainUsername(request);
        if(username == null || username.isEmpty()) {
            log.error("로그인 실패 -> username 정보 없음");
            response.setStatus(401);
            return null;
        }

        String password = obtainPassword(request);
        if(password == null || password.isEmpty()) {
            log.error("로그인 실패 -> password 정보 없음");
            response.setStatus(401);
            return null;
        }

        log.info("username: {} -> 로그인 시도", username);

        Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);
        return authenticationManager.authenticate(authentication);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        MemberDetails memberDetails = (MemberDetails) authResult.getPrincipal();
        log.info("username: {} -> 로그인 성공", memberDetails.getUsername());

        String jwtToken = jwtProvider.TOKEN_PREFIX + jwtProvider.createJwtToken(memberDetails);
        String refreshToken = jwtProvider.TOKEN_PREFIX + jwtProvider.createRefreshToken(memberDetails);

        List<String> memberRole = memberDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toList();

        refreshTokenService.save(RefreshToken.builder()
                        .token(refreshToken)
                        .username(memberDetails.getUsername())
                        .name(memberDetails.getName())
                        .role(memberRole)
                        .expirationTime(System.currentTimeMillis() + jwtProvider.REFRESH_TOKEN_EXPIRATION_TIME)
                        .build());

        response.addHeader(jwtProvider.HEADER_STRING, jwtToken);
        response.addHeader(jwtProvider.REFRESH_HEADER_STRING, refreshToken);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        log.error("Message: {} -> 로그인 실패", failed.getMessage());
        response.setStatus(401);
    }
}
