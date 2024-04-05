package com.example.board.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.example.board.dto.CommentDto;
import com.example.board.dto.PostDto;
import com.example.board.dto.UserDto;
import com.example.board.model.Comment;
import com.example.board.model.Post;
import com.example.board.model.User;
import com.example.board.repository.CommentRepository;
import com.example.board.repository.PostRepository;
import com.example.board.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {
	
	
	private final UserRepository userRepository;
	
	private final PostRepository postRepository;
	
	private final CommentRepository commentRepository;

	public String commentWrite(CommentDto commentDto) {
		// TODO Auto-generated method stub
		
		String userId = commentDto.getUserDto().getId();
		User user = userRepository.findById(userId);
		
		int postNo = commentDto.getPostDto().getNo();
		Post post = postRepository.findById(postNo).get();
		
		
		Comment comment = Comment.builder()
				.Comment(commentDto.getComment())
				.post(post)
				.user(user)
				.build();
		
		commentRepository.save(comment);
	
		return "댓글 작성 완료";
	}

	public ArrayList<CommentDto> getAllComments() {
		// TODO Auto-generated method stub
		ArrayList<CommentDto> commentDtoList = new ArrayList<CommentDto>();
		
		ArrayList<Comment> commentList = new ArrayList<Comment>(commentRepository.findAll());
		
		for(Comment c : commentList) {
			
			User user = c.getUser();
			UserDto userDto = UserDto.builder()
					.no(user.getNo())
					.id(user.getId())
					.nickName(user.getNickName())
					.build();
			
			Post post = c.getPost();
			PostDto postDto = PostDto.builder()
					.no(post.getNo())
					.content(post.getContent())
					.title(post.getContent())
					.build();
			
			commentDtoList.add(
					CommentDto.builder()
					.no(c.getNo())
					.userDto(userDto)
					.postDto(postDto)
					.comment(c.getComment())
				.build());
		}
		
		
		return commentDtoList;
		
	}

	public String deleteComment(CommentDto commentDto) {
		// TODO Auto-generated method stub
		
		Comment comment = commentRepository.findById(commentDto.getNo()).get();
		
		commentRepository.delete(comment);
		
		return "댓글 삭제 완료";
	}
	
}
