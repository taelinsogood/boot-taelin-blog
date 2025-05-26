package com.board.board.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.board.board.dto.request.board.PatchBoardRequestDto;
import com.board.board.dto.request.board.PostBoardRequestDto;
import com.board.board.dto.request.board.PostCommentRequestDto;
import com.board.board.dto.response.board.DeleteBoardResponseDto;
import com.board.board.dto.response.board.GetBoardResponseDto;
import com.board.board.dto.response.board.GetCommentListResponseDto;
import com.board.board.dto.response.board.GetFavoriteListResponseDto;
import com.board.board.dto.response.board.GetLatestBoardListResponseDto;
import com.board.board.dto.response.board.GetSearchBoardListResponseDto;
import com.board.board.dto.response.board.GetTop3BoardListResponseDto;
import com.board.board.dto.response.board.GetUserBoardListResponseDto;
import com.board.board.dto.response.board.IncreaseViewCountResponseDto;
import com.board.board.dto.response.board.PatchBoardResponseDto;
import com.board.board.dto.response.board.PostBoardResponseDto;
import com.board.board.dto.response.board.PostCommentResponseDto;
import com.board.board.dto.response.board.PutFavoriteResponseDto;
import com.board.board.service.AuthService;
import com.board.board.service.BoardService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/board")
@RequiredArgsConstructor
public class BoardController {

	private final BoardService boardService;
	
	@GetMapping("/{boardNumber}")
	public ResponseEntity<? super GetBoardResponseDto> getBoard(
			@PathVariable("boardNumber") Integer boardNumber
	){
		ResponseEntity<? super GetBoardResponseDto> response = boardService.getBoard(boardNumber);
		return response;
	}
	
	@PostMapping("")
	public ResponseEntity<? super PostBoardResponseDto> postBoard(
			@RequestBody @Valid PostBoardRequestDto requestBody,
			@AuthenticationPrincipal String email
	){
		ResponseEntity<? super PostBoardResponseDto> response = boardService.postBoard(requestBody, email);
		return response;
	}
	
	@PutMapping("/{boardNumber}/favorite")
	public ResponseEntity<? super PutFavoriteResponseDto> putFavorite(
			@PathVariable("boardNumber") Integer boardNumber,
			@AuthenticationPrincipal String email
	){
		ResponseEntity<? super PutFavoriteResponseDto> response = boardService.putFavorite(boardNumber, email);
		return response;
	}
	
	@GetMapping("/{boardNumber}/favorite-list")
	public ResponseEntity<? super GetFavoriteListResponseDto> getFavoriteList(
			@PathVariable("boardNumber") Integer boardNumber
	){
		ResponseEntity<? super GetFavoriteListResponseDto> response = boardService.getFavoriteList(boardNumber);
		return response;
	}
	
	@PostMapping("/{boardNumber}/comment")
	public ResponseEntity<? super PostCommentResponseDto> postComment(
			@RequestBody @Valid PostCommentRequestDto requestBody,
			@PathVariable("boardNumber") Integer boardNumber,
			@AuthenticationPrincipal String email
	){
		ResponseEntity<? super PostCommentResponseDto> response = boardService.postComment(requestBody, boardNumber, email);
		return response;
	}
	
	@GetMapping("/{boardNumber}/comment-list")
	public ResponseEntity<? super GetCommentListResponseDto> getComment(
			@PathVariable("boardNumber") Integer boardNumber
	){
		ResponseEntity<? super GetCommentListResponseDto> response = boardService.getCommentList(boardNumber);
		return response;
	}
	
	@GetMapping("/{boardNumber}/increase-view-count")
	public ResponseEntity<? super IncreaseViewCountResponseDto> increaseViewCount(
			@PathVariable("boardNumber") Integer boardNumber
	){
		ResponseEntity<? super IncreaseViewCountResponseDto> response = boardService.increaseViewCount(boardNumber);
		return response;
		
	}
	
	@DeleteMapping("/{boardNumber}")
	public ResponseEntity<? super DeleteBoardResponseDto> deleteBoard(
			@PathVariable("boardNumber") Integer boardNumber,
			@AuthenticationPrincipal String email
	){
		ResponseEntity<? super DeleteBoardResponseDto> response = boardService.deleteBoard(boardNumber, email);
		return response;
	}
	
	@PatchMapping("/{boardNumber}")
	public ResponseEntity<? super PatchBoardResponseDto> patchBoard(
			@RequestBody @Valid PatchBoardRequestDto requestBody,
			@PathVariable("boardNumber") Integer boardNumber,
			@AuthenticationPrincipal String email
	){
		ResponseEntity<? super PatchBoardResponseDto> response = boardService.patchBoard(requestBody, boardNumber, email);
		return response;
	}
	
	@GetMapping("/latest-list")
	public ResponseEntity<? super GetLatestBoardListResponseDto> getLatestBoardList(){
		ResponseEntity<? super GetLatestBoardListResponseDto> response = boardService.getLatestBoardList();
		return response;
	}
	
	@GetMapping("/top-3")
	public ResponseEntity<? super GetTop3BoardListResponseDto> getTop3BoardList(){
		ResponseEntity<? super GetTop3BoardListResponseDto> response = boardService.getTop3BoardList();
		return response;
	}
	
	@GetMapping(value = {"/search-list/{searchWord}", "/search-list/{searchWord}/{preSearchWord}"})
	public ResponseEntity<? super GetSearchBoardListResponseDto> getSearchBoardList(
			@PathVariable("searchWord") String searchWord,
			@PathVariable(value = "preSearchWord", required = false) String preSearchWord
		){
		ResponseEntity<? super GetSearchBoardListResponseDto> response = boardService.getSearchBoardList(searchWord, preSearchWord);
		return response;
	}
	
	@GetMapping("/user-board-list/{email}")
	public ResponseEntity<? super GetUserBoardListResponseDto> getUserBoardList(
			@PathVariable("email") String email
	){
		ResponseEntity<? super GetUserBoardListResponseDto> result = boardService.getUserBoardList(email);
		return result;
	}
}
