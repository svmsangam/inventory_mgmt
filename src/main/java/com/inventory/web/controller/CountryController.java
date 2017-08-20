package com.inventory.web.controller;

import com.inventory.core.api.iapi.ICountryInfoApi;
import com.inventory.core.api.iapi.IUserApi;
import com.inventory.core.model.dto.CountryInfoDTO;
import com.inventory.core.validation.CountryValidation;
import com.inventory.web.error.CountryError;
import com.inventory.web.util.AuthenticationUtil;
import com.inventory.web.util.StringConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
@RequestMapping("/country")
public class CountryController {

	private Logger logger = LoggerFactory.getLogger(CountryController.class);
	
    @Autowired
    private ICountryInfoApi countryService;
    
    @Autowired
    private CountryValidation countryValidation;

    @Autowired
	private IUserApi userApi;


    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(RedirectAttributes redirectAttributes) throws IOException {
    	try {

    		if (AuthenticationUtil.getCurrentUser(userApi) == null) {
				redirectAttributes.addFlashAttribute(StringConstants.ERROR , "Athentication failed");
				return "redirect:/logout";
			}

			return "country/addCountry";

    	}catch(Exception e){
    		logger.error("Stack trace: " + e.getStackTrace());
			return "redirect:/";
    	}

    }

    @RequestMapping(method = RequestMethod.POST, value = "/save")
	public String addCountry(ModelMap modelMap, @ModelAttribute("countryDto") CountryInfoDTO countryDto,
			RedirectAttributes redirectAttributes) {
		try {

			if (AuthenticationUtil.getCurrentUser(userApi) == null) {
				redirectAttributes.addFlashAttribute(StringConstants.ERROR , "Athentication failed");
				return "redirect:/logout";
			}

			CountryError countryError = new CountryError();
			countryError = countryValidation.countryValidateOnSave(countryDto);
			if (countryError.isValid()) {
				countryService.save(countryDto);
				redirectAttributes.addFlashAttribute(StringConstants.MESSAGE , "successfully saved");
				return "redirect:/country/list";
			} else {
				redirectAttributes.addAttribute(StringConstants.ERROR , "Failed to save check the field errors");
				modelMap.put(StringConstants.ERROR ,countryError);
				modelMap.put(StringConstants.CITY_ERROR , countryDto);
				 return "country/addCountry";
			}

		} catch (Exception e) {
			logger.error("Stack trace: " + e.getStackTrace());
			return "redirect:/";
		}

	}
    
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(HttpServletRequest request , ModelMap modelMap , RedirectAttributes redirectAttributes) throws IOException {
    	try {

    		if (AuthenticationUtil.getCurrentUser(userApi) == null) {
				redirectAttributes.addFlashAttribute(StringConstants.ERROR , "Athentication failed");
				return "redirect:/logout";
			}

			modelMap.put(StringConstants.COUNTRY_LIST , countryService.list());

			return "country/countryList";

    	}catch(Exception e){
    		logger.error("Stack trace: " + e.getStackTrace());
			return "redirect:/";
    	}
    }

}
