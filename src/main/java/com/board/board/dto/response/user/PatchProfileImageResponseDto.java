package com.board.board.dto.response.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.board.board.common.ResponseCode;
import com.board.board.common.ResponseMessage;
import com.board.board.dto.response.ResponseDto;

import lombok.Getter;

@Getter
public class PatchProfileImageResponseDto extends ResponseDto{

	private PatchProfileImageResponseDto() {
		super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
	}
	
	public static ResponseEntity<PatchProfileImageResponseDto> success(){
		PatchProfileImageResponseDto result = new PatchProfileImageResponseDto();
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	
	public static ResponseEntity<ResponseDto> noExistUser(){
		ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_USER, ResponseMessage.NOT_EXISTED_USER);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
	}
}
