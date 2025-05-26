package com.board.board.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.board.board.dto.request.user.PatchNicknameRequestDto;
import com.board.board.dto.request.user.PatchProfileImageRequestDto;
import com.board.board.dto.response.user.GetSignInUserResponseDto;
import com.board.board.dto.response.user.GetUserResponseDto;
import com.board.board.dto.response.user.PatchNicknameResponseDto;
import com.board.board.dto.response.user.PatchProfileImageResponseDto;
import com.board.board.service.AuthService;
import com.board.board.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
	
	private final UserService userService;

	@GetMapping("")
	public ResponseEntity<? super GetSignInUserResponseDto> getSignInUser(
			@AuthenticationPrincipal String email
	){
		ResponseEntity<? super GetSignInUserResponseDto> response = userService.getSignInUser(email);
		return response;
	}
	
	@GetMapping("/{email}")
	public ResponseEntity<? super GetUserResponseDto> getUser(
			@PathVariable("email") String email
	){
		ResponseEntity<? super GetUserResponseDto> response = userService.getUser(email);
		return response;
	}
	
	@PatchMapping("/nickname")
	public ResponseEntity<? super PatchNicknameResponseDto> patchNickname(
			@RequestBody @Valid PatchNicknameRequestDto requestBody,
			@AuthenticationPrincipal String email
	){
		ResponseEntity<? super PatchNicknameResponseDto> response = userService.patchNickname(requestBody, email);
		return response;
	}
	
	@PatchMapping("/profile-image")
	public ResponseEntity<? super PatchProfileImageResponseDto> patchNickname(
			@RequestBody @Valid PatchProfileImageRequestDto requestBody,
			@AuthenticationPrincipal String email
	){
		ResponseEntity<? super PatchProfileImageResponseDto> response = userService.patchProfileImage(requestBody, email);
		return response;
	}
}


//🤣ResponseEntity
//ResponseEntity<T>는 HTTP 응답의 상태코드, 헤더, 바디를 포함하는 전체 응답 객체입니다.
//T에는 주로 DTO가 들어가고, 이 값이 실제 프론트에 JSON으로 전달되는 내용입니다.
