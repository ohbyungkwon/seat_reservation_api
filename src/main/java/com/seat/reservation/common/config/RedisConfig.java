package com.seat.reservation.common.config;

import com.seat.reservation.common.cache.CustomRedisCache;
import com.seat.reservation.common.cache.CustomRedisCacheManager;
import com.seat.reservation.common.cache.CustomRedisCacheWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
public class RedisConfig {
    public static final String DEFAULT_REDIS_STORAGE = "reservation-redis-storage";

    @Value("${spring.redis.caches}")
    private String caches;

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    private RedisServer redisServer;

    @PostConstruct
    public void startServer() {
        redisServer = new RedisServer(port);
        try {
            redisServer.start();
        } catch (Exception e){
            redisServer.stop();
            redisServer.start();
        }
    }

    @PreDestroy
    public void stopServer() {
        redisServer.stop();
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(host, port);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(){
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        return redisTemplate;
    }

    @Bean
    public RedisCacheManager redisCacheManager() {
        Set<String> cacheSet = Arrays.stream(caches.split(",")).collect(Collectors.toSet());
        return RedisCacheManager.RedisCacheManagerBuilder.fromCacheWriter(redisCacheWriter())
                .cacheDefaults(redisCacheConfiguration())
                .disableCreateOnMissingCache()
                .initialCacheNames(cacheSet)
                .build();
    }

    @Bean
    public CustomRedisCacheWriter redisCacheWriter(){
        return new CustomRedisCacheWriter(redisConnectionFactory());
    }

    @Bean
    public RedisCacheConfiguration redisCacheConfiguration(){
        return RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
    }

    /**
     * Default로 지정된 Cache를 사용할 경우 바로 DI 가능하도록 구성
     */
    @Bean
    public CustomRedisCache redisCache(){
        return new CustomRedisCache(DEFAULT_REDIS_STORAGE, redisCacheWriter(), redisCacheConfiguration());
    }
}
