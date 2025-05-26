package com.board.board.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.board.board.dto.response.board.GetFavoriteListResponseDto;
import com.board.board.entity.FavoriteEntity;
import com.board.board.entity.primaryKey.FavoritePk;
import com.board.board.repository.resultSet.GetFavoriteListResultSet;

import jakarta.transaction.Transactional;

@Repository
public interface FavoriteRepository extends JpaRepository<FavoriteEntity, FavoritePk>{

	FavoriteEntity findByBoardNumberAndUserEmail(Integer boardNumber, String userEmail);

	// @Query 구문은 특정 게시글 번호(boardNumber)에 좋아요(즐겨찾기) 한 **사용자들 정보(이메일, 닉네임, 프로필 이미지)**를 조회하는 쿼리
	@Query(
		value = 
		"SELECT " + 
		"U.email AS email, " +
		"U.nickname AS nickname, " + 
		"U.profile_image AS profileImage " + 
		"FROM favorite AS F  " + 
		"INNER JOIN user AS U " + 
		"ON F.user_email = U.email " + 
		"WHERE F.board_number = ?1 ",
		nativeQuery = true
	)
	List<GetFavoriteListResultSet> getFavoriteList(Integer boardNumber);
	
	@Transactional
	void deleteByBoardNumber(Integer boardNumber);
}
