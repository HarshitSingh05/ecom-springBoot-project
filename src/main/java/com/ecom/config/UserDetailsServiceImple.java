package com.ecom.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ecom.model.UserDetls;
import com.ecom.repository.UserRepository;

@Service
public class UserDetailsServiceImple implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		UserDetls user = userRepository.findByEmail(username);
		if(user == null) {
			throw new UsernameNotFoundException("User Not Found");
		}
		return new CustomUser(user);
	}

}
