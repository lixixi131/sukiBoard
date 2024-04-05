package com.example.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LogInResponseDto {
	private TokenDto tokenDto;
	private String response;
	private String nickName;
}
	