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
public class GetTop3BoardListResponseDto extends ResponseDto{

	private List<BoardListItem> top3List; // 값 넣을 dto 선언
	
	private GetTop3BoardListResponseDto(List<BoardListViewEntity> boardListViewEntities) { // entity를 dto로 변환
		super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
		this.top3List = BoardListItem.getList(boardListViewEntities); // entity를 list dto에 값 넣기
	}
	
	public static ResponseEntity<GetTop3BoardListResponseDto> success(List<BoardListViewEntity> boardListViewEntities){
		GetTop3BoardListResponseDto result = new GetTop3BoardListResponseDto(boardListViewEntities);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	
	
}
