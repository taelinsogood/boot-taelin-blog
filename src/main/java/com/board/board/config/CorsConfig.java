package com.board.board.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer{
	@Override
	public void addCorsMappings(CorsRegistry corsRegistry) {
		corsRegistry
			.addMapping("/**") // 모든 URL 경로에 대해
			.allowedOrigins("http://localhost:3000") // 해당 프론트 주소 요청 허용
			.allowedMethods("*") // 모든 HTTP 메서드 허용 (GET, POST, PUT, DELETE 등)
			.allowedHeaders("*")              // ✅ Authorization 헤더 허용
			.allowCredentials(true);         // ✅ 인증정보(Cookie, Header) 허용
	}
}
