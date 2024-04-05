package com.example.board.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.board.dto.LogInResponseDto;
import com.example.board.dto.UserDto;
import com.example.board.model.User;
import com.example.board.repository.UserRepository;
import com.example.board.security.TokenProvider;

import lombok.RequiredArgsConstructor;


@Service

public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private TokenProvider tokenProvider;
	
	public LogInResponseDto logIn(UserDto u) {
		
		User tmp = userRepository.findById(u.getId());
		LogInResponseDto response = new LogInResponseDto();
		
		if(tmp == null) {
			response.setResponse("존재하지 않는 아이디입니다.");
			response.setTokenDto(null);
		}
		
		else if(!(passwordEncoder.matches(u.getPassword() , tmp.getPassword() ))) {
			response.setResponse("비밀번호가 틀립니다.");
			response.setTokenDto(null);
		}
		
		else {
			response.setResponse("로그인 성공");
			response.setTokenDto(tokenProvider.createToken(u.getId()));
			response.setNickName(tmp.getNickName());
		}
		
		return response;
		
	}
	
	public User findById(String id) {
		List<User> users = new ArrayList<User>();
		userRepository.findAll().forEach(e -> users.add(e));
		
		User tmp = null;
		for(User user : users) {
			if(user.getId().equals(id)) {
				tmp = user;
			}
		}
		return tmp;
	}
	
	
	
	public boolean duplicateCheck(UserDto userDto) {
		
	
		User user = userRepository.findById(userDto.getId());
		
		System.out.println(userDto.getId());
		
		boolean isDuplicate = false;
		
		
		if(user == null) {

			isDuplicate = true;
		}
		
		return isDuplicate;
	}
	
	public boolean nickNameDuplicateCheck(UserDto userDto) {
		
		User user = userRepository.findByNickName(userDto.getNickName());
		
		boolean isDuplicate = false;
		
		
		if(user == null) {

			isDuplicate = true;
		}
		
		return isDuplicate;
		
		
	}



	public String signUp(UserDto userDto) {
		// TODO Auto-generated method stub
		User tmp = new User(userDto);
		userRepository.save(tmp);
		
		return "회원가입성공";
		
	}
	
}
