package com.inventory.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.inventory.core.api.iapi.IFiscalYearInfoApi;
import com.inventory.core.api.iapi.IUserApi;
import com.inventory.core.model.dto.FiscalYearInfoDTO;
import com.inventory.core.model.dto.InvUserDTO;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.web.util.AuthenticationUtil;
import com.inventory.web.util.LoggerUtil;
import com.inventory.web.util.StringConstants;
import com.inventory.web.util.UIUtil;

/**
 * Created by dhiraj on 12/24/17.
 */
@Controller
@RequestMapping("fiscalyear")
public class FiscalYearInfoController {

	@Autowired
	private IUserApi userApi;

	@Autowired
	private IFiscalYearInfoApi fiscalYearInfoApi;

	@GetMapping(value = "/")
	@PreAuthorize("hasAnyRole('ROLE_SUPERADMINISTRATOR','ROLE_ADMINISTRATOR')")
	public String index() {

		return "redirect:/fiscalyear/list";
	}

	@GetMapping(value = "/list")
	@PreAuthorize("hasAnyRole('ROLE_SUPERADMINISTRATOR','ROLE_ADMINISTRATOR')")
	public String list(ModelMap modelMap, RedirectAttributes redirectAttributes, HttpServletRequest request,
			HttpServletResponse response) {

		try {
			/* current user checking start */
			InvUserDTO currentUser = AuthenticationUtil.getCurrentUser(userApi);

			if (currentUser.getStoreId() == null) {
				redirectAttributes.addFlashAttribute(StringConstants.INFO, UIUtil.addStoreMessage());
				return "redirect:/store/list";// store not assigned page
			}

			/* current user checking end */

			modelMap.put(StringConstants.FISCAL_YEAR_LIST,
					fiscalYearInfoApi.list(Status.ACTIVE, currentUser.getStoreId(), 0, 100));

		} catch (Exception e) {

			LoggerUtil.logException(this.getClass(), e);
			return "redirect:/500";
		}

		return "fiscalyear/list";
	}

	@GetMapping(value = "/add")
	@PreAuthorize("hasAnyRole('ROLE_SUPERADMINISTRATOR','ROLE_ADMINISTRATOR')")
	public String add(ModelMap modelMap, RedirectAttributes redirectAttributes, HttpServletRequest request,
			HttpServletResponse response) {

		try {
			/* current user checking start */
			InvUserDTO currentUser = AuthenticationUtil.getCurrentUser(userApi);

			if (currentUser.getStoreId() == null) {
				redirectAttributes.addFlashAttribute(StringConstants.INFO, UIUtil.addStoreMessage());
				return "redirect:/store/list";// store not assigned page
			}

			/* current user checking end */

		} catch (Exception e) {

			LoggerUtil.logException(this.getClass(), e);
			return "redirect:/500";
		}

		return "fiscalyear/add";
	}

	@PostMapping(value = "/save")
	@PreAuthorize("hasAnyRole('ROLE_SUPERADMINISTRATOR','ROLE_ADMINISTRATOR')")
	public String save(FiscalYearInfoDTO fiscalYearInfoDTO, ModelMap modelMap,
			RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response) {

		try {

			/* current user checking start */
			InvUserDTO currentUser = AuthenticationUtil.getCurrentUser(userApi);

			if (currentUser.getStoreId() == null) {
				redirectAttributes.addFlashAttribute(StringConstants.INFO, UIUtil.addStoreMessage());
				return "redirect:/store/list";// store not assigned page
			}

			/* current user checking end */

			if (fiscalYearInfoDTO == null) {

				redirectAttributes.addFlashAttribute(StringConstants.ERROR, "bad request");

				return "redirect:/fiscalyear/add";
			}

			fiscalYearInfoDTO.setStoreInfoId(currentUser.getStoreId());

			fiscalYearInfoApi.save(fiscalYearInfoDTO);

			redirectAttributes.addFlashAttribute(StringConstants.MESSAGE, "fiscal year successfully saved");

		} catch (Exception e) {

			LoggerUtil.logException(this.getClass(), e);

			return "redirect:/500";
		}

		return "redirect:/fiscalyear/list";
	}

}
