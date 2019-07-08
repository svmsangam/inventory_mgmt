package com.inventory.web.util;

import com.inventory.core.api.iapi.IUserApi;
import com.inventory.core.model.dto.InvUserDTO;
import com.inventory.core.model.enumconstant.Permission;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.model.enumconstant.UserType;
import com.inventory.web.session.UserDetailsWrapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthenticationUtil {

    public static final UserDetailsWrapper getCurrentUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            return null;
        }

        if (!authentication.isAuthenticated()) {
            return null;
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof UserDetailsWrapper) {

            UserDetailsWrapper user = (UserDetailsWrapper) principal;

            return user;

        }

        return null;
    }


    public static final InvUserDTO getCurrentUser(IUserApi userApi) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            return null;
        }

        if (!authentication.isAuthenticated()) {
            return null;
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof UserDetailsWrapper) {

            UserDetailsWrapper user = (UserDetailsWrapper) principal;

            InvUserDTO userDTO = userApi.getUserByUserName(user.getUsername());

            if (userDTO == null) {
                return null;
            }

            if (userDTO.getUserauthority() == null) {
                return null;
            }

            if (!userDTO.getUserstatus().equals(Status.ACTIVE)) {
                return null;
            }

            if (!userDTO.getEnable()) {
                return null;
            }

            if (userDTO.getUserType().equals(UserType.USER)) {
                userDTO.setPermissionList(userApi.getUserPermission(userDTO.getUserId()));
            }

            return userDTO;

        }
        return null;
    }

    public static boolean checkPermission(InvUserDTO currentUser, Permission permission) {

        if (currentUser == null) {
            return false;
        }

        if (currentUser.getPermissionList() == null) {
            return false;
        }

        for (Permission p : currentUser.getPermissionList()) {

            if (p.equals(permission)) {

                return true;
            }
        }

        return false;
    }
}
