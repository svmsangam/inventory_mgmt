package com.inventory.web.controller;

import com.inventory.core.api.iapi.IEmployeeProfileApi;
import com.inventory.core.api.iapi.IUserApi;
import com.inventory.core.model.dto.InvUserDTO;
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
 * Created by dhiraj on 12/19/17.
 */
@Controller
@RequestMapping("profile")
public class EmployeeProfileController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IUserApi userApi;

    @Autowired
    private IEmployeeProfileApi profileApi;

    @GetMapping(value = "/")
    public String index(RedirectAttributes redirectAttributes) {


        return "redirect:/profile/list";
    }

    @GetMapping(value = "/list")
    public String list(RedirectAttributes redirectAttributes , ModelMap modelMap) {

        try {

         /*current user checking start*/
            InvUserDTO currentUser = AuthenticationUtil.getCurrentUser(userApi);

            if (currentUser == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Athentication failed");
                return "redirect:/logout";
            }

            if (!((currentUser.getUserauthority().contains(Authorities.SUPERADMIN) | currentUser.getUserauthority().contains(Authorities.ADMINISTRATOR)) && currentUser.getUserauthority().contains(Authorities.AUTHENTICATED))) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Athentication failed");
                return "redirect:/logout";
            }

            if (currentUser.getStoreId() == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Store not assigned");
                return "redirect:/";//store not assigned page
            }

        /*current user checking end*/

            modelMap.put(StringConstants.EMPLOYEE_PROFILE, profileApi.list(Status.ACTIVE));

        }catch (Exception e){
            logger.error("Exception on employee profile controller : " + Arrays.toString(e.getStackTrace()));
            return "redirect:/500";
        }

        return "employee/list";
    }

    @GetMapping(value = "/add")
    public String add(RedirectAttributes redirectAttributes) {

          /*current user checking start*/
        InvUserDTO currentUser = AuthenticationUtil.getCurrentUser(userApi);

        if (currentUser == null) {
            redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Athentication failed");
            return "redirect:/logout";
        }

        if (!((currentUser.getUserauthority().contains(Authorities.SUPERADMIN) | currentUser.getUserauthority().contains(Authorities.ADMINISTRATOR)) && currentUser.getUserauthority().contains(Authorities.AUTHENTICATED))) {
            redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Athentication failed");
            return "redirect:/logout";
        }

        if (currentUser.getStoreId() == null) {
            redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Store not assigned");
            return "redirect:/";//store not assigned page
        }

        /*current user checking end*/


        return "employee/add";
    }
}
