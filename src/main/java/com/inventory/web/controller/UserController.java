package com.inventory.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.inventory.core.api.iapi.IStoreInfoApi;
import com.inventory.core.api.iapi.IStoreUserInfoApi;
import com.inventory.core.api.iapi.IUserApi;
import com.inventory.core.api.iapi.IUserPermissionApi;
import com.inventory.core.model.dto.InvUserDTO;
import com.inventory.core.model.dto.UserPermissionDTO;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.model.enumconstant.UserType;
import com.inventory.core.util.Authorities;
import com.inventory.core.util.CustomSessionService;
import com.inventory.core.validation.UserValidation;
import com.inventory.web.error.PasswordError;
import com.inventory.web.error.UserManageError;
import com.inventory.web.util.AuthenticationUtil;
import com.inventory.web.util.LoggerUtil;
import com.inventory.web.util.StringConstants;
import com.inventory.web.util.UIUtil;

@Controller
@RequestMapping("user")
public class UserController {

	@Autowired
	private IUserApi userApi;

	@Autowired
	private UserValidation userValidation;

	@Autowired
	private IUserPermissionApi userPermissionApi;

	@Autowired
	private IStoreInfoApi storeInfoApi;

	@Autowired
	private IStoreUserInfoApi storeUserInfoApi;

	@Autowired
	private CustomSessionService sessionService;

	/*
	 * @Autowired private SessionInfo sessionInfo;
	 */

	@Autowired
	private PasswordEncoder passwordEncoder;

	public PasswordEncoder getPasswordEncoder() {
		return passwordEncoder;
	}

	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	@GetMapping(value = "/changepassword")
	@PreAuthorize("isAuthenticated()")
	public String changePassword(RedirectAttributes redirectAttributes) {
		return "user/changePassword";
	}

	@PostMapping(value = "/changepassword")
	@PreAuthorize("isAuthenticated()")
	public String updatePassword(@RequestParam("oldpassword") String oldPassword,
			@RequestParam("newpassword") String newPassword, @RequestParam("confirmpassword") String confirmPassword,
			RedirectAttributes redirectAttributes) {

		try {

			InvUserDTO currentUser = AuthenticationUtil.getCurrentUser(userApi);

			PasswordError error = userValidation.change(oldPassword, newPassword, confirmPassword,
					currentUser.getUserId());

			if (!error.isValid()) {
				redirectAttributes.addFlashAttribute(StringConstants.ERROR, error.getError());
				return "redirect:/user/changepassword";
			}

			userApi.changePassword(currentUser.getUserId(), newPassword.trim());

			redirectAttributes.addFlashAttribute(StringConstants.MESSAGE, "password changed successfully");

		} catch (Exception e) {
			LoggerUtil.logException(this.getClass(), e);
			return "redirect:/500";
		}

		return "redirect:/dashboard";
	}

	@GetMapping(value = "/list")
	public String list(ModelMap modelMap, HttpSession session, RedirectAttributes redirectAttributes) {
		try {

			InvUserDTO currentUser = AuthenticationUtil.getCurrentUser(userApi);

			if (currentUser == null) {
				redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Athentication failed");
				return "redirect:/logout";
			}

			if (currentUser.getStoreId() == null && !(currentUser.getUserauthority().contains(Authorities.SYSTEM))) {
				redirectAttributes.addFlashAttribute(StringConstants.INFO, UIUtil.addStoreMessage());
				return "redirect:/store/list";// store not assigned page
			}

			if (currentUser.getUserauthority().contains(Authorities.SYSTEM)
					&& currentUser.getUserauthority().contains(Authorities.AUTHENTICATED)) {

				List<UserType> userTypeList = new ArrayList<>();

				userTypeList.add(UserType.SUPERADMIN);

				modelMap.put(StringConstants.USERTYPE_LIST, userTypeList);

				modelMap.put(StringConstants.USER_LIST,
						userApi.getAllByStatusAndUserTypeIn(Status.ACTIVE, userTypeList));

				return "user/list";

			} else if (currentUser.getUserauthority().contains(Authorities.SUPERADMIN)
					&& currentUser.getUserauthority().contains(Authorities.AUTHENTICATED)) {

				List<UserType> userTypeList = new ArrayList<>();

				userTypeList.add(UserType.ADMIN);
				userTypeList.add(UserType.USER);

				modelMap.put(StringConstants.USERTYPE_LIST, userTypeList);

				modelMap.put(StringConstants.USER_LIST, userApi.getAllByStatusAndUserTypeInAndSuperAdmin(Status.ACTIVE,
						userTypeList, currentUser.getUserId()));

				modelMap.put(StringConstants.STORE_LIST, storeUserInfoApi.getAllStoreByUser(currentUser.getUserId()));

				return "user/listUser";

			} else if (currentUser.getUserauthority().contains(Authorities.ADMINISTRATOR)
					&& currentUser.getUserauthority().contains(Authorities.AUTHENTICATED)) {

				List<UserType> userTypeList = new ArrayList<>();

				userTypeList.add(UserType.USER);

				modelMap.put(StringConstants.USERTYPE_LIST, userTypeList);

				modelMap.put(StringConstants.USER_LIST, userApi.getAllByStatusAndUserTypeInAndStoreInfo(Status.ACTIVE,
						userTypeList, currentUser.getStoreId()));

				return "user/listUser";
			} else {

				redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Access denied");
				return "redirect:/";
			}

		} catch (Exception e) {
			LoggerUtil.logException(this.getClass(), e);
			return "redirect:/500";
		}
	}

	@GetMapping(value = "/manage")
	@PreAuthorize("hasAnyRole('ROLE_SUPERADMINISTRATOR','ROLE_ADMINISTRATOR')")
	public String manage(@RequestParam("userId") long userId, ModelMap modelMap,
			RedirectAttributes redirectAttributes) {
		try {
			UserManageError error = userValidation.onManage(userId);

			if (!error.isValid()) {
				redirectAttributes.addFlashAttribute(StringConstants.ERROR, error.getError());
				return "redirect:/user/list";
			}

			InvUserDTO userDTO = userApi.getUserWithId(userId);

			modelMap.put(StringConstants.USER, userDTO);
			UserPermissionDTO userPermissionDTO = userPermissionApi.getByUserId(userId);

			if (userPermissionDTO != null) {
				if (userPermissionDTO.getPermissionList() != null)
					modelMap.put(StringConstants.USER_PERMISSION, userPermissionDTO.getPermissionList());
			}

			if (userDTO.getStoreId() != null) {
				modelMap.put(StringConstants.STORE, storeInfoApi.show(userDTO.getStoreId(), Status.ACTIVE));
			}

			return "user/manageUser";

		} catch (Exception e) {
			LoggerUtil.logException(this.getClass(), e);
			return "redirect:/500";
		}
	}

	@PostMapping(value = "/manage")
	@PreAuthorize("hasAnyRole('ROLE_SUPERADMINISTRATOR','ROLE_ADMINISTRATOR')")
	public String manage(@RequestAttribute("userpermission") UserPermissionDTO userPermissionDTO, ModelMap modelMap,
			RedirectAttributes redirectAttributes) {
		try {
			synchronized (this.getClass()) {
				UserManageError error = userValidation.onManage(userPermissionDTO.getUserId());

				if (!error.isValid()) {
					redirectAttributes.addFlashAttribute(StringConstants.ERROR, error.getError());
					return "redirect:/user/list";
				}

				UserPermissionDTO userPermissionDTO1 = userPermissionApi.getByUserId(userPermissionDTO.getUserId());

				if (userPermissionDTO1 == null) {
					userPermissionApi.save(userPermissionDTO);
				} else {
					userPermissionDTO.setUserPermissionId(userPermissionDTO1.getUserPermissionId());
					userPermissionApi.update(userPermissionDTO);
				}

				redirectAttributes.addFlashAttribute(StringConstants.MESSAGE, "user managed successfully");

				return "redirect:/user/manage?userId=" + userPermissionDTO.getUserId();
			}

		} catch (Exception e) {
			LoggerUtil.logException(this.getClass(), e);
			;
			return "redirect:/";
		}
	}

	@GetMapping(value = "/updateenable")
	@PreAuthorize("hasAnyRole('ROLE_SUPERADMINISTRATOR','ROLE_ADMINISTRATOR')")
	public String updateEnable(@RequestParam("userId") long userId, ModelMap modelMap,
			RedirectAttributes redirectAttributes) {
		try {
			synchronized (this.getClass()) {
				UserManageError error = userValidation.onUpadteEnable(userId);

				if (!error.isValid()) {
					redirectAttributes.addFlashAttribute(StringConstants.ERROR, error.getError());
					return "redirect:/user/list";
				}

				InvUserDTO userDTO = userApi.updateEnable(userId);
			}

			/*
			 * if (!userDTO.getEnable()){ sessionInfo.listSale(userDTO.getInventoryuser());
			 * }
			 */

			redirectAttributes.addFlashAttribute(StringConstants.MESSAGE, "user updated successfully");
			return "redirect:/user/list";

		} catch (Exception e) {
			LoggerUtil.logException(this.getClass(), e);
			return "redirect:/";
		}
	}

	@GetMapping(value = "/activate")
	public String updateEnable(@RequestParam("token") String token, ModelMap modelMap,
			RedirectAttributes redirectAttributes, HttpServletRequest request) {
		try {

			InvUserDTO currentUser = AuthenticationUtil.getCurrentUser(userApi);

			if (currentUser != null) {
				redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Athentication failed");
				return "redirect:/";
			}

			if (token == null) {
				redirectAttributes.addFlashAttribute(StringConstants.ERROR, "invalid request");
				return "redirect:/";
			}

			if (userApi.getByToken(token) == null) {
				redirectAttributes.addFlashAttribute(StringConstants.ERROR, "invalid token");
				return "redirect:/";
			}

			currentUser = userApi.verifyUser(token);

			sessionService.authenticateUserAndSetSession(currentUser, request);
			redirectAttributes.addFlashAttribute(StringConstants.MESSAGE, "user activated successfully");

		} catch (Exception e) {
			LoggerUtil.logException(this.getClass(), e);
			return "redirect:/500";
		}

		return "redirect:/dashboard";
	}
}
