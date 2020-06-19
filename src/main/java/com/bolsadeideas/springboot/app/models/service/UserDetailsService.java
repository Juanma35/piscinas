package com.bolsadeideas.springboot.app.models.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserDetailsService {
	
	public UserDetails loadUserByUsername(String username);
}
