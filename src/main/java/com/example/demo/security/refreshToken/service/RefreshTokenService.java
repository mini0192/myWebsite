package com.example.demo.security.refreshToken.service;

import com.example.demo.config.NotFountDataException;
import com.example.demo.security.refreshToken.domain.RefreshToken;
import com.example.demo.security.refreshToken.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@EnableScheduling
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public void save(RefreshToken refreshToken) {
        refreshToken.countSetting(0);
        refreshTokenRepository.save(refreshToken);
    }

    @Transactional
    public Optional<RefreshToken> findToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    @Transactional
    public void deleteToken(RefreshToken refreshToken) {
        refreshTokenRepository.delete(refreshToken);
    }

    @Transactional
    public void addCount(RefreshToken refreshToken) {
        int count = refreshToken.getCount() + 1;
        refreshToken.countSetting(count);
        refreshTokenRepository.save(refreshToken);
    }

    @Scheduled(fixedDelay = 1000) // 1초마다 실행
    public void deleteExpirationToken() {
        List<RefreshToken> savedRefreshTokenList = refreshTokenRepository.findByOrderByExpirationTime();
        long time = System.currentTimeMillis();

        for(RefreshToken data : savedRefreshTokenList) {
            long dataTime = data.getExpirationTime();
            if(time < dataTime) break;

            refreshTokenRepository.delete(data);
            log.info("만료된 Refresh Token 삭제");
        }
    }
}
