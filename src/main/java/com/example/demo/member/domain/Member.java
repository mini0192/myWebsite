package com.example.demo.member.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Size(min = 1, max = 30)
    private String username;

    @Column
    @Size(min = 1, max = 100)
    private String password;

    @Column
    @Size(min = 1, max = 30)
    private String name;

    private String locked;

    @ElementCollection(fetch = FetchType.LAZY)
    private List<String> role;

    public void passwordEncoding(String password) {
        this.password = password;
    }
}
