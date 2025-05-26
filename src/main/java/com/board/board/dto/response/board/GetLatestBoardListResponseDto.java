package com.board.board.dto.response.board;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.board.board.common.ResponseCode;
import com.board.board.common.ResponseMessage;
import com.board.board.dto.object.BoardListItem;
import com.board.board.dto.response.ResponseDto;
import com.board.board.entity.BoardListViewEntity;

import lombok.Getter;

@Getter
public class GetLatestBoardListResponseDto extends ResponseDto{
	
	private List<BoardListItem> latestList;
	
	private GetLatestBoardListResponseDto(List<BoardListViewEntity> boardEntities) {
		// 2.
		super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
		// 3. entity 리스트를 DTO 리스트로 변환해서 필드에 저장
		this.latestList = BoardListItem.getList(boardEntities);
	}
	
	// 실행 시작
	public static ResponseEntity<GetLatestBoardListResponseDto> success(List<BoardListViewEntity> boardEntities){
		// 1. 생성자 호출
		GetLatestBoardListResponseDto result = new GetLatestBoardListResponseDto(boardEntities);
		// 4. 결과 - 생성된 객체(result)를 응답(ResponseEntity)에 담아서 반환
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
}
