package com.board.board.service;

import org.springframework.http.ResponseEntity;

import com.board.board.dto.response.search.GetPopularListResponseDto;
import com.board.board.dto.response.search.GetRelationListResponseDto;

public interface SearchService {

	ResponseEntity<? super GetPopularListResponseDto> getPopularList();
	ResponseEntity<? super GetRelationListResponseDto> getRelationList(String searchWord);
	
}
