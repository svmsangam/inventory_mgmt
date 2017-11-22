package com.inventory.web.util;

import com.inventory.web.session.UserDetailsWrapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthenticationUtil {

	public static final com.inventory.core.model.entity.User getCurrentUser() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null) {
			return null;
		}

		Object principal = authentication.getPrincipal();

		if (principal instanceof UserDetailsWrapper) {

			com.inventory.core.model.entity.User user = ((UserDetailsWrapper) principal).getUser();

			return user;
		}
		return null;
	}
}
