package com.board.board.provider;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.rsocket.RSocketSecurity.JwtSpec;
import org.springframework.stereotype.Component;

@Component
public class JwtProvider {

	@Value("${secret-key}")
	private String secretKey;
	
	public String create(String email) { // JWT 생성(create) – 사용자의 이메일 같은 정보를 담아 토큰을 만듦
		// SecretKeySpec을 사용하여 비밀 키로부터 SecretKey 객체를 생성
//        SecretKey key = new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS256.getJcaName());

        Date expiredDate = Date.from(Instant.now().plus(1, ChronoUnit.HOURS));
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        
        String jwt = Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS256)  // SecretKey 객체를 사용하여 서명
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(expiredDate)
                .compact();
        
        return jwt;
	}
	
	public String validate(String jwt) { // JWT 검증(validate) – 요청으로 받은 토큰이 유효한지 확인해주며, 유효하면 **이메일(사용자 정보)**을 꺼내줍니다.
		Claims claims = null;
		Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
		
		try {
	        claims = Jwts.parserBuilder() // parser() 대신 parserBuilder() 사용
	                .setSigningKey(key) // setSigningKey()에 byte[]를 전달
	                .build()
	                .parseClaimsJws(jwt)  // JWT 파싱
	                .getBody();  // Claims 추출
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }
	    return claims.getSubject(); // 이메일을 추출
	}
}

// JWT 생성과 검증을 담당하는 핵심 클래스
// 로그인하거나 회원가입할 때 발급할 JWT 토큰을 생성하는 코드
// 클라이언트가 인증을 성공했을 때, 해당 사용자의 이메일 주소를 기반으로 **JWT(Json Web Token)**을 만들어서 반환