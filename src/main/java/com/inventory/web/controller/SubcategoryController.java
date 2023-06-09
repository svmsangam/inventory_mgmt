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

import com.inventory.core.api.iapi.ISubcategoryInfoApi;
import com.inventory.core.api.iapi.IUserApi;
import com.inventory.core.model.dto.InvUserDTO;
import com.inventory.core.model.dto.SubCategoryInfoDTO;
import com.inventory.core.model.enumconstant.Permission;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.model.liteentity.CategoryDomain;
import com.inventory.core.util.Authorities;
import com.inventory.core.validation.SubCategoryInfoValidation;
import com.inventory.web.error.SubCategoryInfoError;
import com.inventory.web.util.AuthenticationUtil;
import com.inventory.web.util.LoggerUtil;
import com.inventory.web.util.StringConstants;
import com.inventory.web.util.UIUtil;

@Controller
@RequestMapping("/category")
public class SubcategoryController {

	@Autowired
	private IUserApi userApi;

	@Autowired
	private ISubcategoryInfoApi subcategoryInfoApi;

	@Autowired
	private SubCategoryInfoValidation subCategoryInfoValidation;

	@GetMapping(value = "/")
	public String index(RedirectAttributes redirectAttributes) {

		return "redirect:/category/list";
	}

	@GetMapping(value = "/list")
	@PreAuthorize("hasAnyRole('ROLE_SUPERADMINISTRATOR','ROLE_ADMINISTRATOR','ROLE_USER,ROLE_AUTHENTICATED')")
	public String list(ModelMap modelMap, RedirectAttributes redirectAttributes) {

		try {

			/* current user checking start */
			InvUserDTO currentUser = AuthenticationUtil.getCurrentUser(userApi);

			if (currentUser.getUserauthority().contains(Authorities.USER)
					& !AuthenticationUtil.checkPermission(currentUser, Permission.SUBCATEGORY_VIEW)) {
				redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Access deniled");
				return "redirect:/";// access deniled page
			}

			if (currentUser.getStoreId() == null) {
				redirectAttributes.addFlashAttribute(StringConstants.INFO, UIUtil.addStoreMessage());
				return "redirect:/store/list";// store not assigned page
			}

			/* current user checking end */

			modelMap.put(StringConstants.SUBCATEGORY_LIST,
					subcategoryInfoApi.getTree(Status.ACTIVE, currentUser.getStoreId()));

		} catch (Exception e) {

			LoggerUtil.logException(this.getClass(), e);
			return "redirect:/500";
		}

		return "subcategory/list";
	}

	@GetMapping(value = "/add")
	@PreAuthorize("hasAnyRole('ROLE_SUPERADMINISTRATOR','ROLE_ADMINISTRATOR','ROLE_USER,ROLE_AUTHENTICATED')")
	public String add(RedirectAttributes redirectAttributes, ModelMap modelMap) {

		try {

			/* current user checking start */
			InvUserDTO currentUser = AuthenticationUtil.getCurrentUser(userApi);

			if (currentUser.getUserauthority().contains(Authorities.USER)
					& !AuthenticationUtil.checkPermission(currentUser, Permission.SUBCATEGORY_CREATE)) {
				redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Access deniled");
				return "redirect:/";// access deniled page
			}

			if (currentUser.getStoreId() == null) {
				redirectAttributes.addFlashAttribute(StringConstants.INFO, UIUtil.addStoreMessage());
				return "redirect:/store/list";// store not assigned page
			}
			/* current user checking end */

			modelMap.put(StringConstants.CATEGORY_LIST,
					subcategoryInfoApi.getTree(Status.ACTIVE, currentUser.getStoreId()));

		} catch (Exception e) {

			LoggerUtil.logException(this.getClass(), e);
			return "redirect:/500";
		}

		return "subcategory/add";
	}

	@PostMapping(value = "/save")
	@PreAuthorize("hasAnyRole('ROLE_SUPERADMINISTRATOR','ROLE_ADMINISTRATOR','ROLE_USER,ROLE_AUTHENTICATED')")
	public String save(@ModelAttribute("subcategory") SubCategoryInfoDTO subCategoryInfoDTO,
			BindingResult bindingResult, ModelMap modelMap, RedirectAttributes redirectAttributes) {

		try {

			/* current user checking start */
			InvUserDTO currentUser = AuthenticationUtil.getCurrentUser(userApi);

			if (currentUser.getUserauthority().contains(Authorities.USER)
					& !AuthenticationUtil.checkPermission(currentUser, Permission.SUBCATEGORY_CREATE)) {
				redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Access deniled");
				return "redirect:/";// access deniled page
			}

			if (currentUser.getStoreId() == null) {
				redirectAttributes.addFlashAttribute(StringConstants.INFO, UIUtil.addStoreMessage());
				return "redirect:/store/list";// store not assigned page
			}

			/* current user checking end */

			if (subCategoryInfoDTO == null) {
				redirectAttributes.addFlashAttribute(StringConstants.ERROR, "bad request");
				return "redirect:/category/add";
			}

			synchronized (this.getClass()) {
				subCategoryInfoDTO.setStoreInfoId(currentUser.getStoreId());
				subCategoryInfoDTO.setCreatedById(currentUser.getUserId());

				SubCategoryInfoError error = subCategoryInfoValidation.onSave(subCategoryInfoDTO, bindingResult);

				if (!error.isValid()) {
					modelMap.put(StringConstants.SUBCATEGORY_ERROR, error);
					modelMap.put(StringConstants.SUBCATEGORY, subCategoryInfoDTO);
					modelMap.put(StringConstants.CATEGORY_LIST,
							subcategoryInfoApi.getTree(Status.ACTIVE, currentUser.getStoreId()));
					return "subcategory/add";
				}

				subcategoryInfoApi.save(subCategoryInfoDTO);
			}

		} catch (Exception e) {
			LoggerUtil.logException(this.getClass(), e);
			return "redirect:/500";
		}

		return "redirect:/category/list";
	}

	@GetMapping(value = "/edit")
	@PreAuthorize("hasAnyRole('ROLE_SUPERADMINISTRATOR','ROLE_ADMINISTRATOR','ROLE_USER,ROLE_AUTHENTICATED')")
	public String edit(@RequestParam("subCategoryId") long subcategoryId, ModelMap modelMap,
			RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response) {

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

			CategoryDomain subCategoryInfoDTO = subcategoryInfoApi.showLite(subcategoryId, currentUser.getStoreId(),
					Status.ACTIVE);

			if (subCategoryInfoDTO == null) {
				redirectAttributes.addFlashAttribute(StringConstants.ERROR, "category not found");
				return "redirect:/category/list";// store not assigned page
			}

			modelMap.put(StringConstants.SUBCATEGORY, subCategoryInfoDTO);
			modelMap.put(StringConstants.CATEGORY_LIST,
					subcategoryInfoApi.getTree(Status.ACTIVE, currentUser.getStoreId()));
			/* current user checking end */
		} catch (Exception e) {

			LoggerUtil.logException(this.getClass(), e);
			return "redirect:/500";
		}

		return "/subcategory/edit";
	}

	@PostMapping(value = "/update")
	@PreAuthorize("hasAnyRole('ROLE_SUPERADMINISTRATOR','ROLE_ADMINISTRATOR','ROLE_USER,ROLE_AUTHENTICATED')")
	public String update(@ModelAttribute("subCategory") SubCategoryInfoDTO subCategoryInfoDTO, ModelMap modelMap,
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

			if (subCategoryInfoDTO == null) {
				redirectAttributes.addFlashAttribute(StringConstants.ERROR, "bad request");
				return "redirect:/category/list";
			}

			synchronized (this.getClass()) {

				SubCategoryInfoError error = subCategoryInfoValidation.onUpdate(subCategoryInfoDTO, bindingResult,
						currentUser.getStoreId());

				if (!error.isValid()) {
					modelMap.put(StringConstants.SUBCATEGORY_ERROR, error);
					modelMap.put(StringConstants.SUBCATEGORY, subCategoryInfoDTO);
					modelMap.put(StringConstants.CATEGORY_LIST,
							subcategoryInfoApi.getTree(Status.ACTIVE, currentUser.getStoreId()));

					return "subcategory/edit";
				}

				subcategoryInfoApi.update(subCategoryInfoDTO);
			}

		} catch (Exception e) {

			LoggerUtil.logException(this.getClass(), e);
			return "redirect:/500";
		}

		return "redirect:/category/list";
	}

	@GetMapping(value = "/show")
	public String show(@ModelAttribute("subCategory") SubCategoryInfoDTO subCategoryInfoDTO, ModelMap modelMap,
			BindingResult bindingResult, RedirectAttributes redirectAttributes) {

		return "redirect:/category/list";
	}
}
