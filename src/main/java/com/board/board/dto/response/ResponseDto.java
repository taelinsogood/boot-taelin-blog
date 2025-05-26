package com.board.board.dto.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.board.board.common.ResponseCode;
import com.board.board.common.ResponseMessage;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseDto { 
	private String code;
	private String message;
	
	public static ResponseEntity<ResponseDto> databaseError() {
		ResponseDto responseBody = new ResponseDto(ResponseCode.DATEBASE_ERROR, ResponseMessage.DATEBASE_ERROR);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
	}
	
	public static ResponseEntity<ResponseDto> validationFailed(){
		ResponseDto responseBody = new ResponseDto(ResponseCode.VALIDATION_FAILED, ResponseMessage.VALIDATION_FAILED);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
	}
}


// ResponseDto는 백엔드 서버가 프론트엔드로 API 응답을 보낼 때 사용하는 데이터 객체
// ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)는 HTTP 500 상태 코드를 설정