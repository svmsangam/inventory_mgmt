package com.inventory.web.controller;

import com.inventory.core.api.iapi.ICountryInfoApi;
import com.inventory.core.api.iapi.IStateInfoApi;
import com.inventory.core.api.iapi.IUserApi;
import com.inventory.core.model.dto.StateInfoDTO;
import com.inventory.core.validation.StateValidation;
import com.inventory.web.error.StateError;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


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

    private Logger logger = LoggerFactory.getLogger(StateController.class);

    @RequestMapping(method = RequestMethod.GET, value = "/list")
    public String listStates(ModelMap modelMap, RedirectAttributes redirectAttributes) {
        try {
            if (AuthenticationUtil.getCurrentUser(userApi) == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Athentication failed");
                return "redirect:/home/logout";
            }

            modelMap.put(StringConstants.STATE_LIST, stateService.list());
            return "state/listStates";


        } catch (Exception e) {
            logger.error("Stack trace: " + e.getStackTrace());
            return "redirect:/";
        }
    }


    @RequestMapping(method = RequestMethod.GET, value = "/delete")
    public String removeState(ModelMap modelMap, @RequestParam("id") long id, RedirectAttributes redirectAttributes) {
        try {

            if (AuthenticationUtil.getCurrentUser(userApi) == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Athentication failed");
                return "redirect:/home/logout";
            }

            stateService.delete(id);
            return "redirect:/state/list";

        } catch (Exception e) {
            logger.error("Stack trace: " + e.getStackTrace());
            return "redirect:/";
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/add")
    public String addState(ModelMap modelMap, RedirectAttributes redirectAttributes) {
        try {

            if (AuthenticationUtil.getCurrentUser(userApi) == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Athentication failed");
                return "redirect:/home/logout";
            }

            modelMap.put(StringConstants.COUNTRY_LIST, countryService.list());
            return "state/addState";

        } catch (Exception e) {
            logger.error("Stack trace: " + e.getStackTrace());
            return "redirect:/";
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/save")
    public String addState(ModelMap modelMap, @ModelAttribute("stateDto") StateInfoDTO stateDto,
                           RedirectAttributes redirectAttributes) {
        try {
            if (AuthenticationUtil.getCurrentUser(userApi) == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Athentication failed");
                return "redirect:/home/logout";
            }

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
            logger.error("Stack trace: " + e.getStackTrace());
            return "redirect:/";
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/edit")
    public String editState(ModelMap modelMap, @RequestParam("id") long id, RedirectAttributes redirectAttributes) {
        try {
            if (AuthenticationUtil.getCurrentUser(userApi) == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Athentication failed");
                return "redirect:/home/logout";
            }

            StateInfoDTO stateDto = stateService.show(id);
            modelMap.put(StringConstants.COUNTRY_LIST, countryService.list());
            modelMap.put(StringConstants.STATE, stateDto);
            return "state/editState";

        } catch (Exception e) {
            logger.error("Stack trace: " + e.getStackTrace());
            return "redirect:/";
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/update")
    public String updateState(ModelMap modelMap, @ModelAttribute("stateDto") StateInfoDTO stateDto,
                              RedirectAttributes redirectAttributes) {
        try {

            if (AuthenticationUtil.getCurrentUser(userApi) == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Athentication failed");
                return "redirect:/logout";
            }
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
            logger.error("Stack trace: " + e.getStackTrace());
            return "redirect:/";
        }
    }

}
