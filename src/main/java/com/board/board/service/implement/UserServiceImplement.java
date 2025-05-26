package com.board.board.service.implement;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.board.board.dto.request.user.PatchNicknameRequestDto;
import com.board.board.dto.request.user.PatchProfileImageRequestDto;
import com.board.board.dto.response.ResponseDto;
import com.board.board.dto.response.user.GetSignInUserResponseDto;
import com.board.board.dto.response.user.GetUserResponseDto;
import com.board.board.dto.response.user.PatchNicknameResponseDto;
import com.board.board.dto.response.user.PatchProfileImageResponseDto;
import com.board.board.entity.UserEntity;
import com.board.board.provider.JwtProvider;
import com.board.board.repository.UserRepository;
import com.board.board.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImplement implements UserService{
	
	private final UserRepository userRepository;
	
	@Override                                               // 로그인
	public ResponseEntity<? super GetSignInUserResponseDto> getSignInUser(String email) {
		
		UserEntity userEntity = null; // 초기값
		
		try {
			userEntity = userRepository.findByEmail(email);
			if(userEntity == null) return GetSignInUserResponseDto.notExistUser();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseDto.databaseError();
		}
		
		return GetSignInUserResponseDto.success(userEntity);
	}

	@Override
	public ResponseEntity<? super GetUserResponseDto> getUser(String email) {
		
		UserEntity userEntity = null;
		
		try {
			
			userEntity = userRepository.findByEmail(email);
			if(userEntity == null) return GetUserResponseDto.noExistUser();
			
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseDto.databaseError();
		}
		
		return GetUserResponseDto.success(userEntity);
	}

	@Override
	public ResponseEntity<? super PatchNicknameResponseDto> patchNickname(PatchNicknameRequestDto dto, String email) {
		
		try {
		
			UserEntity userEntity = userRepository.findByEmail(email);
			if(userEntity == null) PatchNicknameResponseDto.noExistUser();
			
			String nickname = dto.getNickname();
			boolean existedNickname = userRepository.existsByNickname(nickname);
			if(existedNickname) return PatchNicknameResponseDto.duplicateNickname();
			
			userEntity.setNickname(nickname);
			userRepository.save(userEntity);
			
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseDto.databaseError();
		}
		
		return PatchNicknameResponseDto.success();
	}

	@Override
	public ResponseEntity<? super PatchProfileImageResponseDto> patchProfileImage(PatchProfileImageRequestDto dto, String email) {

		try {

			UserEntity userEntity = userRepository.findByEmail(email);
			if(userEntity == null) PatchProfileImageResponseDto.noExistUser();
			
			String profileImage = dto.getProfileImage();
			userEntity.setProfileImage(profileImage);
			userRepository.save(userEntity);
			
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseDto.databaseError();
		}
		
		return PatchProfileImageResponseDto.success();
	}

}
