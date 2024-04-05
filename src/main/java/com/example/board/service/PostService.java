package com.example.board.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.board.dto.PostDto;
import com.example.board.dto.UserDto;
import com.example.board.model.Post;
import com.example.board.model.User;
import com.example.board.repository.PostRepository;
import com.example.board.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {

	private final UserRepository userRepository;
	
	private final PostRepository postRepository;
	
	public String write(PostDto postDto) {
		// TODO Auto-generated method stub
		
		User user = userRepository.findById(postDto.getUserDto().getId());
		
		Post post = Post.builder()
				.title(postDto.getTitle())
				.content(postDto.getContent())
				.user(user)
				.build();
				
		postRepository.save(post);
		
		return "글 작성 완료";
		
	}

	public ArrayList<PostDto> getAllPosts() {
		// TODO Auto-generated method stub
		
		ArrayList<Post> postList = new ArrayList<Post>();
		postList.addAll(postRepository.findAll());
		
		
		ArrayList<PostDto> postDtoList = new ArrayList<PostDto>();
		
		for(Post post : postList) {
			
			UserDto userDto = new UserDto(post.getUser());
			
			postDtoList.add(
					PostDto
					.builder()
					.no(post.getNo())
					.title(post.getTitle())
					.content(post.getContent())
					.userDto(userDto)
					.build());
			
		}
		
		return postDtoList;
		
	}

	public PostDto findByNo(int no) {
		// TODO Auto-generated method stub
		
		Post post = postRepository.findById(no).get();
		
		UserDto userDto = new UserDto(post.getUser());
		
		PostDto postDto = PostDto.builder()
				.content(post.getContent())
				.title(post.getTitle())
				.no(post.getNo())
				.userDto(userDto)
				.build();
		
		return postDto;
		
	}

	public String postEdit(PostDto postDto) {
		// TODO Auto-generated method stub
		
		Post beforeEditPost = postRepository.findById(postDto.getNo()).get();
		
		beforeEditPost.setContent(postDto.getContent());
		beforeEditPost.setTitle(postDto.getTitle());
		
		postRepository.save(beforeEditPost);
		
		return "글수정완료";
	}

	public String postDelete(PostDto postDto) {
		// TODO Auto-generated method stub
		
		Post deletePost = postRepository.findById(postDto.getNo()).get();
		postRepository.delete(deletePost);
		return "삭제 완료";
	}

	public ArrayList<PostDto> postSearch(PostDto postDto) {
		// TODO Auto-generated method stub
		String keyWord = postDto.getTitle();
		
		ArrayList<Post> postList = new ArrayList<Post>();
		postList.addAll(postRepository.findAll());
		
		ArrayList<PostDto> postDtoList = new ArrayList<PostDto>();
		
		for(Post p : postList) {
			String postTitle = p.getTitle();
			if(postTitle.matches("(.*)"+ keyWord + "(.*)")) {
				
				UserDto userDto = new UserDto(p.getUser());

				postDtoList.add(
						PostDto.builder()
						.no(p.getNo())
						.title(p.getTitle())
						.content(p.getContent())
						.userDto(userDto)
						.build());
				
			}
			
			
		}
		
		return postDtoList;
	}

	
	
	
	
}
