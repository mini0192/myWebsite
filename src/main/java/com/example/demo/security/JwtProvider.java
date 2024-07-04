package com.example.demo.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.member.domain.MemberDetails;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Date;

public class JwtProvider {
    public static String SECRET = "asdasdasdasdasdasd";
    public static int JWT_TOKEN_EXPIRATION_TIME =  60000 * 10;
    public static int REFRESH_TOKEN_EXPIRATION_TIME =  60000 * 10 * 60;
    public static String TOKEN_PREFIX = "Bearer ";
    public static String HEADER_STRING = "Authorization";
    public static String REFRESH_HEADER_STRING = "Refresh";

    public static String createJwtToken(MemberDetails memberDetails) {
        Collection<? extends GrantedAuthority> roleList = memberDetails.getAuthorities();
        String[] role = roleList.stream().map(GrantedAuthority::getAuthority).toArray(String[]::new);

        return JWT.create()
                .withExpiresAt(new Date(System.currentTimeMillis() + JWT_TOKEN_EXPIRATION_TIME))
                .withClaim("username", memberDetails.getUsername())
                .withClaim("name", memberDetails.getName())
                .withArrayClaim("role", role)
                .sign(Algorithm.HMAC512(SECRET));
    }

    public static String createRefreshToken(MemberDetails memberDetails) {
        Collection<? extends GrantedAuthority> roleList = memberDetails.getAuthorities();
        String[] role = roleList.stream().map(GrantedAuthority::getAuthority).toArray(String[]::new);

        return JWT.create()
                .withExpiresAt(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION_TIME))
                .withClaim("username", memberDetails.getUsername())
                .withClaim("name", memberDetails.getName())
                .withArrayClaim("role", role)
                .sign(Algorithm.HMAC512(SECRET));
    }

    public static DecodedJWT decodeJwtToken(String jwtToken) {
        return JWT.require(Algorithm.HMAC512(SECRET))
                .build()
                .verify(jwtToken);
    }
}
