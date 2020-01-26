package com.inventory.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.inventory.core.api.iapi.ICityInfoApi;
import com.inventory.core.api.iapi.IStateInfoApi;
import com.inventory.core.api.iapi.IUserApi;
import com.inventory.core.validation.CityInfoValidation;
import com.inventory.web.util.LoggerUtil;
import com.inventory.web.util.StringConstants;

@Controller
@RequestMapping("/city")
public class CityController {

	@Autowired
	private ICityInfoApi cityInfoApi;

	@Autowired
	private IStateInfoApi stateInfoApi;

	@Autowired
	private CityInfoValidation cityInfoValidation;

	@Autowired
	private IUserApi userApi;

	private Logger logger = LoggerFactory.getLogger(CityController.class);

	@GetMapping(value = "/list")
	@PreAuthorize("isAuthenticated()")
	public String listCity(ModelMap modelMap, RedirectAttributes redirectAttributes, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			modelMap.put(StringConstants.CITY_LIST, cityInfoApi.list());

			return "city/listCities";

		} catch (Exception e) {
			LoggerUtil.logException(this.getClass(), e);
			return "redirect:/";
		}
	}

}
