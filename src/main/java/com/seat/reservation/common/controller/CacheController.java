package com.seat.reservation.common.controller;

import com.seat.reservation.common.cache.CustomRedisCacheManager;
import com.seat.reservation.common.dto.ResponseComDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/common")
public class CacheController {
    private final CustomRedisCacheManager redisCacheManager;

    @Autowired
    public CacheController(CustomRedisCacheManager redisCacheManager) {
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
        Objects.requireNonNull(redisCacheManager.getCache(name)).evict(key);

        return new ResponseEntity<>(ResponseComDto.builder()
                .resultObj(null)
                .resultMsg("케시 삭제 완료.")
                .build(), HttpStatus.OK);
    }
}
