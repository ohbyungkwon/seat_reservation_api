package com.seat.reservation.common.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Cache Manager Bean 생성
 * Cache 제어용도
 */
@Component
public class CustomRedisCacheManager extends RedisCacheManager {
    private final RedisCacheWriter redisCacheWriter;
    private final RedisCacheConfiguration redisCacheConfiguration;

    @Autowired
    public CustomRedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration,
                                    Map<String, RedisCacheConfiguration> initialCacheConfigurations) {
        super(cacheWriter, defaultCacheConfiguration, initialCacheConfigurations, Boolean.FALSE);
        this.redisCacheWriter = cacheWriter;
        this.redisCacheConfiguration = defaultCacheConfiguration;
    }

    @Override
    protected RedisCache createRedisCache(String name, @Nullable RedisCacheConfiguration cacheConfig) {
        return new CustomRedisCache(name, redisCacheWriter, cacheConfig != null ? cacheConfig : redisCacheConfiguration);
    }

    @Override
    protected RedisCache getMissingCache(String name) {
        return null;
    }
}
