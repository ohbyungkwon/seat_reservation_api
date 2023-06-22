package com.seat.reservation.common.cache;

import com.seat.reservation.common.config.RedisConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheWriter;

public class CustomRedisCache extends RedisCache {

    /**
     * Create new {@link RedisCache}.
     *
     * @param name        must not be {@literal null}.
     * @param cacheWriter must not be {@literal null}.
     * @param cacheConfig must not be {@literal null}.
     */
    public CustomRedisCache(String name, RedisCacheWriter cacheWriter, RedisCacheConfiguration cacheConfig) {
        super(name, cacheWriter, cacheConfig);
    }

    public Object getValue(Object key) {
        return this.lookup(key);
    }
}
