package com.inventory.web.util;

import com.inventory.core.api.iapi.IUserApi;
import com.inventory.core.model.dto.InvUserDTO;
import com.inventory.core.model.dto.ServiceDTO;
import com.inventory.core.model.dto.SubscriberServiceDTO;
import com.inventory.core.model.entity.User;
import com.inventory.core.model.enumconstant.Permission;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.model.enumconstant.UserType;
import com.inventory.web.session.UserDetailsWrapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Date;

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

    public static final User getCurrentUserInfo() {

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

            if (user == null){
                return null;
            }

            if (user.getUser() == null){
                return null;
            }

            return user.getUser();

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

    public static final SubscriberServiceDTO getSubscriberService(){

        UserDetailsWrapper userDetailsWrapper = getCurrentUser();

        if (userDetailsWrapper == null){
            return null;
        }

        return userDetailsWrapper.getSubscriberServiceDTO();
    }

    public static final ServiceDTO getService(){

       SubscriberServiceDTO subscriberServiceDTO = getSubscriberService();

        if (subscriberServiceDTO == null){
            return null;
        }

        Date currentDate = DateFormatter.calculateExpiryDate();

        if (subscriberServiceDTO.getExpireOn().before(currentDate)) {
            subscriberServiceDTO.setExpired(false);
        } else {
            subscriberServiceDTO.setExpired(true);
        }

        return subscriberServiceDTO.getServiceInfo();
    }

    public static int getStoreLimit(){

        SubscriberServiceDTO subscriberServiceDTO = getSubscriberService();

        if (subscriberServiceDTO == null){
            return 0;
        }

        if (subscriberServiceDTO.getServiceInfo() == null){
            return 0;
        }

        return subscriberServiceDTO.getServiceInfo().getTotalStore();
    }

    public static int getOrderLimit(){

        SubscriberServiceDTO subscriberServiceDTO = getSubscriberService();

        if (subscriberServiceDTO == null){
            return 0;
        }

        if (subscriberServiceDTO.getServiceInfo() == null){
            return 0;
        }

        return subscriberServiceDTO.getServiceInfo().getTotalOrder();
    }
}
