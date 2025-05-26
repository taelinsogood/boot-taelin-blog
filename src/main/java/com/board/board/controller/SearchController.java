package com.board.board.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.board.board.dto.response.search.GetPopularListResponseDto;
import com.board.board.dto.response.search.GetRelationListResponseDto;
import com.board.board.service.AuthService;
import com.board.board.service.SearchService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/search")
@RequiredArgsConstructor
public class SearchController {

	private final SearchService searchService;
	
	@GetMapping("/popular-list")
	public ResponseEntity<? super GetPopularListResponseDto> getPopularList(){
		ResponseEntity<? super GetPopularListResponseDto> response = searchService.getPopularList();
		return response;
	}
	
	@GetMapping("/{searchWord}/relation-list")
	public ResponseEntity<? super GetRelationListResponseDto> getRelationList(
			@PathVariable("searchWord") String searchWord
	){
		ResponseEntity<? super GetRelationListResponseDto> response = searchService.getRelationList(searchWord);
		return response;
	}
}
