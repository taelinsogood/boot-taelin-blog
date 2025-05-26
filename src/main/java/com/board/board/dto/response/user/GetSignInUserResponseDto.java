package com.board.board.dto.response.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.board.board.common.ResponseCode;
import com.board.board.common.ResponseMessage;
import com.board.board.dto.response.ResponseDto;
import com.board.board.entity.UserEntity;

import lombok.Getter;

@Getter
public class GetSignInUserResponseDto extends ResponseDto{

	private String email;
	private String nickname;
	private String profileImage;
	
	private GetSignInUserResponseDto(UserEntity userEntity) {
		super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
		this.email = userEntity.getEmail();
		this.nickname = userEntity.getNickname();
		this.profileImage = userEntity.getProfileImage();
	}
	
	public static ResponseEntity<GetSignInUserResponseDto> success(UserEntity userEntity){
		GetSignInUserResponseDto result = new GetSignInUserResponseDto(userEntity);  // Entity를 Dto로 변경
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	
	public static ResponseEntity<ResponseDto> notExistUser(){
		ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_USER, ResponseMessage.NOT_EXISTED_USER);
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
	}
}
