package com.example.demo.security.refreshToken.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class RefreshToken {
    @Id
    private String token;

    @Column(nullable = false)
    @Size(min = 1, max = 30)
    private String username;

    @Column(nullable = false)
    @Size(min = 1, max = 30)
    private String name;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> role;

    @Column(nullable = false)
    private int count;

    @Column(nullable = false)
    private long expirationTime;

    public void countSetting(int count) {
        this.count = count;
    }
}
