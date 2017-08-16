package com.inventory.web.controller;

import com.inventory.core.api.iapi.IUserApi;
import com.inventory.core.api.iapi.IUserPermissionApi;
import com.inventory.core.model.dto.InvUserDTO;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.model.enumconstant.UserType;
import com.inventory.core.util.Authorities;
import com.inventory.core.validation.UserValidation;
import com.inventory.web.util.AuthenticationUtil;
import com.inventory.web.util.StringConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("user")
public class UserController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IUserApi userApi;

	@Autowired
	private UserValidation userValidation;

	@Autowired
	private IUserPermissionApi userPermissionApi;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public PasswordEncoder getPasswordEncoder() {
		return passwordEncoder;
	}

	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	@GetMapping( value = "/list")
	public String list(ModelMap modelMap , RedirectAttributes redirectAttributes) {
		try {
			if (AuthenticationUtil.getCurrentUser() != null) {

				String authority = AuthenticationUtil.getCurrentUser().getAuthority();

				if (authority.contains(Authorities.SYSTEM) && authority.contains(Authorities.AUTHENTICATED)) {

					List<UserType> userTypeList = new ArrayList<>();

					userTypeList.add(UserType.SUPERADMIN);

					modelMap.put(StringConstants.USERTYPE_LIST , userTypeList);

					modelMap.put(StringConstants.USER_LIST , userApi.getAllByStatusAndUserTypeIn(Status.ACTIVE , userTypeList));

					return "user/listUser";

				}else if (authority.contains(Authorities.SUPERADMIN) && authority.contains(Authorities.AUTHENTICATED)){

					List<UserType> userTypeList = new ArrayList<>();

					userTypeList.add(UserType.ADMIN);
					userTypeList.add(UserType.USER);

					modelMap.put(StringConstants.USERTYPE_LIST , userTypeList);

					modelMap.put(StringConstants.USER_LIST , userApi.getAllByStatusAndUserTypeIn(Status.ACTIVE , userTypeList));

					return "user/listUser";

				}else if(authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)){

					InvUserDTO currentUser = userApi.getUserByUserName(AuthenticationUtil.getCurrentUser().getUsername());

					List<UserType> userTypeList = new ArrayList<>();

					userTypeList.add(UserType.USER);

					modelMap.put(StringConstants.USERTYPE_LIST , userTypeList);

					modelMap.put(StringConstants.USER_LIST , userApi.getAllByStatusAndUserTypeInAndStoreInfo(Status.ACTIVE , userTypeList , currentUser.getStoreId()));

					return "user/listUser";
				}else {

					redirectAttributes.addFlashAttribute(StringConstants.ERROR , "Access deniled");
					return "redirect:/";
				}
			}

			return "redirect:/";
		} catch (Exception e) {
			logger.error("Stack trace: " + e.getStackTrace());
			return "redirect:/";
		}
	}


	@GetMapping( value = "/manage")
	public String manage(@RequestParam("userId")long userId , BindingResult bindingResult, ModelMap modelMap , RedirectAttributes redirectAttributes) {
		try {
			if (AuthenticationUtil.getCurrentUser() != null) {

				String authority = AuthenticationUtil.getCurrentUser().getAuthority();

				if ((authority.contains(Authorities.ADMINISTRATOR) || authority.contains(Authorities.SUPERADMIN)) && authority.contains(Authorities.AUTHENTICATED)) {

					if (bindingResult.hasErrors() | userId < 0){
						redirectAttributes.addFlashAttribute(StringConstants.ERROR , "invalid user");
						return "redirect:/user/list";
					}

					InvUserDTO userDTO = userApi.getUserWithId(userId);

					if (userDTO == null){
						redirectAttributes.addFlashAttribute(StringConstants.ERROR , "user not found");
						return "redirect:/user/list";
					}

					modelMap.put(StringConstants.USER , userDTO);
					modelMap.put(StringConstants.USER_PERMISSION , userPermissionApi.getByUserId(userId));

					return "user/manageUser";

				}else {

					redirectAttributes.addFlashAttribute(StringConstants.ERROR , "Access deniled");
					return "redirect:/";
				}
			}

			return "redirect:/";
		} catch (Exception e) {
			logger.error("Stack trace: " + e.getStackTrace());
			return "redirect:/";
		}
	}
}
