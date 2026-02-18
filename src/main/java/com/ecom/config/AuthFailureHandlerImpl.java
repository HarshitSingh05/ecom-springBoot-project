package com.ecom.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.ecom.model.UserDetls;
import com.ecom.repository.UserRepository;
import com.ecom.service.UserService;
import com.ecom.util.AppConstant;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthFailureHandlerImpl extends SimpleUrlAuthenticationFailureHandler {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserService userService;

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		
		String email = request.getParameter("username");
		UserDetls userDetls = userRepository.findByEmail(email);
		
		if(userDetls.getEnable()) {
			
			if(userDetls.getAccountNonLocked()) {
				
				if(userDetls.getFailedAttempt() < AppConstant.ATTEMPT_TIME) {
					
					userService.increaseFailedAttempt(userDetls);
					
				}else {
					userService.userAccountLock(userDetls);
					exception = new LockedException("Your account is Locked !! failed attempt 3");
				}
				
			}else {
				
				if(userService.unlockAccountTimeExpired(userDetls)) {
					
					 exception = new LockedException("Your account is unlocked !! Please try to login"); 
				}else {
					exception = new LockedException("Your account is Locked !! Try after sometimes");
				}
				
			}
			
		}else {
			exception = new LockedException("Your account is Inactive");
		}
		
		super.setDefaultFailureUrl("/signin?error");
		super.onAuthenticationFailure(request, response, exception);
	}

	
}
