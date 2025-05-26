package com.board.board.repository.resultSet;

public interface GetBoardResultSet { // 쿼리 결과를 담는 인터페이스
	Integer getBoardNumber();
	String getTitle();
	String getContent();
	String getWriteDatetime();
	String getWriterEmail();
	String getWriterNickname();
	String getWriterProfileImage();
}
