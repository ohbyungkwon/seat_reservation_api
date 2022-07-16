//package com.seat.reservation.common.security;
//
//import com.seat.reservation.common.domain.User;
//import com.seat.reservation.common.domain.enums.Role;
//import io.jsonwebtoken.*;
//import lombok.AccessLevel;
//import lombok.NoArgsConstructor;
//import lombok.extern.log4j.Log4j2;
//
//import javax.crypto.spec.SecretKeySpec;
//import javax.persistence.Access;
//import javax.xml.bind.DatatypeConverter;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//import java.security.Key;
//
//@Log4j2
//@NoArgsConstructor(access= AccessLevel.PRIVATE)
//public final class TokenUtils {
//    private static final String secretKey = "Example";
//
//    public static String generateJwtToken(User user){
//        JwtBuilder builder = Jwts.builder()
//                .setSubject(user.getEmail())
//                .setHeader(createHeader())
//                .setClaims(createClaims(user))
//                .setExpiration(createExpireDateForOneYear())
//                .signWith(SignatureAlgorithm.HS256, createSigningKey());
//
//        return builder.compact();
//    }
//
//    public static boolean isValidToken(String token){
//        try{
//            Claims claims = getClaimsFormToken(token);
//            log.info("expire time: "+claims.getExpiration());
//            log.info("email : "+claims.get("email"));
//            log.info("role : "+claims.get("role"));
//            return true;
//        } catch (ExpiredJwtException exception){
//            log.error("Token Expried");
//            return false;
//        } catch (JwtException exception){
//            log.error("Token Tampered");
//            return false;
//        } catch(NullPointerException exception){
//            log.error("Token is Null");
//            return false;
//        }
//    }
//
//    public static String getTokenFormHeader(String header){
//        return header.split(" ")[1];
//    }
//
//    private static Date createExpireDateForOneYear(){
//        Calendar c = Calendar.getInstance();
//        c.add(Calendar.DATE, 30);
//        return c.getTime();
//    }
//
//    private static Map<String,Object> createHeader(){
//        Map<String, Object> header = new HashMap<>();
//
//        header.put("typ", "JWT");
//        header.put("alg", "HS256");
//        header.put("regDate", System.currentTimeMillis());
//
//        return header;
//    }
//
//    private static Map<String,Object> createClaims(User user){
//        Map<String,Object> claims = new HashMap<>();
//
//        claims.put("email", user.getEmail());
//        claims.put("role", user.getRole());
//
//        return claims;
//    }
//
//    private static Key createSigningKey(){
//        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secretKey);
//        return new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());
//    }
//
//    private static Claims getClaimsFormToken(String token){
//        return Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
//                .parseClaimsJws(token).getBody();
//    }
//
//    private static String getUserEmailFormToken(String token){
//        Claims claims = getClaimsFormToken(token);
//        return (String)claims.get("email");
//    }
//
//    private static Role getRoleFormToken(String token){
//        Claims claims = getClaimsFormToken(token);
//        return (Role)claims.get("role");
//    }
//
//}
