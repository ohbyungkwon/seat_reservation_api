package com.seat.reservation.common.config;

import com.seat.reservation.common.cache.CustomRedisCache;
import com.seat.reservation.common.cache.CustomRedisCacheWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Configuration
public class RedisConfig {
    public static final String DEFAULT_REDIS_STORAGE = "reservation-redis-storage";

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
    public CustomRedisCacheWriter redisCacheWriter(){
        return new CustomRedisCacheWriter(redisConnectionFactory());
    }

    @Bean
    public RedisCacheConfiguration redisCacheConfiguration(){
        return RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
    }

    @Bean
    public CustomRedisCache redisCacheManager(){
        return new CustomRedisCache(DEFAULT_REDIS_STORAGE, redisCacheWriter(), redisCacheConfiguration());
    }
}
