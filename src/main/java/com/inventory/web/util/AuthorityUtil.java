/*
package com.tmk.inventory.web.util;

import java.util.List;

import com.tmk.inventory.core.model.dto.BusinessServicePlanDto;
import permission;
import Authorities;

public class AuthorityUtil {
	
	public static boolean checkBusinessOwnerRole(){
		
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if ((authority.contains(Authorities.BUSINESSOWNER) && authority.contains(Authorities.AUTHENTICATED)) || (authority.contains(Authorities.BUSINESSUSER) && authority.contains(Authorities.AUTHENTICATED)) || (authority.contains(Authorities.SALESPERSONNEL) && authority.contains(Authorities.AUTHENTICATED)) || (authority.contains(Authorities.DELIVERYPERSONNEL) && authority.contains(Authorities.AUTHENTICATED))) {
				return true;
			}
		}
		
		return false;
	}

public static boolean checkAdmiRole(){
	
	if (AuthenticationUtil.getCurrentUser() != null) {
		String authority = AuthenticationUtil.getCurrentUser().getAuthority();
		if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
			return true;
		}
	}
	
	return false;
}

public static boolean checkPermission(permission permission, BusinessServicePlanDto businessServicePlanDto){
	
	if (businessServicePlanDto != null) {
		List<permission> permissions = businessServicePlanDto.getPermissionList();
		for(permission sPlan : permissions){
		if (sPlan.equals(permission)) {
			return true;
		}
		}
	}
	
	return false;
}


}

*/
