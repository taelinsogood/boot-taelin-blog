package com.board.board.dto.response.board;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.board.board.common.ResponseCode;
import com.board.board.common.ResponseMessage;
import com.board.board.dto.response.ResponseDto;

import lombok.Getter;

@Getter
public class DeleteBoardResponseDto extends ResponseDto{
	
	private DeleteBoardResponseDto() {
		super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
	}
	
	public static ResponseEntity<DeleteBoardResponseDto> success(){
		DeleteBoardResponseDto result = new DeleteBoardResponseDto();
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	
	public static ResponseEntity<ResponseDto> noExistBoard(){
		ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_BOARD, ResponseMessage.NOT_EXISTED_BOARD);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
	}
	
	public static ResponseEntity<ResponseDto> noExistUser(){
		ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_USER, ResponseMessage.NOT_EXISTED_USER);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
	}
	
	public static ResponseEntity<ResponseDto> noPermission(){
		ResponseDto result = new ResponseDto(ResponseCode.N0_PERMISSION, ResponseMessage.N0_PERMISSION);
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(result);
	}
}
