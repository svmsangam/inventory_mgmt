package com.inventory.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.inventory.core.api.iapi.INotificationApi;
import com.inventory.core.api.iapi.IUnitInfoApi;
import com.inventory.core.api.iapi.IUserApi;
import com.inventory.core.model.dto.InvUserDTO;
import com.inventory.core.model.dto.UnitInfoDTO;
import com.inventory.core.model.enumconstant.Permission;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.util.Authorities;
import com.inventory.core.validation.UnitInfoValidation;
import com.inventory.web.error.UnitInfoError;
import com.inventory.web.util.AuthenticationUtil;
import com.inventory.web.util.LoggerUtil;
import com.inventory.web.util.StringConstants;
import com.inventory.web.util.UIUtil;

/*
 * Created by dhiraj on 6/26/17.

**/

@Controller
@RequestMapping("/unit")
public class UnitController {

	@Autowired
	private IUserApi userApi;

	@Autowired
	private IUnitInfoApi unitInfoApi;

	@Autowired
	private UnitInfoValidation unitInfoValidation;

	@Autowired
	private INotificationApi notificationApi;

	@GetMapping(value = "/list")
	@PreAuthorize("hasAnyRole('ROLE_SUPERADMINISTRATOR','ROLE_ADMINISTRATOR','ROLE_USER,ROLE_AUTHENTICATED')")
	public String list(ModelMap modelMap, RedirectAttributes redirectAttributes) {

		try {

			/* current user checking start */
			InvUserDTO currentUser = AuthenticationUtil.getCurrentUser(userApi);

			if (currentUser.getUserauthority().contains(Authorities.USER)
					& !AuthenticationUtil.checkPermission(currentUser, Permission.UNIT_VIEW)) {
				redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Access deniled");
				return "redirect:/";// access deniled page
			}

			if (currentUser.getStoreId() == null) {
				redirectAttributes.addFlashAttribute(StringConstants.INFO, UIUtil.addStoreMessage());
				return "redirect:/store/list";// store not assigned page
			}

			/* current user checking end */

			modelMap.put(StringConstants.UNIT_LIST, unitInfoApi.list(Status.ACTIVE, currentUser.getStoreId()));

		} catch (Exception e) {

			LoggerUtil.logException(this.getClass(), e);
			return "redirect:/500";
		}

		return "unit/list";
	}

	@GetMapping(value = "/add")
	@PreAuthorize("hasAnyRole('ROLE_SUPERADMINISTRATOR','ROLE_ADMINISTRATOR','ROLE_USER,ROLE_AUTHENTICATED')")
	public String add(RedirectAttributes redirectAttributes) {

		try {

			/* current user checking start */
			InvUserDTO currentUser = AuthenticationUtil.getCurrentUser(userApi);

			if (currentUser.getUserauthority().contains(Authorities.USER)
					& !AuthenticationUtil.checkPermission(currentUser, Permission.UNIT_CREATE)) {
				redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Access deniled");
				return "redirect:/";// access deniled page
			}

			if (currentUser.getStoreId() == null) {
				redirectAttributes.addFlashAttribute(StringConstants.INFO, UIUtil.addStoreMessage());
				return "redirect:/store/list";// store not assigned page
			}

			/* current user checking end */
		} catch (Exception e) {

			LoggerUtil.logException(this.getClass(), e);
			return "redirect:/500";
		}

		return "unit/add";
	}

	@PostMapping(value = "/save")
	@PreAuthorize("hasAnyRole('ROLE_SUPERADMINISTRATOR','ROLE_ADMINISTRATOR','ROLE_USER,ROLE_AUTHENTICATED')")
	public String save(@ModelAttribute("unit") UnitInfoDTO unitInfoDTO, BindingResult bindingResult, ModelMap modelMap,
			RedirectAttributes redirectAttributes) {

		try {

			/* current user checking start */
			InvUserDTO currentUser = AuthenticationUtil.getCurrentUser(userApi);

			if (currentUser.getUserauthority().contains(Authorities.USER)
					& !AuthenticationUtil.checkPermission(currentUser, Permission.UNIT_CREATE)) {
				redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Access deniled");
				return "redirect:/";// access deniled page
			}

			if (currentUser.getStoreId() == null) {
				redirectAttributes.addFlashAttribute(StringConstants.INFO, UIUtil.addStoreMessage());
				return "redirect:/store/list";// store not assigned page
			}

			/* current user checking end */

			if (unitInfoDTO == null) {
				redirectAttributes.addFlashAttribute(StringConstants.ERROR, "bad request");
				return "redirect:/unit/add";
			}

			synchronized (this.getClass()) {
				unitInfoDTO.setStoreInfoId(currentUser.getStoreId());
				unitInfoDTO.setCreatedById(currentUser.getUserId());

				UnitInfoError error = unitInfoValidation.onSave(unitInfoDTO, bindingResult);

				if (!error.isValid()) {
					modelMap.put(StringConstants.UNIT_ERROR, error);
					modelMap.put(StringConstants.UNIT, unitInfoDTO);
					return "unit/add";
				}

				unitInfoApi.save(unitInfoDTO);
				redirectAttributes.addFlashAttribute(StringConstants.MESSAGE, "unit saved successfully");
			}

		} catch (Exception e) {

			LoggerUtil.logException(this.getClass(), e);
			return "redirect:/500";
		}

		return "redirect:/unit/list";
	}

	@GetMapping(value = "/edit")
	@PreAuthorize("hasAnyRole('ROLE_SUPERADMINISTRATOR','ROLE_ADMINISTRATOR','ROLE_USER,ROLE_AUTHENTICATED')")
	public String edit(@RequestParam("unitId") long unitId, ModelMap modelMap, RedirectAttributes redirectAttributes,
			HttpServletRequest request, HttpServletResponse response) {

		try {

			/* current user checking start */
			InvUserDTO currentUser = AuthenticationUtil.getCurrentUser(userApi);

			if (currentUser.getUserauthority().contains(Authorities.USER)
					& !AuthenticationUtil.checkPermission(currentUser, Permission.CATEGORY_CREATE)) {
				redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Access deniled");
				return "redirect:/";// access deniled page
			}

			if (currentUser.getStoreId() == null) {
				redirectAttributes.addFlashAttribute(StringConstants.INFO, UIUtil.addStoreMessage());
				return "redirect:/store/list";// store not assigned page
			}

			UnitInfoDTO unitInfoDTO = unitInfoApi.getByIdAndStoreAndStatus(unitId, currentUser.getStoreId(),
					Status.ACTIVE);
			modelMap.put("unit", unitInfoDTO);
			/* current user checking end */
		} catch (Exception e) {

			LoggerUtil.logException(this.getClass(), e);
			return "redirect:/500";
		}

		return "/unit/edit";

	}

	@PostMapping(value = "/update")
	@PreAuthorize("hasAnyRole('ROLE_SUPERADMINISTRATOR','ROLE_ADMINISTRATOR','ROLE_USER,ROLE_AUTHENTICATED')")
	public String update(@ModelAttribute("unit") UnitInfoDTO unitInfoDTO, ModelMap modelMap,
			BindingResult bindingResult, RedirectAttributes redirectAttributes) {

		try {

			/* current user checking start */
			InvUserDTO currentUser = AuthenticationUtil.getCurrentUser(userApi);

			if (currentUser.getUserauthority().contains(Authorities.USER)
					& !AuthenticationUtil.checkPermission(currentUser, Permission.CATEGORY_CREATE)) {
				redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Access deniled");
				return "redirect:/";// access deniled page
			}

			if (currentUser.getStoreId() == null) {
				redirectAttributes.addFlashAttribute(StringConstants.INFO, UIUtil.addStoreMessage());
				return "redirect:/store/list";// store not assigned page
			}

			/* current user checking end */

			if (unitInfoDTO == null) {
				redirectAttributes.addFlashAttribute(StringConstants.ERROR, "bad request");
				return "redirect:/unit/add";
			}

			synchronized (this.getClass()) {

				unitInfoDTO.setStoreInfoId(currentUser.getStoreId());
				unitInfoDTO.setCreatedById(currentUser.getUserId());

				UnitInfoError error = unitInfoValidation.onUpdate(unitInfoDTO, bindingResult);

				if (!error.isValid()) {
					modelMap.put(StringConstants.UNIT_ERROR, error);
					modelMap.put(StringConstants.UNIT, unitInfoDTO);
					return "unit/add";
				}

				unitInfoApi.update(unitInfoDTO);

				redirectAttributes.addFlashAttribute(StringConstants.MESSAGE, "unit updated successfully");
			}

		} catch (Exception e) {

			LoggerUtil.logException(this.getClass(), e);
			return "redirect:/500";
		}
		return "redirect:/unit/list";
	}
}
