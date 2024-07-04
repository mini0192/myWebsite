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

    @Column
    @Size(min = 1, max = 30)
    private String username;

    @Column
    @Size(min = 1, max = 30)
    private String name;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> role;

    private int count;

    private long expirationTime;

    public void countSetting(int count) {
        this.count = count;
    }
}
