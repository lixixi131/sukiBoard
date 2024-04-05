package com.example.board.dto;

import com.example.board.model.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

	private int no;
	private String id;
	private String password;
	private String nickName;
	
	public UserDto(User user) {
		this.no = user.getNo();
		this.id = user.getId();
		this.password = user.getPassword();
		this.nickName = user.getNickName();
	}
	
	
}
