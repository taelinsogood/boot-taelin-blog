package com.board.board.dto.response.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.board.board.common.ResponseCode;
import com.board.board.common.ResponseMessage;
import com.board.board.dto.response.ResponseDto;

import lombok.Getter;

@Getter
public class SignInResponseDto extends ResponseDto{

	private String token;
	private int expirationTime;
	
	private SignInResponseDto(String token) {
		super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
		this.token = token;  //전달받은 JWT 토큰 저장
		this.expirationTime = 3600;  //만료 시간은 3600초로 고정
	}
	
	public static ResponseEntity<SignInResponseDto> success(String token) {
		SignInResponseDto result = new SignInResponseDto(token);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	
	public static ResponseEntity<ResponseDto> signInFail() {
		ResponseDto result = new ResponseDto(ResponseCode.SIGN_IN_FAIL, ResponseMessage.SIGN_IN_FAIL);
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
	}
}



