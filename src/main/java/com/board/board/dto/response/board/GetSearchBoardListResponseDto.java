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
public class GetSearchBoardListResponseDto extends ResponseDto{

	private List<BoardListItem> searchList;
	
	private GetSearchBoardListResponseDto(List<BoardListViewEntity> boardListViewEntities) { // BoardListItem와 BoardListViewEntity는 짝꿍
		super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
		this.searchList = BoardListItem.getList(boardListViewEntities);
	}
	
	public static ResponseEntity<GetSearchBoardListResponseDto> success(List<BoardListViewEntity> boardListViewEntities){
		GetSearchBoardListResponseDto result = new GetSearchBoardListResponseDto(boardListViewEntities);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
}
