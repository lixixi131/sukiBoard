package com.example.board.controller;

import java.util.ArrayList;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.board.dto.CommentDto;
import com.example.board.dto.UserDto;
import com.example.board.service.CommentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CommentController {

	private final CommentService commentService;
	
	@PostMapping("/comment/write")
	public String commentWrite(
			Authentication authentication, 
			@RequestBody CommentDto commentDto) {
		
		String userId = authentication.getPrincipal().toString();
		
		commentDto.setUserDto(UserDto.builder().id(userId).build());
		
		return commentService.commentWrite(commentDto);
	}


	@GetMapping("/comment/list")
	public ArrayList<CommentDto> getAllComments(@RequestParam("no") int no){
		
		ArrayList<CommentDto> tmp = commentService.getAllComments();
		ArrayList<CommentDto> commentDtoList = new ArrayList<CommentDto>();
		for(CommentDto comment : tmp) {
			if(comment.getPostDto().getNo() == no) {
				commentDtoList.add(comment);
			}
			
		}
		
		
		return commentDtoList;
		
		//return commentDtoList;
		
		
	}
	
	@DeleteMapping("/comment/delete")
	public String deleteComment(@RequestParam("no") int no , CommentDto commentDto) {
		System.out.println(no);
		return commentService.deleteComment(commentDto);
		
	}

}
