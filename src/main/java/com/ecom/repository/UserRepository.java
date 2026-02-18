package com.ecom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecom.model.UserDetls;

public interface UserRepository extends JpaRepository<UserDetls, Integer>{

	public UserDetls findByEmail(String email);

	public List<UserDetls> findByRole(String role);
	
	public UserDetls findByResetToken(String token);
	
}
