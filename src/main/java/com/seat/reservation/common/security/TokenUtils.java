package com.seat.reservation.common.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seat.reservation.common.dto.UserDto;
import com.seat.reservation.common.util.CommonUtil;
import io.jsonwebtoken.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

/**
 * This class is util file about JWT.
 */
@Slf4j
@NoArgsConstructor(access= AccessLevel.PRIVATE)
public final class TokenUtils {
    private static final String secretKey = "test-reservation-token-key";

    public static String generateJwtToken(Object target) throws JsonProcessingException {
        LocalDateTime now = CommonUtil.getNowDateTime();
        LocalDateTime expiredDate;
        if(target instanceof UserDto.create) {
            expiredDate = now.plusHours(1);
        } else {
            expiredDate = now.plusWeeks(1);
            String randomValue = UUID.randomUUID().toString().substring(0, 10);
            target += (AuthConstants.SEPARATOR + randomValue +
                    AuthConstants.SEPARATOR + System.currentTimeMillis());
        }

        ObjectMapper objectMapper = new ObjectMapper();
        String subject = objectMapper.writeValueAsString(target);
        log.debug("Subject: {}", subject);

        return Jwts.builder()
                .setHeader(createHeader())
                .setSubject(subject)
                .setIssuedAt(CommonUtil.convertDate(now))
                .setExpiration(CommonUtil.convertDate(expiredDate))
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes())
                .compact();
    }

    public static String removeTokenType(String token) {
        if(token.contains(AuthConstants.ACCESS_TOKEN_TYPE)){
            return token.substring(AuthConstants.ACCESS_TOKEN_TYPE.length() + 1);
        }
        return token;
    }

    public static UserDto.create getUser(String token) throws IOException {
        token = removeTokenType(token);
        String claims = getClaimsFormToken(token);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(claims, UserDto.create.class);
    }

    public static boolean isValidToken(String token, String tokenType){
        token = removeTokenType(token);
        try{
            String claims = getClaimsFormToken(token);
            ObjectMapper objectMapper = new ObjectMapper();

            if(AuthConstants.ACCESS_TOKEN_TYPE.equals(tokenType)){
                UserDto.create user = objectMapper.readValue(claims, UserDto.create.class);
                log.debug("User: {}", user);
            } else {
                String content = objectMapper.readValue(claims, String.class);
                log.debug("Content: {}", content);
            }

            return true;
        } catch (ExpiredJwtException exception){
            log.error("Token Expired");
            return false;
        } catch (JwtException exception){
            log.error("Token Tampered");
            return false;
        } catch(NullPointerException exception){
            log.error("Token is Null");
            return false;
        } catch (IOException e) {
            log.error("I/O Exception(ObjectMapper)");
            return false;
        }
    }

    private static Map<String,Object> createHeader(){
        Map<String, Object> header = new HashMap<>();

        header.put("typ", "JWT");
        header.put("alg", "HS256");
        header.put("regDate", CommonUtil.getNowDateTime());

        return header;
    }

//    private static Key createSigningKey(){
//        return new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS256.getJcaName());
//    }

    private static String getClaimsFormToken(String token){
        return Jwts.parser().setSigningKey(secretKey.getBytes())
                .parseClaimsJws(token).getBody().getSubject();
    }
}
