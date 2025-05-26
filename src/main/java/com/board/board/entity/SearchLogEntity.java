package com.board.board.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="search_log")
@Table(name="search_log")
public class SearchLogEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int sequence;
	private String searchWord;
	private String relationWord;
	private boolean relation; // 검색어들 사이에 연관성이 있는지 확인
	
	public SearchLogEntity(String searchWord, String relationWord, boolean relation) {
		this.searchWord = searchWord;
		this.relationWord = relationWord;
		this.relation = relation; 
	}
}
