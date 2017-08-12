package com.inventory.web.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class AuthenticationUtil {

	public static final com.inventory.core.model.entity.User getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			return null;
		}
		Object principal = authentication.getPrincipal();
		if (principal instanceof User) {
			User user = (User) principal;
			com.inventory.core.model.entity.User invUser = new com.inventory.core.model.entity.User();
			Collection<GrantedAuthority> authorities = user.getAuthorities();
			invUser.setUsername(user.getUsername());
			invUser.setPassword(user.getPassword());
			
			for (GrantedAuthority authority : authorities) {
				// userOne.setAuthority(authority.getAuthority());
				System.out.println("currentUser Authority >>>>> " + authority.getAuthority());
				invUser.setAuthority(authority.getAuthority());
			}
			return invUser;
		}
		return null;
	}
}
