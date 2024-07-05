package com.example.demo.member.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;
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

    @Column(nullable = false)
    @Size(min = 5, max = 30)
    private String username;

    @Column(nullable = false)
    @Size(min = 10, max = 50)
    private String password;

    @Column(nullable = false)
    @Size(min = 2, max = 30)
    private String name;

    @Column(nullable = false)
    private String locked;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> role;

    public void passwordEncoding(String password) {
        this.password = password;
    }

    public static boolean equals(Member m1, Member m2) {
        if(!m1.getUsername().equals(m2.getUsername())) return false;
        if(!m1.getName().equals(m2.getName())) return false;
        if(!Arrays.equals(m1.getRole().toArray(), m2.getRole().toArray())) return false;

        return true;
    }
}
