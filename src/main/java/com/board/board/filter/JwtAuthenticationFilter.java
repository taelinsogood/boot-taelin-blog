package com.board.board.filter;

import java.io.IOException;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.board.board.provider.JwtProvider;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	private final JwtProvider jwtProvider;

	@Override     // doFilterInternal -> 인증 로직을 구현
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		
		try {			
			String token = parseBearerToken(request); // JWT 토큰 값을 추출하는 사용자 정의 함수
			if(token == null) {
				filterChain.doFilter(request, response); // 토큰이 없으면 다음 필터(혹은 컨트롤러)로 넘김
				return; // 인증 처리는 생략
			}
			
			String email = jwtProvider.validate(token); // 유효시 토큰 안에 있는 email 값을 반환
			if(email == null) {
				filterChain.doFilter(request, response); // 이메일이 없으면 인증 안 된 상태로 다음 필터로
				return;
			}
			
			// *인증 토큰 생성
			// AbstractAuthenticationToken -> Spring Security에서 제공하는 인증 객체
			AbstractAuthenticationToken authenticationToken = 
					new UsernamePasswordAuthenticationToken(email, null, AuthorityUtils.NO_AUTHORITIES); // 사용자 ID (여기선 email), 비밀번호 (여기선 필요 없어 null), 권한 목록 (현재 NO_AUTHORITIES → 권한 없음)
			// WebAuthenticationDetailsSource -> 요청(request)의 부가 정보를 담는 객체입니다. (IP 주소, 세션 ID 등)
			authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			
			// *인증 객체를 SecurityContext에 저장
			SecurityContext securityContext = SecurityContextHolder.createEmptyContext(); // 비어 있는 SecurityContext 생성
			securityContext.setAuthentication(authenticationToken); // 인증 정보 넣기
			SecurityContextHolder.setContext(securityContext); // 이 Context를 SecurityContextHolder에 등록
			// 인증된 정보를 전역 컨텍스트(SecurityContextHolder) 에 저장
			// 이후 컨트롤러, 서비스 등 어디서든 로그인한 사용자 정보를 꺼내 쓸 수 있음.
		} catch (Exception e) {
			e.printStackTrace();
		}
		// *필터 체인 계속 진행
		filterChain.doFilter(request, response); // 모든 인증 처리 끝 -> 다음 필터 또는 컨트롤러로 요청을 넘김
	}
	
	// 목적: HTTP 요청에서 Authorization 헤더를 읽고, Bearer 토큰이 포함되어 있다면 그 토큰 값을 추출하여 반환.
	private String parseBearerToken(HttpServletRequest request) {
		
		// Authorization 헤더 가져오기
		String authorization = request.getHeader("Authorization");
		
		// Authorization 값이 비어있는지 확인
		// StringUtils.hasText(authorization) -> 문자열이 null이 아니고, 길이가 0보다 크며, 공백만으로 이루어지지 않았는지를 검사
		boolean hasAuthorization = StringUtils.hasText(authorization);
		if(!hasAuthorization) return null;
		
		// "Bearer "로 시작하는지 확인
		boolean isBearer = authorization.startsWith("Bearer ");
		if(!isBearer) return null;
		
		// 토큰 추출
		String token = authorization.substring(7);
		return token;
	}
}


//🔐 전체 순서 요약
//요청에서 JWT 토큰 꺼냄
//토큰이 없으면 다음으로 넘김
//토큰이 있으면 검증
//유효하면 인증 객체 생성 → SecurityContext에 저장
//다음 필터 또는 컨트롤러로 요청 처리
