package com.inventory.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.inventory.core.api.iapi.IQualificationApi;
import com.inventory.core.api.iapi.IUserApi;
import com.inventory.core.model.dto.InvUserDTO;
import com.inventory.core.model.dto.QualificationLevelDTO;
import com.inventory.core.model.enumconstant.Permission;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.util.Authorities;
import com.inventory.web.util.AuthenticationUtil;
import com.inventory.web.util.LoggerUtil;
import com.inventory.web.util.StringConstants;

/**
 * Created by dhiraj on 2/3/18.
 */
@Controller
@RequestMapping("qualification")
public class QualificationLevelController {

	@Autowired
	private IUserApi userApi;

	@Autowired
	private IQualificationApi qualificationApi;

	@GetMapping(value = "/list")
	@PreAuthorize("hasAnyRole('ROLE_SUPERADMINISTRATOR','ROLE_ADMINISTRATOR','ROLE_USER,ROLE_AUTHENTICATED')")
	public String list(ModelMap modelMap, RedirectAttributes redirectAttributes) {

		try {

			/* current user checking start */
			InvUserDTO currentUser = AuthenticationUtil.getCurrentUser(userApi);

			if (currentUser.getUserauthority().contains(Authorities.USER)
					& !AuthenticationUtil.checkPermission(currentUser, Permission.QUALIFICATION_VIEW)) {
				redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Access deniled");
				return "redirect:/";// access deniled page
			}

			if (currentUser.getStoreId() == null) {
				redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Store not assigned");
				return "redirect:/";// store not assigned page
			}

			/* current user checking end */

			modelMap.put(StringConstants.QUALIFICATION_LIST,
					qualificationApi.list(Status.ACTIVE, currentUser.getStoreId()));

		} catch (Exception e) {

			LoggerUtil.logException(this.getClass(), e);
			return "redirect:/500";
		}

		return "qualification/list";
	}

	@GetMapping(value = "/add")
	@PreAuthorize("hasAnyRole('ROLE_SUPERADMINISTRATOR','ROLE_ADMINISTRATOR','ROLE_USER,ROLE_AUTHENTICATED')")
	public String add(ModelMap modelMap, RedirectAttributes redirectAttributes) {

		try {

			/* current user checking start */
			InvUserDTO currentUser = AuthenticationUtil.getCurrentUser(userApi);

			if (currentUser.getUserauthority().contains(Authorities.USER)
					& !AuthenticationUtil.checkPermission(currentUser, Permission.QUALIFICATION_CREATE)) {
				redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Access deniled");
				return "redirect:/";// access deniled page
			}

			if (currentUser.getStoreId() == null) {
				redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Store not assigned");
				return "redirect:/";// store not assigned page
			}

			/* current user checking end */

		} catch (Exception e) {

			LoggerUtil.logException(this.getClass(), e);
			return "redirect:/500";
		}

		return "qualification/add";
	}

	@PostMapping(value = "/save")
	@PreAuthorize("hasAnyRole('ROLE_SUPERADMINISTRATOR','ROLE_ADMINISTRATOR','ROLE_USER,ROLE_AUTHENTICATED')")
	public String save(@ModelAttribute("qualification") QualificationLevelDTO qualificationLevelDTO, ModelMap modelMap,
			RedirectAttributes redirectAttributes) {

		try {

			/* current user checking start */
			InvUserDTO currentUser = AuthenticationUtil.getCurrentUser(userApi);

			if (currentUser.getUserauthority().contains(Authorities.USER)
					& !AuthenticationUtil.checkPermission(currentUser, Permission.QUALIFICATION_CREATE)) {
				redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Access deniled");
				return "redirect:/";// access deniled page
			}

			if (currentUser.getStoreId() == null) {
				redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Store not assigned");
				return "redirect:/";// store not assigned page
			}

			/* current user checking end */

			if (qualificationLevelDTO == null) {
				redirectAttributes.addFlashAttribute(StringConstants.ERROR, "please fill the form");

				return "redirect:/qualification/add";
			}

			synchronized (this) {
				qualificationLevelDTO.setOwnerId(currentUser.getStoreId());
				qualificationApi.save(qualificationLevelDTO);
				redirectAttributes.addFlashAttribute(StringConstants.MESSAGE, "qualification saved successfully");
			}
		} catch (Exception e) {

			LoggerUtil.logException(this.getClass(), e);
			return "redirect:/500";
		}

		return "redirect:/qualification/list";
	}
}
