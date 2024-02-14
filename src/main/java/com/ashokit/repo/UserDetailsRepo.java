package com.ashokit.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.ashokit.entity.UserDetails;

public interface UserDetailsRepo extends JpaRepository<UserDetails, Integer> {
	
	public UserDetails findByemail(String email);
	
	public Optional<UserDetails> findByUserId(Integer userId);
	
	
	public UserDetails findByEmailAndPassword(String email, String password);
	
	
	
	
	
}
