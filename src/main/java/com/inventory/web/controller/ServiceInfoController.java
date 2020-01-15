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

import com.inventory.core.api.iapi.IServiceInfoApi;
import com.inventory.core.api.iapi.IUserApi;
import com.inventory.core.model.dto.InvUserDTO;
import com.inventory.core.model.dto.ServiceDTO;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.web.util.AuthenticationUtil;
import com.inventory.web.util.LoggerUtil;
import com.inventory.web.util.StringConstants;

/**
 * Created by dhiraj on 1/25/18.
 */
@Controller
@RequestMapping("service")
public class ServiceInfoController {

	@Autowired
	private IUserApi userApi;

	@Autowired
	private IServiceInfoApi serviceInfoApi;

	@GetMapping(value = "/list")
	@PreAuthorize("hasRole('ROLE_SYSTEM')")
	public String list(ModelMap modelMap, RedirectAttributes redirectAttributes) {

		try {

			/* current user checking start */
			InvUserDTO currentUser = AuthenticationUtil.getCurrentUser(userApi);

			/* current user checking end */

			modelMap.put(StringConstants.SERVICE_LIST, serviceInfoApi.list(Status.ACTIVE));

		} catch (Exception e) {

			LoggerUtil.logException(this.getClass(), e);
			return "redirect:/500";
		}

		return "serviceInfo/list";
	}

	@GetMapping(value = "/add")
	@PreAuthorize("hasRole('ROLE_SYSTEM')")
	public String add(ModelMap modelMap, RedirectAttributes redirectAttributes) {

		try {

			/* current user checking start */
			InvUserDTO currentUser = AuthenticationUtil.getCurrentUser(userApi);

			/* current user checking end */

		} catch (Exception e) {

			LoggerUtil.logException(this.getClass(), e);
			return "redirect:/500";
		}

		return "serviceInfo/add";
	}

	@PostMapping(value = "/save")
	@PreAuthorize("hasRole('ROLE_SYSTEM')")
	public String save(@ModelAttribute("service") ServiceDTO serviceDTO, ModelMap modelMap,
			RedirectAttributes redirectAttributes) {

		try {

			/* current user checking start */
			InvUserDTO currentUser = AuthenticationUtil.getCurrentUser(userApi);

			/* current user checking end */

			synchronized (this) {
				serviceInfoApi.save(serviceDTO);
				redirectAttributes.addFlashAttribute(StringConstants.MESSAGE, "service saved successfully");
			}

		} catch (Exception e) {

			LoggerUtil.logException(this.getClass(), e);
			return "redirect:/500";
		}

		return "redirect:/service/list";
	}
}
