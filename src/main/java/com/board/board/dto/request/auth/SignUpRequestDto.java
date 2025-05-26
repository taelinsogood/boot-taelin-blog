package com.board.board.dto.request.auth;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SignUpRequestDto {
	@NotBlank 
	@Email
	private String email;
	
	@NotBlank 
	@Size(min=8, max=20)
	private String password;
	
	@NotBlank 
	private String nickname;
	
	@NotBlank 
	@Pattern(regexp="^[0-9]{11,13}$")
	private String telNumber;
	
	@NotBlank 
	private String address;
	
	private String addressDetail;
	
	@AssertTrue(message = "개인정보처리방침에 동의해야 합니다.")
	private Boolean agreedPersonal;
}
