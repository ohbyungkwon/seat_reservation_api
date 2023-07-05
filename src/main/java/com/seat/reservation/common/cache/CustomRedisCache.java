package com.seat.reservation.common.cache;

import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheWriter;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CustomRedisCache extends RedisCache {


    /**
     * Create new {@link RedisCache}.
     *
     * @param name        must not be {@literal null}.
     * @param cacheWriter must not be {@literal null}.
     * @param cacheConfig must not be {@literal null}.
     */
    protected CustomRedisCache(String name,
            RedisCacheWriter cacheWriter, RedisCacheConfiguration cacheConfig) {
        super(name, cacheWriter, cacheConfig);
    }

    public static Map<String, CustomRedisCache> createCacheMap(
            Set<String> cacheSet, RedisCacheWriter cacheWriter, RedisCacheConfiguration cacheConfig) {
        Map<String, CustomRedisCache> cacheMap = new HashMap<>();
        for(String name: cacheSet){
            cacheMap.put(name, new CustomRedisCache(name, cacheWriter, cacheConfig));
        }
        return cacheMap;
    }

    public Object getValue(Object key) {
        return this.lookup(key);
    }
}
