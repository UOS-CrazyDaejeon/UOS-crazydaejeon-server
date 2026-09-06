package com.daejeongwang.uoscrazydaejeon.repository;

import com.daejeongwang.uoscrazydaejeon.entity.AppleRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppleRefreshTokenRepository extends JpaRepository<AppleRefreshToken, Long> {

    Optional<AppleRefreshToken> findByUserId(Long userId);

    void deleteByUserId(Long userId);
}
