package com.inventory.web.controller;

import com.inventory.core.api.iapi.IUserApi;
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
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("user")
public class UserController implements MessageSourceAware {
	private Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private IUserApi userApi;

	@Autowired
	private UserValidation userValidation;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	public PasswordEncoder getPasswordEncoder() {
		return passwordEncoder;
	}

	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	@GetMapping( value = "/list")
	public String list(ModelMap modelMap) {
		try {
			if (AuthenticationUtil.getCurrentUser() != null) {

				String authority = AuthenticationUtil.getCurrentUser().getAuthority();

				if (authority.contains(Authorities.SYSTEM) && authority.contains(Authorities.AUTHENTICATED)) {

					List<UserType> userTypeList = new ArrayList<>();

					userTypeList.add(UserType.SUPERADMIN);

					modelMap.put(StringConstants.USERTYPE_LIST , userTypeList);

					modelMap.put(StringConstants.USER_LIST , userApi.getAllByStatusAndUserTypeIn(Status.ACTIVE , userTypeList));

				}if (authority.contains(Authorities.SUPERADMIN) && authority.contains(Authorities.AUTHENTICATED)){

					List<UserType> userTypeList = new ArrayList<>();

					userTypeList.add(UserType.ADMIN);
					userTypeList.add(UserType.USER);

					modelMap.put(StringConstants.USERTYPE_LIST , userTypeList);

					modelMap.put(StringConstants.USER_LIST , userApi.getAllByStatusAndUserTypeIn(Status.ACTIVE , userTypeList));

				}if(authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)){

					InvUserDTO currentUser = userApi.getUserByUserName(AuthenticationUtil.getCurrentUser().getUsername());

					List<UserType> userTypeList = new ArrayList<>();

					userTypeList.add(UserType.USER);

					modelMap.put(StringConstants.USERTYPE_LIST , userTypeList);

					modelMap.put(StringConstants.USER_LIST , userApi.getAllByStatusAndUserTypeInAndStoreInfo(Status.ACTIVE , userTypeList , currentUser.getStoreId()));

				}

				return "user/listUser";

			}

			return "redirect:/";
		} catch (Exception e) {
			logger.error("Stack trace: " + e.getStackTrace());
			return "redirect:/";
		}
	}
}
