package com.board.board.service.implement;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.board.board.dto.request.board.PatchBoardRequestDto;
import com.board.board.dto.request.board.PostBoardRequestDto;
import com.board.board.dto.request.board.PostCommentRequestDto;
import com.board.board.dto.response.ResponseDto;
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
import com.board.board.entity.BoardEntity;
import com.board.board.entity.BoardListViewEntity;
import com.board.board.entity.CommentEntity;
import com.board.board.entity.FavoriteEntity;
import com.board.board.entity.ImageEntity;
import com.board.board.entity.SearchLogEntity;
import com.board.board.provider.JwtProvider;
import com.board.board.repository.BoardListViewRepository;
import com.board.board.repository.BoardRepository;
import com.board.board.repository.CommentRepository;
import com.board.board.repository.FavoriteRepository;
import com.board.board.repository.ImageRepository;
import com.board.board.repository.SearchLogRepository;
import com.board.board.repository.UserRepository;
import com.board.board.repository.resultSet.GetBoardResultSet;
import com.board.board.repository.resultSet.GetCommentListResultSet;
import com.board.board.repository.resultSet.GetFavoriteListResultSet;
import com.board.board.service.BoardService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardServiceImplement implements BoardService{
	
	private final BoardRepository boardRepository;
	private final ImageRepository imageRepository;
	private final UserRepository userRepository;
	private final FavoriteRepository favoriteRepository;
	private final CommentRepository commentRepository;
	private final BoardListViewRepository boardListViewRepository;
	private final SearchLogRepository searchLogRepository;

	@Override
	public ResponseEntity<? super GetBoardResponseDto> getBoard(Integer boardNumber) {
		
		GetBoardResultSet resultSet = null; // DB에서 가져온 게시글 및 작성자 정보를 담을 인터페이스 객체
		List<ImageEntity> imageEntities = new ArrayList<>(); // 게시글에 첨부된 이미지 정보를 담을 리스트
		
		try {
			
			resultSet = boardRepository.getBoard(boardNumber); // 게시글 정보를 조회
			if(resultSet == null) return GetBoardResponseDto.noExistBoard();
			
			imageEntities = imageRepository.findByBoardNumber(boardNumber); // 게시글 번호로 이미지 테이블에서 이미지 목록을 조회
			
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseDto.databaseError();
		}
		
		return GetBoardResponseDto.success(resultSet, imageEntities);
	}
	
	@Override
	public ResponseEntity<? super PostBoardResponseDto> postBoard(PostBoardRequestDto dto, String email){
		
		try {
			
			boolean existedEmail = userRepository.existsByEmail(email);
			if(!existedEmail) return PostBoardResponseDto.notExistUser();
			
			BoardEntity boardEntity = new BoardEntity(dto, email); // BoardEntity에 dto, email을 넘겨서 BoardEntity 내부에서 변환 처리
			boardRepository.save(boardEntity); // 게시물 만들어주고
			
			// boardImageList
			int boardNumber = boardEntity.getBoardNumber(); // 만든 게시물 번호 추출
			List<String> boardImageList = dto.getBoardImageList(); // dto에서 이미지 URL 또는 파일 이름들의 리스트 가져옴. 예: ["image1.jpg", "image2.png"]
			List<ImageEntity> imageEntities = new ArrayList<>(); // 한꺼번에 저장할 ImageEntity 객체들을 담을 리스트 초기화
			
			// 이미지 리스트를 순회 
			for(String image: boardImageList) {
				ImageEntity imageEntity = new ImageEntity(boardNumber, image); 
				imageEntities.add(imageEntity);
			} // ImageEntity 객체를 생성 후 boardNumber, image를 각 리스트에 넣어 추가함
			
			imageRepository.saveAll(imageEntities); // 리스트를 한 번에 데이터베이스에 저장
			
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseDto.databaseError();
		}
		
		return PostBoardResponseDto.success();
	}

	@Override
	public ResponseEntity<? super PutFavoriteResponseDto> putFavorite(Integer boardNumber, String email) {
		
		try {
			
			boolean existedUser = userRepository.existsByEmail(email);
			if(!existedUser) return PutFavoriteResponseDto.noExistUser();
			
			BoardEntity boardEntity = boardRepository.findByBoardNumber(boardNumber);
			if(boardEntity == null) return PutFavoriteResponseDto.noExistBoard();
			
			FavoriteEntity favoriteEntity = favoriteRepository.findByBoardNumberAndUserEmail(boardNumber, email);
			
			if(favoriteEntity == null) {
				
				favoriteEntity = new FavoriteEntity(email, boardNumber);
				favoriteRepository.save(favoriteEntity);
				boardEntity.increaseFavoriteCount();
				
			} else {
				
				favoriteRepository.delete(favoriteEntity);
				boardEntity.decreaseFavoriteCount();
				
			}
			
			boardRepository.save(boardEntity);
			
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseDto.databaseError();
		}
		
		return PutFavoriteResponseDto.success();
	}

	@Override
	public ResponseEntity<? super GetFavoriteListResponseDto> getFavoriteList(Integer boardNumber) {

		List<GetFavoriteListResultSet> resultSets = new ArrayList<>();
		
		try {
			
			boolean existesBoard = boardRepository.existsByBoardNumber(boardNumber);
			if(!existesBoard) return GetFavoriteListResponseDto.noExistBoard();
			
			resultSets = favoriteRepository.getFavoriteList(boardNumber);
			
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseDto.databaseError();
		}
		
		return GetFavoriteListResponseDto.success(resultSets);
	}

	@Override
	public ResponseEntity<? super PostCommentResponseDto> postComment(PostCommentRequestDto dto, Integer boardNumber, String email) {
		
		try {
			
			BoardEntity boardEntity = boardRepository.findByBoardNumber(boardNumber);
			if(boardEntity == null) return PostCommentResponseDto.noExistBoard();
			
//			boolean existedBoard = boardRepository.existsByBoardNumber(boardNumber);
//			if(!existedBoard) return PostCommentResponseDto.noExistBoard();
			
			boolean existedUser = userRepository.existsByEmail(email);
			if(!existedUser) return PostCommentResponseDto.noExistUser();
			
			CommentEntity commentEntity = new CommentEntity(dto, boardNumber, email);
			commentRepository.save(commentEntity);
			
			boardEntity.increaseCommentCount();
			boardRepository.save(boardEntity);
			
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseDto.databaseError();
		}
		
		return PostCommentResponseDto.success();
	}

	@Override
	public ResponseEntity<? super GetCommentListResponseDto> getCommentList(Integer boardNumber) {
		
		List<GetCommentListResultSet> resultSets = new ArrayList<>();
		
		try {
		
			boolean existedBoard = boardRepository.existsByBoardNumber(boardNumber);
			if(!existedBoard) return GetCommentListResponseDto.noExistBoard();
			
			resultSets = commentRepository.getCommentList(boardNumber);
			
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseDto.databaseError();
		}
		
		return GetCommentListResponseDto.success(resultSets);
	}

	@Override
	public ResponseEntity<? super IncreaseViewCountResponseDto> increaseViewCount(Integer boardNumber) {
		
		try {

			BoardEntity boardEntity = boardRepository.findByBoardNumber(boardNumber);
			if(boardEntity == null) return IncreaseViewCountResponseDto.noExistBoard();
			
			boardEntity.increaseViewCount(); // 조회수 증가
			boardRepository.save(boardEntity);
			
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseDto.databaseError();
		}
		
		return IncreaseViewCountResponseDto.success();
	}

	@Override
	public ResponseEntity<? super DeleteBoardResponseDto> deleteBoard(Integer boardNumber, String email) {
		
		try {
			// 1. 유저 존재 여부 확인
			boolean existedUser = userRepository.existsByEmail(email);
			if(!existedUser) return DeleteBoardResponseDto.noExistUser();
			// 2. 게시글 존재 여부 확인
			BoardEntity boardEntity = boardRepository.findByBoardNumber(boardNumber);
			if(boardEntity == null) return DeleteBoardResponseDto.noExistBoard();
			// 3. 작성자인지 권한 확인
			String writerEmail = boardEntity.getWriterEmail();
			boolean isWriter = writerEmail.equals(email);
			if(!isWriter) return DeleteBoardResponseDto.noPermission();
			
			// 4. 관련 데이터 삭제
			imageRepository.deleteByBoardNumber(boardNumber);
			commentRepository.deleteByBoardNumber(boardNumber);
			favoriteRepository.deleteByBoardNumber(boardNumber);
			// 5. 게시글 삭제
			boardRepository.delete(boardEntity);		
			
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseDto.databaseError();
		}
		
		return DeleteBoardResponseDto.success();
	}

	@Override
	public ResponseEntity<? super PatchBoardResponseDto> patchBoard(PatchBoardRequestDto dto, Integer boardNumber, String email) {
		
		try {
			
			BoardEntity boardEntity = boardRepository.findByBoardNumber(boardNumber); // board number 찾기
			if(boardEntity == null) return PatchBoardResponseDto.noExistBoard();
			
			boolean existesUser = userRepository.existsByEmail(email);
			if(!existesUser) return PatchBoardResponseDto.noExistUser();
			
			String writeEmail = boardEntity.getWriterEmail();
			boolean isWriter = writeEmail.equals(email);
			if(!isWriter) return PatchBoardResponseDto.noPermission();
			
			boardEntity.patchBoard(dto);
			boardRepository.save(boardEntity);
			
			imageRepository.deleteByBoardNumber(boardNumber);
			List<String> boardImageList = dto.getBoardImageList();
			List<ImageEntity> imageEntities = new ArrayList<>();
			
			for(String image: boardImageList) {
				ImageEntity imageEntity = new ImageEntity(boardNumber, image);
				imageEntities.add(imageEntity);
			}
			
			imageRepository.saveAll(imageEntities);
			
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseDto.databaseError();
		}
		
		return PatchBoardResponseDto.success();
	}

	@Override
	public ResponseEntity<? super GetLatestBoardListResponseDto> getLatestBoardList() {
		
		List<BoardListViewEntity> boardListViewEntities = new ArrayList<>(); // entity에 값 저장할 껍데기 만듦 -> 조회한 데이터를 담을 빈 리스트 선언
		
		try {
		
			boardListViewEntities = boardListViewRepository.findByOrderByWriteDatetimeDesc(); // entity에 값 저장 -> Entity 객체 리스트에 실제 DB 데이터 저장
		
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseDto.databaseError();
		}
		
		return GetLatestBoardListResponseDto.success(boardListViewEntities); // 저장한 entity를 dto에 저장
	}

	@Override
	public ResponseEntity<? super GetTop3BoardListResponseDto> getTop3BoardList() {
		
		List<BoardListViewEntity> boardListViewEntities = new ArrayList<>();
		
		try {
			
			Date beforeWeek = Date.from(Instant.now().minus(7, ChronoUnit.DAYS));
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
			String sevenDaysAgo = simpleDateFormat.format(beforeWeek);
			
			boardListViewEntities = boardListViewRepository.findTop3ByWriteDatetimeGreaterThanOrderByFavoriteCountDescCommentCountDescViewCountDescWriteDatetimeDesc(sevenDaysAgo);
		
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseDto.databaseError();
		}
		
		return GetTop3BoardListResponseDto.success(boardListViewEntities);
	}

	@Override
	public ResponseEntity<? super GetSearchBoardListResponseDto> getSearchBoardList(String searchWord, String preSearchWord) {
		
		List<BoardListViewEntity> boardListViewEntities = new ArrayList<>();
		
		try {
			
			boardListViewEntities = boardListViewRepository.findByTitleContainsOrContentContainsOrderByWriteDatetimeDesc(searchWord, preSearchWord);
			
			SearchLogEntity searchLogEntity = new SearchLogEntity(searchWord, preSearchWord, false);
			searchLogRepository.save(searchLogEntity);
			
			boolean relation = preSearchWord != null;
			if(relation) {
				searchLogEntity = new SearchLogEntity(preSearchWord, searchWord, relation);
				searchLogRepository.save(searchLogEntity);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseDto.databaseError();
		}
		
		return GetSearchBoardListResponseDto.success(boardListViewEntities);
	}

	@Override
	public ResponseEntity<? super GetUserBoardListResponseDto> getUserBoardList(String email) {
		
		List<BoardListViewEntity> boardListViewEntities = new ArrayList<>();
		
		try {
			
			boolean existedUser = userRepository.existsByEmail(email);
			if(!existedUser) return GetUserBoardListResponseDto.noExistUser();
			
			boardListViewEntities = boardListViewRepository.findByWriterEmailOrderByWriteDatetimeDesc(email);
			
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseDto.databaseError();
		}
		
		return GetUserBoardListResponseDto.success(boardListViewEntities);
	}

}


// putFavorite
// 1. 유저가 존재하는지 확인 (boolean)
// 2. 게시글이 존재하는지 확인 (Entity)
// 3. 해당 유저가 게시글에 좋아요를 눌렀는지 확인
// 4. 좋아요 없으면 추가, 있으면 삭제
// 5. 예외 처리
// 6. 성공 응답 반환

