package com.inventory.web.controller;

import com.inventory.core.api.iapi.ICityInfoApi;
import com.inventory.core.api.iapi.IStateInfoApi;
import com.inventory.core.api.iapi.IUserApi;
import com.inventory.core.validation.CityInfoValidation;
import com.inventory.web.util.AuthenticationUtil;
import com.inventory.web.util.StringConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String listCity(ModelMap modelMap, RedirectAttributes redirectAttributes) {
        try {
            if (AuthenticationUtil.getCurrentUser(userApi) == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Athentication failed");
                return "redirect:/logout";
            }

            modelMap.put(StringConstants.CITY_LIST, cityInfoApi.list());

            return "city/listCities";

        } catch (Exception e) {
            logger.error("Stack trace: " + e.getStackTrace());
            return "redirect:/";
        }
    }

}

	

