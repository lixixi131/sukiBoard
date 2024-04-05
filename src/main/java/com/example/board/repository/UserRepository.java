 package com.example.board.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.board.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	
	User findById(String id);
	
	User findByNickName(String nickName);
}