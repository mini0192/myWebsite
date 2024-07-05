package com.example.demo.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.member.domain.MemberDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Date;

@Configuration
public class JwtProvider {
    @Value("${JwtKey}")
    public String SECRET;
    public int JWT_TOKEN_EXPIRATION_TIME =  60000 * 10;
    public int REFRESH_TOKEN_EXPIRATION_TIME =  60000 * 10 * 60;
    public String TOKEN_PREFIX = "Bearer ";
    public String HEADER_STRING = "Authorization";
    public String REFRESH_HEADER_STRING = "Refresh";

    public String createJwtToken(MemberDetails memberDetails) {
        System.out.println(SECRET);
        Collection<? extends GrantedAuthority> roleList = memberDetails.getAuthorities();
        String[] role = roleList.stream().map(GrantedAuthority::getAuthority).toArray(String[]::new);

        return JWT.create()
                .withExpiresAt(new Date(System.currentTimeMillis() + JWT_TOKEN_EXPIRATION_TIME))
                .withClaim("username", memberDetails.getUsername())
                .withClaim("name", memberDetails.getName())
                .withArrayClaim("role", role)
                .sign(Algorithm.HMAC512(SECRET));
    }

    public String createRefreshToken(MemberDetails memberDetails) {
        Collection<? extends GrantedAuthority> roleList = memberDetails.getAuthorities();
        String[] role = roleList.stream().map(GrantedAuthority::getAuthority).toArray(String[]::new);

        return JWT.create()
                .withExpiresAt(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION_TIME))
                .withClaim("username", memberDetails.getUsername())
                .withClaim("name", memberDetails.getName())
                .withArrayClaim("role", role)
                .sign(Algorithm.HMAC512(SECRET));
    }

    public DecodedJWT decodeJwtToken(String jwtToken) {
        return JWT.require(Algorithm.HMAC512(SECRET))
                .build()
                .verify(jwtToken);
    }
}
