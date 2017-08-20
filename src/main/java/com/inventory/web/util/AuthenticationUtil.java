package com.inventory.web.util;

import com.inventory.core.api.iapi.IUserApi;
import com.inventory.core.model.dto.InvUserDTO;
import com.inventory.core.model.enumconstant.Status;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

public class AuthenticationUtil {

	public static final InvUserDTO getCurrentUser(IUserApi userApi) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null) {
			return null;
		}

		if (!authentication.isAuthenticated()){
			return null;
		}

		Object principal = authentication.getPrincipal();

		if (principal instanceof User) {

			User user = (User) principal;

			InvUserDTO userDTO = userApi.getUserByUserName(user.getUsername());

			if (userDTO == null){
				return null;
			}

			if (userDTO.getUserauthority() == null){
				return null;
			}

			if (!userDTO.getUserstatus().equals(Status.ACTIVE)){
				return null;
			}

			if (!userDTO.getEnable()){
				return null;
			}

			return userDTO;

		}
		return null;
	}
}
