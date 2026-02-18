package com.ecom.service;

import java.util.List;

import com.ecom.model.UserDetls;

public interface UserService {

	public UserDetls saveUser(UserDetls user);
	
	public UserDetls getUserByEmail(String email);
	
	public List<UserDetls> getUsers(String role);

	public Boolean updateAccountStatus(Integer id, Boolean status);
	
	public void increaseFailedAttempt(UserDetls user);
	
	public void userAccountLock(UserDetls user);
	
	public Boolean unlockAccountTimeExpired(UserDetls user);
	
	public void resetAttempt(int userId);

	public void updateUserResetToken(String email, String resetToken);
	
	public UserDetls getUserByToken(String token);
	
	public UserDetls updateUser(UserDetls user);
}
