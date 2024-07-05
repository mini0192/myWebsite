package com.example.demo.security;

import com.example.demo.security.filter.JwtFilter;
import com.example.demo.security.filter.LoginFilter;
import com.example.demo.security.refreshToken.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SecurityFilterConfig {

    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
    private final JwtProvider jwtProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)

                .cors(c -> c.configurationSource(configurationSource()))
                .sessionManagement(auth -> auth.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .addFilterBefore(new JwtFilter(refreshTokenService, jwtProvider), UsernamePasswordAuthenticationFilter.class)
                .addFilterAt(new LoginFilter(authenticationManager, refreshTokenService, jwtProvider), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    private CorsConfigurationSource configurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.addAllowedOrigin("123.99.104.76");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");

        configuration.setExposedHeaders(List.of(jwtProvider.HEADER_STRING, jwtProvider.REFRESH_HEADER_STRING));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
