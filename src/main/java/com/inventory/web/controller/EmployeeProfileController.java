package com.inventory.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.inventory.core.api.iapi.ICityInfoApi;
import com.inventory.core.api.iapi.IDesignationInfoApi;
import com.inventory.core.api.iapi.IEmployeeProfileApi;
import com.inventory.core.api.iapi.IUserApi;
import com.inventory.core.model.dto.EmployeeProfileDTO;
import com.inventory.core.model.dto.InvUserDTO;
import com.inventory.core.model.enumconstant.EmployeeStatus;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.model.enumconstant.UserType;
import com.inventory.web.util.AuthenticationUtil;
import com.inventory.web.util.LoggerUtil;
import com.inventory.web.util.StringConstants;

/**
 * Created by dhiraj on 12/19/17.
 */
@Controller
@RequestMapping("profile")
public class EmployeeProfileController {

	@Autowired
	private IUserApi userApi;

	@Autowired
	private IEmployeeProfileApi profileApi;

	@Autowired
	private ICityInfoApi cityInfoApi;

	@Autowired
	private IDesignationInfoApi designationInfoApi;

	@GetMapping(value = "/")
	public String index(RedirectAttributes redirectAttributes) {

		return "redirect:/profile/list";
	}

	@GetMapping(value = "/image/upload/{profileId}")
	@PreAuthorize("hasAnyRole('ROLE_SUPERADMINISTRATOR','ROLE_ADMINISTRATOR')")
	public String imageUpload(@PathVariable("profileId") Long profileId, RedirectAttributes redirectAttributes,
			ModelMap modelMap) {

		try {

			/* current user checking start */
			InvUserDTO currentUser = AuthenticationUtil.getCurrentUser(userApi);

			if (currentUser.getStoreId() == null) {
				redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Store not assigned");
				return "redirect:/";// store not assigned page
			}

			/* current user checking end */

			if (profileId == null) {
				redirectAttributes.addFlashAttribute(StringConstants.ERROR, "invalid request");

				return "redirect:/profile/list";
			}

			if (profileId < 0) {
				redirectAttributes.addFlashAttribute(StringConstants.ERROR, "invalid request");

				return "redirect:/profile/list";
			}

			EmployeeProfileDTO employeeProfileDTO = profileApi.show(profileId, Status.ACTIVE, currentUser.getStoreId());

			if (employeeProfileDTO == null) {
				redirectAttributes.addFlashAttribute(StringConstants.ERROR, "employee record not found");
				return "redirect:/profile/list";
			}

		} catch (Exception e) {
			LoggerUtil.logException(this.getClass(), e);
			return "redirect:/500";
		}

		modelMap.put(StringConstants.EMPLOYEE_PROFILE, profileId);

		return "employee/upload";

	}

	@GetMapping(value = "/list")
	@PreAuthorize("hasAnyRole('ROLE_SUPERADMINISTRATOR','ROLE_ADMINISTRATOR')")
	public String list(RedirectAttributes redirectAttributes, ModelMap modelMap) {

		try {

			/* current user checking start */
			InvUserDTO currentUser = AuthenticationUtil.getCurrentUser(userApi);

			if (currentUser.getStoreId() == null) {
				redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Store not assigned");
				return "redirect:/";// store not assigned page
			}

			/* current user checking end */

			List<EmployeeProfileDTO> employeeProfileDTOList = null;

			if (currentUser.getUserType().equals(UserType.SUPERADMIN)) {
				employeeProfileDTOList = profileApi.listForSuperAdmin(Status.ACTIVE, currentUser.getUserId());
			} else {
				employeeProfileDTOList = profileApi.list(Status.ACTIVE, currentUser.getStoreId());
			}

			modelMap.put(StringConstants.EMPLOYEE_PROFILE_LIST, employeeProfileDTOList);

		} catch (Exception e) {
			LoggerUtil.logException(this.getClass(), e);
			return "redirect:/500";
		}

		return "employee/list";
	}

	@GetMapping(value = "/add")
	@PreAuthorize("hasAnyRole('ROLE_SUPERADMINISTRATOR','ROLE_ADMINISTRATOR')")
	public String add(RedirectAttributes redirectAttributes, ModelMap modelMap) {

		/* current user checking start */
		InvUserDTO currentUser = AuthenticationUtil.getCurrentUser(userApi);

		if (currentUser.getStoreId() == null) {
			redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Store not assigned");
			return "redirect:/";// store not assigned page
		}

		/* current user checking end */

		modelMap.put(StringConstants.CITY_LIST, cityInfoApi.list());

		modelMap.put(StringConstants.DESIGNATION_LIST,
				designationInfoApi.list(Status.ACTIVE, currentUser.getStoreId()));
		modelMap.put(StringConstants.EMPLOYEE_STATUS_LIST, EmployeeStatus.values());

		return "employee/add";
	}

	@PostMapping(value = "/save")
	@PreAuthorize("hasAnyRole('ROLE_SUPERADMINISTRATOR','ROLE_ADMINISTRATOR')")
	public String save(@ModelAttribute("employee") EmployeeProfileDTO profileDTO, BindingResult result,
			RedirectAttributes redirectAttributes, ModelMap modelMap) {

		try {

			/* current user checking start */
			InvUserDTO currentUser = AuthenticationUtil.getCurrentUser(userApi);

			if (currentUser.getStoreId() == null) {
				redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Store not assigned");
				return "redirect:/";// store not assigned page
			}

			/* current user checking end */

			if (profileDTO == null) {
				redirectAttributes.addFlashAttribute(StringConstants.ERROR, "bad request");
				return "redirect:/profile/add";// store not assigned page
			}

			profileDTO.setCreatedById(currentUser.getUserId());
			profileDTO.setOwnerId(currentUser.getStoreId());

			profileDTO = profileApi.save(profileDTO);

			redirectAttributes.addFlashAttribute(StringConstants.MESSAGE, "employee successfully saved");

		} catch (Exception e) {
			e.printStackTrace();
			LoggerUtil.logException(this.getClass(), e);
			redirectAttributes.addFlashAttribute(StringConstants.ERROR, "internal server error");
			return "redirect:/500";// store not assigned page
		}

		return "redirect:/profile/list";// store not assigned page
	}

	@GetMapping(value = "/show/{employeeProfileId}")
	@PreAuthorize("hasAnyRole('ROLE_SUPERADMINISTRATOR','ROLE_ADMINISTRATOR')")
	public String show(@PathVariable("employeeProfileId") Long employeeProfileId, RedirectAttributes redirectAttributes,
			ModelMap modelMap) {

		try {
			/* current user checking start */
			InvUserDTO currentUser = AuthenticationUtil.getCurrentUser(userApi);

			if (currentUser.getStoreId() == null) {
				redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Store not assigned");
				return "redirect:/";// store not assigned page
			}

			/* current user checking end */

			EmployeeProfileDTO employeeProfileDTO = null;

			if (currentUser.getUserType().equals(UserType.SUPERADMIN)) {
				employeeProfileDTO = profileApi.showForSuperAdmin(employeeProfileId, Status.ACTIVE,
						currentUser.getUserId());
			} else {

				employeeProfileDTO = profileApi.show(employeeProfileId, Status.ACTIVE, currentUser.getStoreId());
			}
			modelMap.put(StringConstants.EMPLOYEE_PROFILE, employeeProfileDTO);

		} catch (Exception e) {
			LoggerUtil.logException(this.getClass(), e);
			return "redirect:/500";
		}

		return "employee/show";
	}
}
