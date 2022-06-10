package com.mvc.forrest.dao.firebase;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import com.mvc.forrest.service.domain.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class FCMTokenDAO {

    private final StringRedisTemplate tokenRedisTemplate;

    public void saveToken(User loginUser) {
        tokenRedisTemplate.opsForValue()
        .set(loginUser.getUserId(), loginUser.getPushToken());
    }

    public String getToken(String email) {
        return tokenRedisTemplate.opsForValue().get(email);
    }

    public void deleteToken(String email) {
        tokenRedisTemplate.delete(email);
    }

    public boolean hasKey(String email) {
        return tokenRedisTemplate.hasKey(email);
    }
}