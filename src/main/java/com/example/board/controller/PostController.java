package com.example.board.controller;

import java.util.ArrayList;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.board.dto.PostDto;
import com.example.board.dto.UserDto;
import com.example.board.service.PostService;

import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class PostController {
	
	private final PostService postService;
	
	@PostMapping("/post/write")
	public String write(Authentication authentication , @RequestBody PostDto postDto) {
		
		
		
		postDto.setUserDto(UserDto.builder().id(authentication.getPrincipal().toString()).build());
		
		
		postService.write(postDto);
		
		
		return "글작성완료";
	}
	
	@GetMapping("/post/list")
	public ArrayList<PostDto> getAllPosts() {
		
		ArrayList<PostDto> postDtoList = postService.getAllPosts();
		return postDtoList;
	}
	
	@PutMapping("/post/edit")
	public String postEdit(@RequestBody PostDto postDto) {
		
		System.out.println(postDto.toString());
		
		return postService.postEdit(postDto);
		
		//return "글수정완료";
	}
	
	@DeleteMapping("/post/delete")
	@Transactional
	public String postDelete(@RequestParam("no") int postNo) {
		System.out.println(postNo);
		
		PostDto postDto = PostDto.builder().no(postNo).build();
		return postService.postDelete(postDto);
	}
	
	
	@GetMapping("/post")
	public PostDto getPost(@RequestParam("no") int no) {
		
		System.out.println("post/{no}");
		PostDto postDto = postService.findByNo(no);
		
		
		
		return postDto;
		
	}
	
	@GetMapping("/post/search")
	public ArrayList<PostDto> postSearch(@RequestParam("keyWord") String keyWord){
		
		System.out.println("search");
		PostDto postDto = PostDto.builder().title(keyWord).build();
		ArrayList<PostDto> postDtoList = postService.postSearch(postDto);
		
		return postService.postSearch(postDto);
		
	}
	
}
