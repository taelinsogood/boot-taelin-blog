package com.board.board.service;

import org.springframework.http.ResponseEntity;

import com.board.board.dto.request.auth.SignInRequestDto;
import com.board.board.dto.request.auth.SignUpRequestDto;
import com.board.board.dto.response.auth.SignInResponseDto;
import com.board.board.dto.response.auth.SignUpResponseDto;

public interface AuthService {
	ResponseEntity<? super SignUpResponseDto> signUp(SignUpRequestDto dto);
	ResponseEntity<? super SignInResponseDto> signIn(SignInRequestDto dto);
}
