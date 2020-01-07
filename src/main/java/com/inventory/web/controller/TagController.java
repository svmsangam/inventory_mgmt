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

import com.inventory.core.api.iapi.ITagInfoApi;
import com.inventory.core.api.iapi.IUserApi;
import com.inventory.core.model.dto.InvUserDTO;
import com.inventory.core.model.dto.TagInfoDTO;
import com.inventory.core.model.enumconstant.Permission;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.util.Authorities;
import com.inventory.core.validation.TagInfoValidation;
import com.inventory.web.error.TagInfoError;
import com.inventory.web.util.AuthenticationUtil;
import com.inventory.web.util.LoggerUtil;
import com.inventory.web.util.StringConstants;
import com.inventory.web.util.UIUtil;

@Controller
@RequestMapping("/tag")
public class TagController {

	@Autowired
	private ITagInfoApi tagInfoApi;

	@Autowired
	private IUserApi userApi;

	@Autowired
	private TagInfoValidation tagInfoValidation;

	@GetMapping(value = "/list")
	@PreAuthorize("hasAnyRole('ROLE_SUPERADMINISTRATOR','ROLE_ADMINISTRATOR','ROLE_USER,ROLE_AUTHENTICATED')")
	public String list(ModelMap modelMap, RedirectAttributes redirectAttributes, HttpServletRequest request,
			HttpServletResponse response) {

		try {

			/* current user checking start */
			InvUserDTO currentUser = AuthenticationUtil.getCurrentUser(userApi);

			if (currentUser.getUserauthority().contains(Authorities.USER)
					& !AuthenticationUtil.checkPermission(currentUser, Permission.TAG_VIEW)) {
				redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Access deniled");
				return "redirect:/";// access deniled page
			}

			if (currentUser.getStoreId() == null) {
				redirectAttributes.addFlashAttribute(StringConstants.INFO, UIUtil.addStoreMessage());
				return "redirect:/store/list";// store not assigned page
			}

			/* current user checking end */

			modelMap.put(StringConstants.TAG_LIST, tagInfoApi.list(Status.ACTIVE, currentUser.getStoreId()));

		} catch (Exception e) {

			LoggerUtil.logException(this.getClass(), e);
			return "redirect:/500";
		}

		return "tag/list";
	}

	@GetMapping(value = "/add")
	@PreAuthorize("hasAnyRole('ROLE_SUPERADMINISTRATOR','ROLE_ADMINISTRATOR','ROLE_USER,ROLE_AUTHENTICATED')")
	public String add(RedirectAttributes redirectAttributes) {

		try {

			/* current user checking start */
			InvUserDTO currentUser = AuthenticationUtil.getCurrentUser(userApi);

			if (currentUser.getUserauthority().contains(Authorities.USER)
					& !AuthenticationUtil.checkPermission(currentUser, Permission.TAG_CREATE)) {
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

		return "tag/add";
	}

	@PostMapping(value = "/save")
	@PreAuthorize("hasAnyRole('ROLE_SUPERADMINISTRATOR','ROLE_ADMINISTRATOR','ROLE_USER,ROLE_AUTHENTICATED')")
	public String save(@ModelAttribute("tag") TagInfoDTO tagInfoDTO, BindingResult bindingResult, ModelMap modelMap,
			RedirectAttributes redirectAttributes) {

		try {

			/* current user checking start */
			InvUserDTO currentUser = AuthenticationUtil.getCurrentUser(userApi);
			if (currentUser.getUserauthority().contains(Authorities.USER)
					& !AuthenticationUtil.checkPermission(currentUser, Permission.TAG_CREATE)) {
				redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Access deniled");
				return "redirect:/";// access deniled page
			}

			if (currentUser.getStoreId() == null) {
				redirectAttributes.addFlashAttribute(StringConstants.INFO, UIUtil.addStoreMessage());
				return "redirect:/store/list";// store not assigned page
			}

			/* current user checking end */

			if (tagInfoDTO == null) {
				redirectAttributes.addFlashAttribute(StringConstants.ERROR, "bad request");
				return "redirect:/tag/add";
			}

			synchronized (this.getClass()) {
				tagInfoDTO.setStoreInfoId(currentUser.getStoreId());
				tagInfoDTO.setCreatedById(currentUser.getUserId());

				TagInfoError error = tagInfoValidation.onSave(tagInfoDTO, bindingResult);

				if (!error.isValid()) {
					modelMap.put(StringConstants.TAG_ERROR, error);
					modelMap.put(StringConstants.TAG, tagInfoDTO);
					System.out.println("validation failed");
					return "tag/add";
				}

				tagInfoApi.save(tagInfoDTO);

				redirectAttributes.addFlashAttribute(StringConstants.MESSAGE, "tag saved successfully");

			}

		} catch (Exception e) {
			LoggerUtil.logException(this.getClass(), e);
			return "redirect:/500";
		}

		return "redirect:/tag/list";
	}

	@GetMapping(value = "/edit")
	@PreAuthorize("hasAnyRole('ROLE_SUPERADMINISTRATOR','ROLE_ADMINISTRATOR','ROLE_USER,ROLE_AUTHENTICATED')")
	public String edit(@RequestParam("tagId") Long tagId, ModelMap modelMap, RedirectAttributes redirectAttributes,
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

			TagInfoDTO tagInfoDTO = tagInfoApi.getTagByIdAndStatusAndStore(tagId, Status.ACTIVE,
					currentUser.getStoreId());
			modelMap.put("tag", tagInfoDTO);
			/* current user checking end */
		} catch (Exception e) {

			LoggerUtil.logException(this.getClass(), e);
			return "redirect:/500";
		}

		return "/tag/edit";

	}

	@PostMapping(value = "/update")
	@PreAuthorize("hasAnyRole('ROLE_SUPERADMINISTRATOR','ROLE_ADMINISTRATOR','ROLE_USER,ROLE_AUTHENTICATED')")
	public String update(@ModelAttribute("tag") TagInfoDTO tagInfoDTO, BindingResult bindingResult, ModelMap modelMap,
			RedirectAttributes redirectAttributes) {

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

			if (tagInfoDTO == null) {
				redirectAttributes.addFlashAttribute(StringConstants.ERROR, "bad request");
				return "redirect:/tag/add";
			}

			synchronized (this.getClass()) {

				tagInfoDTO.setStoreInfoId(currentUser.getStoreId());
				tagInfoDTO.setCreatedById(currentUser.getUserId());

				TagInfoError error = tagInfoValidation.onUpdate(tagInfoDTO, bindingResult);

				if (!error.isValid()) {
					modelMap.put(StringConstants.TAG_ERROR, error);
					modelMap.put(StringConstants.TAG, tagInfoDTO);
					return "tag/add";
				}

				tagInfoApi.update(tagInfoDTO);

				redirectAttributes.addFlashAttribute(StringConstants.MESSAGE, "tag updated successfully");

			}

		} catch (Exception e) {

			LoggerUtil.logException(this.getClass(), e);
			return "redirect:/500";
		}
		return "redirect:/tag/list";
	}
}
