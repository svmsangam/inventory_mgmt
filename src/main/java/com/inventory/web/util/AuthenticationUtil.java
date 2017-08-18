package com.inventory.web.util;

import com.inventory.core.model.enumconstant.UserType;
import com.inventory.core.util.Authorities;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class AuthenticationUtil {

	public static final com.inventory.core.model.entity.User getCurrentUser() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		//System.out.println("currentUser isAuthenticated >>>>> " + authentication.isAuthenticated());

		if (!authentication.isAuthenticated()){
			return null;
		}

		Object principal = authentication.getPrincipal();

		if (principal instanceof User) {

			User user = (User) principal;

			if (!user.isEnabled()){
				return null;
			}

			com.inventory.core.model.entity.User invUser = new com.inventory.core.model.entity.User();

			Collection<GrantedAuthority> authorities = user.getAuthorities();

			invUser.setUsername(user.getUsername());
			invUser.setPassword(user.getPassword());
			invUser.setEnabled(user.isEnabled());
			
			for (GrantedAuthority authority : authorities) {
				// userOne.setAuthority(authority.getAuthority());
				//System.out.println("currentUser Authority >>>>> " + authority.getAuthority());
				invUser.setAuthority(authority.getAuthority());

				if (authority.getAuthority().contains(Authorities.SUPERADMIN)){
					invUser.setUserType(UserType.SUPERADMIN);
				}else if (authority.getAuthority().contains(Authorities.SYSTEM)){
					invUser.setUserType(UserType.SYSTEM);
				}else if (authority.getAuthority().contains(Authorities.ADMINISTRATOR)){
					invUser.setUserType(UserType.ADMIN);
				}else if (authority.getAuthority().contains(Authorities.USER)){
					invUser.setUserType(UserType.USER);
				}
			}
			return invUser;
		}
		return null;
	}
}
