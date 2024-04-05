package com.example.board.model;


import jakarta.persistence.Id;

import com.example.board.dto.UserDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class User{
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int no;
	@Column(columnDefinition = "VARCHAR(255) CHARACTER SET UTF8")
	private String id;
	@Column(columnDefinition = "VARCHAR(255) CHARACTER SET UTF8")
	private String password;
	@Column(columnDefinition = "VARCHAR(255) CHARACTER SET UTF8")
	private String nickName;
	
	
	public User(UserDto userDto) {
		this.id = userDto.getId();
		this.password = userDto.getPassword();
		this.no = userDto.getNo();
		this.nickName = userDto.getNickName();
	}
	
	
	
	
	
	
	
	
}