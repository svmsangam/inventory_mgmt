package com.inventory.web.controller;

import com.inventory.core.api.iapi.IDesignationInfoApi;
import com.inventory.core.api.iapi.IUserApi;
import com.inventory.core.model.dto.InvUserDTO;
import com.inventory.core.model.enumconstant.Permission;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.util.Authorities;
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

import java.util.Arrays;

/**
 * Created by dhiraj on 2/3/18.
 */
@Controller
@RequestMapping("designation")
public class DesignationController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IUserApi userApi;

    @Autowired
    private IDesignationInfoApi designationInfoApi;

    @GetMapping(value = "/list")
    public String list(ModelMap modelMap, RedirectAttributes redirectAttributes) {

        try {

        /*current user checking start*/
            InvUserDTO currentUser = AuthenticationUtil.getCurrentUser(userApi);

            if (currentUser == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Athentication failed");
                return "redirect:/logout";
            }

            if (!((currentUser.getUserauthority().contains(Authorities.SUPERADMIN) | currentUser.getUserauthority().contains(Authorities.ADMINISTRATOR) | currentUser.getUserauthority().contains(Authorities.USER)) && currentUser.getUserauthority().contains(Authorities.AUTHENTICATED))) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Athentication failed");
                return "redirect:/logout";
            }

            if (currentUser.getUserauthority().contains(Authorities.USER) & ! AuthenticationUtil.checkPermission(currentUser, Permission.DESIGNATION_VIEW)) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Access deniled");
                return "redirect:/";//access deniled page
            }

            if (currentUser.getStoreId() == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Store not assigned");
                return "redirect:/";//store not assigned page
            }

        /*current user checking end*/

        modelMap.put(StringConstants.DESIGNATION_LIST , designationInfoApi.list(Status.ACTIVE , currentUser.getStoreId()));

        } catch (Exception e) {

            logger.error("Exception on qualification controller : " + Arrays.toString(e.getStackTrace()));
            return "redirect:/500";
        }

        return "designation/list";
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

            if (!((currentUser.getUserauthority().contains(Authorities.SUPERADMIN) | currentUser.getUserauthority().contains(Authorities.ADMINISTRATOR) | currentUser.getUserauthority().contains(Authorities.USER)) && currentUser.getUserauthority().contains(Authorities.AUTHENTICATED))) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Athentication failed");
                return "redirect:/logout";
            }

            if (currentUser.getUserauthority().contains(Authorities.USER) & ! AuthenticationUtil.checkPermission(currentUser, Permission.DESIGNATION_CREATE)) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Access deniled");
                return "redirect:/";//access deniled page
            }

            if (currentUser.getStoreId() == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Store not assigned");
                return "redirect:/";//store not assigned page
            }

        /*current user checking end*/

        } catch (Exception e) {

            logger.error("Exception on qualification controller : " + Arrays.toString(e.getStackTrace()));
            return "redirect:/500";
        }

        return "designation/add";
    }
}
