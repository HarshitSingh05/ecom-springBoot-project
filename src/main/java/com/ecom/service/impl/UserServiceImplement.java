package com.ecom.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecom.model.UserDetls;
import com.ecom.repository.UserRepository;
import com.ecom.service.UserService;
import com.ecom.util.AppConstant;

@Service
public class UserServiceImplement implements UserService  {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	@Override
	public UserDetls saveUser(UserDetls user) {
		
	user.setRole("ROLE_USER");
	user.setEnable(true);
	user.setAccountNonLocked(true);
	user.setFailedAttempt(0);
	String encodePassword = passwordEncoder.encode(user.getPassword());
	user.setPassword(encodePassword);
	
	
	UserDetls saveUser = userRepository.save(user);
	return saveUser;
	}
	
	
	@Override
	public UserDetls getUserByEmail(String email) {
	
		return userRepository.findByEmail(email);
	}


	@Override
	public List<UserDetls> getUsers(String role) {
		
		return userRepository.findByRole(role);
	}


	@Override
	public Boolean updateAccountStatus(Integer id, Boolean status) {
		
		Optional<UserDetls> findByUser = userRepository.findById(id);
		if(findByUser.isPresent()) {
			UserDetls userDetls = findByUser.get();
			userDetls.setEnable(status);
			userRepository.save(userDetls);
			return true;
		}
		return false;
	}


	@Override
	public void increaseFailedAttempt(UserDetls user) {
	    int attempt = user.getFailedAttempt() + 1;
	    user.setFailedAttempt(attempt);
	    userRepository.save(user);
		
	}


	@Override
	public void userAccountLock(UserDetls user) {
		user.setAccountNonLocked(false);
		user.setLockTime(new Date());
		userRepository.save(user);
		
	}


	@Override
	public Boolean unlockAccountTimeExpired(UserDetls user) {
		
		long lockTime = user.getLockTime().getTime();
		long unlockTime = lockTime + AppConstant.UNLOCK_DURATION_TIME;
		
		long currentTime = System.currentTimeMillis();
		
		if(unlockTime < currentTime) {
			user.setAccountNonLocked(true);
			user.setFailedAttempt(0);
			user.setLockTime(null);
			userRepository.save(user);
			return true;
		}
		
		return false;
	}


	@Override
	public void resetAttempt(int userId) {
		
		
	}


	@Override
	public void updateUserResetToken(String email, String resetToken) {
		UserDetls userEmail = userRepository.findByEmail(email);
		userEmail.setResetToken(resetToken);
		userRepository.save(userEmail);
		
	}


	@Override
	public UserDetls getUserByToken(String token) {
		
	return userRepository.findByResetToken(token);
	
	}


	@Override
	public UserDetls updateUser(UserDetls user) {
		
		return userRepository.save(user);
	}

}

