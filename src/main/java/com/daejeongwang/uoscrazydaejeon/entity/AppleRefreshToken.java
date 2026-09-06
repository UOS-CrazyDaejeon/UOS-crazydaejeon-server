package com.daejeongwang.uoscrazydaejeon.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AppleRefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long userId;

    @Column(nullable = false, length = 1000)
    private String token;

    private LocalDateTime updatedAt;

    @Builder
    public AppleRefreshToken(Long userId, String token, LocalDateTime updatedAt) {
        this.userId = userId;
        this.token = token;
        this.updatedAt = updatedAt;
    }

    public void updateToken(String token) {
        this.token = token;
        this.updatedAt = LocalDateTime.now();
    }
}
