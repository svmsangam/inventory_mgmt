package com.inventory.web.controller;

import com.inventory.core.api.iapi.IServiceInfoApi;
import com.inventory.core.api.iapi.IUserApi;
import com.inventory.core.model.dto.InvUserDTO;
import com.inventory.core.model.dto.ServiceDTO;
import com.inventory.core.model.enumconstant.Permission;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.util.Authorities;
import com.inventory.web.util.AuthenticationUtil;
import com.inventory.web.util.LoggerUtil;
import com.inventory.web.util.StringConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;

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
    public String list(ModelMap modelMap, RedirectAttributes redirectAttributes) {

        try {

        /*current user checking start*/
            InvUserDTO currentUser = AuthenticationUtil.getCurrentUser(userApi);

            if (currentUser == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Athentication failed");
                return "redirect:/logout";
            }

            if (!(currentUser.getUserauthority().contains(Authorities.SYSTEM) && currentUser.getUserauthority().contains(Authorities.AUTHENTICATED))) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Athentication failed");
                return "redirect:/logout";
            }


        /*current user checking end*/

            modelMap.put(StringConstants.SERVICE_LIST, serviceInfoApi.list(Status.ACTIVE));

        } catch (Exception e) {

            LoggerUtil.logException(this.getClass() , e);
            return "redirect:/500";
        }

        return "serviceInfo/list";
    }

    @GetMapping(value = "/add")
    public String add(ModelMap modelMap, RedirectAttributes redirectAttributes) {

        try {

        /*current user checking start*/
            InvUserDTO currentUser = AuthenticationUtil.getCurrentUser(userApi);

            if (currentUser == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Athentication failed");
                return "redirect:/logout";
            }

            if (!(currentUser.getUserauthority().contains(Authorities.SYSTEM) && currentUser.getUserauthority().contains(Authorities.AUTHENTICATED))) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Athentication failed");
                return "redirect:/logout";
            }


        /*current user checking end*/

        } catch (Exception e) {

            LoggerUtil.logException(this.getClass() , e);
            return "redirect:/500";
        }

        return "serviceInfo/add";
    }

    @PostMapping(value = "/save")
    public String save(@ModelAttribute("service")ServiceDTO serviceDTO , ModelMap modelMap, RedirectAttributes redirectAttributes) {

        try {

        /*current user checking start*/
            InvUserDTO currentUser = AuthenticationUtil.getCurrentUser(userApi);

            if (currentUser == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Athentication failed");
                return "redirect:/logout";
            }

            if (!(currentUser.getUserauthority().contains(Authorities.SYSTEM) && currentUser.getUserauthority().contains(Authorities.AUTHENTICATED))) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Athentication failed");
                return "redirect:/logout";
            }


        /*current user checking end*/

            synchronized (this){
                serviceInfoApi.save(serviceDTO);
                redirectAttributes.addFlashAttribute(StringConstants.MESSAGE , "service saved successfully");
            }

        } catch (Exception e) {

            LoggerUtil.logException(this.getClass() , e);
            return "redirect:/500";
        }

        return "redirect:/service/list";
    }
}
