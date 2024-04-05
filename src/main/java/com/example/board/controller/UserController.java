package com.example.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.board.dto.LogInResponseDto;
import com.example.board.dto.UserDto;
import com.example.board.model.User;
import com.example.board.security.WebSecurityConfig;
import com.example.board.service.UserService;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class UserController{
	
	@Autowired
	UserService userService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@GetMapping("/user")
	public String User() {
		return "test";
	}
	
	@GetMapping("/user/find")
	public User findUser() {
		User u = userService.findById("zxzx8724");
		System.out.println("u : "  + u.getPassword() + u.getNickName());
		return u;
	}
	
	
	@PostMapping("/logIn")
	public LogInResponseDto logIn(@RequestBody UserDto u) {
	
		String str = "";
		
		
		str += u.getId();
		str += u.getPassword();
		
		
		
		LogInResponseDto response = userService.logIn(u);
		
		
		return response;
	}
	
	@PostMapping("/signUp")
	public String signUp(@RequestBody UserDto u) {
		
		String str = "";
			
	
		String encodedPassword = passwordEncoder.encode(u.getPassword());
		u.setPassword(encodedPassword);
		
		str = userService.signUp(u);

		
		return str;
	}
	
	@PostMapping("/signUp/duplicateCheck")
	public boolean duplicateCheck(@RequestBody UserDto u) {
		
		boolean isDuplicate = false;
		
		System.out.println("중복체크");
		
		isDuplicate = userService.duplicateCheck(u); 
		System.out.printf("userDto : %s" , u.getId());
		System.out.println(isDuplicate);
		return isDuplicate;
		
		
	}
	
	@PostMapping("/signUp/nickNameDuplicateCheck")
	public boolean nickNameDuplicateCheck(@RequestBody UserDto u) {
		
		boolean isDuplicate = false;
		
		System.out.println(u.getNickName());
		isDuplicate = userService.nickNameDuplicateCheck(u); 

		return isDuplicate;
		
		
	}
	
	@PostMapping("/test")
	public String test(@RequestBody UserDto u) {
		System.out.println("test function activated");
		return "테스트 성공";
	}
	
	@GetMapping("/getTest")
	public String getTest() {
		System.out.println("getTest function activated");
		return "테스트 성공";
	}
	
	
	
}