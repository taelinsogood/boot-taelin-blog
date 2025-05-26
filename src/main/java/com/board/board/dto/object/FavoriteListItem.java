package com.board.board.dto.object;

import java.util.ArrayList;
import java.util.List;

import com.board.board.repository.resultSet.GetFavoriteListResultSet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteListItem {
	private String email;
	private String nickname;
	private String profileImage;
	
	// db에서 가져온 값을 dto로 가공
	public FavoriteListItem (GetFavoriteListResultSet resultSet){
		this.email = resultSet.getEmail();
		this.nickname = resultSet.getNickname();
		this.profileImage = resultSet.getProfileImage();
	}

	// GetFavoriteListResultSet → FavoriteListItem 객체로 변환하는 도우미(헬퍼) 메서드
	public static List<FavoriteListItem> copyList(List<GetFavoriteListResultSet> resultSets){
		List<FavoriteListItem> list = new ArrayList<>();
		for(GetFavoriteListResultSet resultSet: resultSets) {
			FavoriteListItem favoriteListItem = new FavoriteListItem(resultSet);
			list.add(favoriteListItem);
		}
		return list;
	}
}
