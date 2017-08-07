package com.inventory.web.util;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

public class AuthenticationUtil {

	public static final com.inventory.core.model.entity.User getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			return null;
		}
		Object principal = authentication.getPrincipal();
		if (principal instanceof User) {
			User user = (User) principal;
			com.inventory.core.model.entity.User amtUser = new com.inventory.core.model.entity.User();
			Collection<GrantedAuthority> authorities = user.getAuthorities();
			amtUser.setUsername(user.getUsername());
			amtUser.setPassword(user.getPassword());
			
			for (GrantedAuthority authority : authorities) {
				// userOne.setAuthority(authority.getAuthority());
				amtUser.setAuthority(authority.getAuthority());
			}
			return amtUser;
		}
		return null;
	}
}
