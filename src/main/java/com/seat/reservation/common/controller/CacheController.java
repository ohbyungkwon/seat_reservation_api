package com.seat.reservation.common.controller;

import com.seat.reservation.common.cache.CustomRedisCache;
import com.seat.reservation.common.dto.ResponseComDto;
import com.seat.reservation.common.exception.NotFoundInputException;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/common")
public class CacheController {
    private final RedisCacheManager redisCacheManager;

    @Autowired
    public CacheController(RedisCacheManager redisCacheManager) {
        this.redisCacheManager = redisCacheManager;
    }

    /**
     * @param name
     * @param key
     * @return ResponseEntity<ResponseComDto>
     * @throws Exception
     * - 캐시 수동 제거
     */
    @DeleteMapping("/cache/{name}/{key}")
    public ResponseEntity<ResponseComDto> deleteCache(@PathVariable String name, @PathVariable String key) throws Exception{
        CustomRedisCache cache = (CustomRedisCache) redisCacheManager.getCache(name);
        if(Objects.isNull(cache)) {
            throw new NotFoundException("캐시가 존재하지 않습니다.");
        }

        cache.evict(key);

        return new ResponseEntity<>(ResponseComDto.builder()
                .resultObj(null)
                .resultMsg("케시 삭제 완료.")
                .build(), HttpStatus.OK);
    }

    /**
     * @param name
     * @param key
     * @return ResponseEntity<ResponseComDto>
     * @throws Exception
     * - 캐시 내용 조회
     */
    @GetMapping("/cache/{name}/{key}")
    public ResponseEntity<ResponseComDto> getCache(@PathVariable String name, @PathVariable String key) throws Exception{
        CustomRedisCache cache = (CustomRedisCache) redisCacheManager.getCache(name);
        if(Objects.isNull(cache)){
            throw new NotFoundException("캐시가 존재하지 않습니다.");
        }

        Object body = null;
        Cache.ValueWrapper wrapper = cache.get(key);
        if(wrapper != null) {
            body = wrapper.get();
        }

        return new ResponseEntity<>(ResponseComDto.builder()
                .resultObj(body)
                .resultMsg("캐시 조회 완료")
                .build(), HttpStatus.OK);
    }
}
