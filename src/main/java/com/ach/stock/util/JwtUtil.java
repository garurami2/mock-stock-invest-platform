package com.ach.stock.util;

import com.ach.stock.dto.Users;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
@Slf4j
public class JwtUtil {

    private final Key key;

    private final long expirationTime;

    public JwtUtil(@Value("${jwt.secret}") String secretKey, @Value("${jwt.expirationTime}") String expirationTime) {
        log.info("secretKey {}", secretKey);
        log.info("expirationTime {}", expirationTime);
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
        this.expirationTime = Long.parseLong(expirationTime);
    }

    // JWT(Json Web Token)을 생성하는 코드
    public String generateToken(Users users) {
        return Jwts.builder()
                .setSubject(users.getUserId())
                .claim("role", users.getRole().name())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // 토큰에서 사용자 ID 추출
    public String extractUserId(String token){
        return getClaims(token).getSubject();
    }

    // 토큰에서 role 정보 추출
    public String extractUserRole(String token) {
        return (String) getClaims(token).get("role");
    }

    // 토큰 유효성 검사
    public boolean validateToken(String token) {
        try {
            getClaims(token); // 파싱 성공하면 유효함
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // Claims 파싱
    private Claims getClaims(String token) {
        return Jwts.parserBuilder() // JWT 파서를 생성하기 위해 객체 선언
                .setSigningKey(key) // 비밀키(application.properties에 세팅한 키) 세팅
                .build() // 설정이 완료된 JWT 파서를 생성 parseClaimsJws() 같은 메서드를 호출할 수 있는 완성된 파서 객체가 됨
                // 전달 받은 JWT 문자열(token) 값을 파싱, 정상적일 경우 Jws<Claims> 리턴, Jws는 JWT의 3부분(Header, Payload, Signature)을 모두 담고 있음
                .parseClaimsJws(token)
                .getBody(); // .getBody()를 호출하면 Payload = Claims, 즉, 이메일(subject), role 등의 데이터를 담고 있는 Map 객체가 리턴됨
    }

}
