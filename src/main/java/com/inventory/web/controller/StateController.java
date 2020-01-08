package com.inventory.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.inventory.core.api.iapi.ICountryInfoApi;
import com.inventory.core.api.iapi.IStateInfoApi;
import com.inventory.core.api.iapi.IUserApi;
import com.inventory.core.model.dto.StateInfoDTO;
import com.inventory.core.validation.StateValidation;
import com.inventory.web.error.StateError;
import com.inventory.web.util.LoggerUtil;
import com.inventory.web.util.StringConstants;

@Controller
@RequestMapping("/state")
public class StateController {

	@Autowired
	private IStateInfoApi stateService;

	@Autowired
	private ICountryInfoApi countryService;

	@Autowired
	private IUserApi userApi;

	@Autowired
	private StateValidation stateValidation;

	@RequestMapping(method = RequestMethod.GET, value = "/list")
	@PreAuthorize("isAuthenticated()")
	public String listStates(ModelMap modelMap, RedirectAttributes redirectAttributes) {
		try {
			modelMap.put(StringConstants.STATE_LIST, stateService.list());
			return "state/listStates";
		} catch (Exception e) {
			LoggerUtil.logException(this.getClass(), e);
			return "redirect:/";
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/delete")
	@PreAuthorize("isAuthenticated()")
	public String removeState(ModelMap modelMap, @RequestParam("id") long id, RedirectAttributes redirectAttributes) {
		try {
			stateService.delete(id);
			return "redirect:/state/list";

		} catch (Exception e) {
			LoggerUtil.logException(this.getClass(), e);
			return "redirect:/";
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/add")
	@PreAuthorize("isAuthenticated()")
	public String addState(ModelMap modelMap, RedirectAttributes redirectAttributes) {
		try {
			modelMap.put(StringConstants.COUNTRY_LIST, countryService.list());
			return "state/addState";
		} catch (Exception e) {
			LoggerUtil.logException(this.getClass(), e);
			return "redirect:/";
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/save")
	@PreAuthorize("isAuthenticated()")
	public String addState(ModelMap modelMap, @ModelAttribute("stateDto") StateInfoDTO stateDto,
			RedirectAttributes redirectAttributes) {
		try {
			StateError stateError = new StateError();
			stateError = stateValidation.stateValidationOnSave(stateDto);
			if (stateError.isValid()) {
				stateService.save(stateDto);
				return "redirect:/state/list";
			} else {
				modelMap.put(StringConstants.ERROR, stateError);
				modelMap.put(StringConstants.COUNTRY_LIST, countryService.list());
				modelMap.put(StringConstants.STATE, stateDto);
				return "state/addState";
			}

		} catch (Exception e) {
			LoggerUtil.logException(this.getClass(), e);
			return "redirect:/";
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/edit")
	@PreAuthorize("isAuthenticated()")
	public String editState(ModelMap modelMap, @RequestParam("id") long id, RedirectAttributes redirectAttributes) {
		try {
			StateInfoDTO stateDto = stateService.show(id);
			modelMap.put(StringConstants.COUNTRY_LIST, countryService.list());
			modelMap.put(StringConstants.STATE, stateDto);
			return "state/editState";

		} catch (Exception e) {
			LoggerUtil.logException(this.getClass(), e);
			return "redirect:/";
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/update")
	@PreAuthorize("isAuthenticated()")
	public String updateState(ModelMap modelMap, @ModelAttribute("stateDto") StateInfoDTO stateDto,
			RedirectAttributes redirectAttributes) {
		try {
			StateError stateError = new StateError();
			stateError = stateValidation.stateValidationOnSave(stateDto);
			if (stateError.isValid()) {
				stateService.update(stateDto);
				return "redirect:/state/list";
			} else {
				modelMap.put(StringConstants.ERROR, stateError);
				modelMap.put(StringConstants.COUNTRY_LIST, countryService.list());
				modelMap.put(StringConstants.STATE, stateDto);
				return "state/editState";
			}

		} catch (Exception e) {
			LoggerUtil.logException(this.getClass(), e);
			return "redirect:/";
		}
	}

}
