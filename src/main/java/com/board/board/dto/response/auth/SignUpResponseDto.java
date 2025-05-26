package com.board.board.dto.response.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.board.board.common.ResponseCode;
import com.board.board.common.ResponseMessage;
import com.board.board.dto.response.ResponseDto;

import lombok.Getter;

@Getter
public class SignUpResponseDto extends ResponseDto{
	
	public SignUpResponseDto() {
		super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
	}
	
	public static ResponseEntity<ResponseDto> success() {
		SignUpResponseDto result = new SignUpResponseDto();
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	
	public static ResponseEntity<ResponseDto> duplicateEmail() {
		ResponseDto result = new ResponseDto(ResponseCode.DUPLICATE_EMAIL, ResponseMessage.DUPLICATE_EMAIL);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
	}
	
	public static ResponseEntity<ResponseDto> duplicateNickname() {
		ResponseDto result = new ResponseDto(ResponseCode.DUPLICATE_NICKNAME, ResponseMessage.DUPLICATE_NICKNAME);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
	}
	
	public static ResponseEntity<ResponseDto> duplicateTelNumber() {
		ResponseDto result = new ResponseDto(ResponseCode.DUPLICATE_TEL_NUMBER, ResponseMessage.DUPLICATE_TEL_NUMBER);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
	}
}
