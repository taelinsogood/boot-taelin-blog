package com.board.board.service;

import org.springframework.http.ResponseEntity;

import com.board.board.dto.request.user.PatchNicknameRequestDto;
import com.board.board.dto.request.user.PatchProfileImageRequestDto;
import com.board.board.dto.response.user.GetSignInUserResponseDto;
import com.board.board.dto.response.user.GetUserResponseDto;
import com.board.board.dto.response.user.PatchNicknameResponseDto;
import com.board.board.dto.response.user.PatchProfileImageResponseDto;

public interface UserService {
	ResponseEntity<? super GetSignInUserResponseDto> getSignInUser(String email);
	ResponseEntity<? super GetUserResponseDto> getUser(String email);
	ResponseEntity<? super PatchNicknameResponseDto> patchNickname(PatchNicknameRequestDto dto, String email);
	ResponseEntity<? super PatchProfileImageResponseDto> patchProfileImage(PatchProfileImageRequestDto dto, String email);
}
