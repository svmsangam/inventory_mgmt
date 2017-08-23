package com.inventory.web.controller;

import com.inventory.core.api.iapi.IUnitInfoApi;
import com.inventory.core.api.iapi.IUserApi;
import com.inventory.core.model.dto.InvUserDTO;
import com.inventory.core.model.dto.UnitInfoDTO;
import com.inventory.core.model.enumconstant.Permission;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.util.Authorities;
import com.inventory.core.validation.UnitInfoValidation;
import com.inventory.web.error.UnitInfoError;
import com.inventory.web.util.AuthenticationUtil;
import com.inventory.web.util.StringConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;

/*
 * Created by dhiraj on 6/26/17.

**/

@Controller
@RequestMapping("/unit")
public class UnitController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IUserApi userApi;

    @Autowired
    private IUnitInfoApi unitInfoApi;

    @Autowired
    private UnitInfoValidation unitInfoValidation;

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

            if (currentUser.getUserauthority().contains(Authorities.USER) & ! AuthenticationUtil.checkPermission(currentUser, Permission.UNIT_VIEW)) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Access deniled");
                return "redirect:/";//access deniled page
            }

            if (currentUser.getStoreId() == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Store not assigned");
                return "redirect:/";//store not assigned page
            }

        /*current user checking end*/

            modelMap.put(StringConstants.UNIT_LIST, unitInfoApi.list(Status.ACTIVE, currentUser.getStoreId()));

        } catch (Exception e) {

            logger.error("Exception on unit controller : " + Arrays.toString(e.getStackTrace()));
            return "redirect:/500";
        }

        return "category/list";
    }

    @GetMapping(value = "/add")
    public String add(RedirectAttributes redirectAttributes) {

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

            if (currentUser.getUserauthority().contains(Authorities.USER) & ! AuthenticationUtil.checkPermission(currentUser, Permission.UNIT_CREATE)) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Access deniled");
                return "redirect:/";//access deniled page
            }

            if (currentUser.getStoreId() == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Store not assigned");
                return "redirect:/";//store not assigned page
            }

        /*current user checking end*/
        } catch (Exception e) {

            logger.error("Exception on unit controller : " + Arrays.toString(e.getStackTrace()));
            return "redirect:/500";
        }

        return "tag/add";
    }

    @PostMapping(value = "/save")
    public String save(@ModelAttribute("unit") UnitInfoDTO unitInfoDTO, BindingResult bindingResult, ModelMap modelMap, RedirectAttributes redirectAttributes) {

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

            if (currentUser.getUserauthority().contains(Authorities.USER) & ! AuthenticationUtil.checkPermission(currentUser, Permission.UNIT_CREATE)) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Access deniled");
                return "redirect:/";//access deniled page
            }

            if (currentUser.getStoreId() == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Store not assigned");
                return "redirect:/";//store not assigned page
            }

        /*current user checking end*/

            if (unitInfoDTO == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "bad request");
                return "redirect:/unit/add";
            }

            unitInfoDTO.setStoreInfoId(currentUser.getStoreId());

            UnitInfoError error = unitInfoValidation.onSave(unitInfoDTO, bindingResult);

            if (!error.isValid()) {
                modelMap.put(StringConstants.UNIT_ERROR, error);
                modelMap.put(StringConstants.UNIT, unitInfoDTO);
                return "tag/add";
            }

            unitInfoApi.save(unitInfoDTO);

        } catch (Exception e) {

            logger.error("Exception on unit controller : " + Arrays.toString(e.getStackTrace()));
            return "redirect:/500";
        }

        return "redirect:/unit/list";
    }

    @GetMapping(value = "/edit")
    public String edit(@RequestParam("unit") long unitId, ModelMap modelMap, RedirectAttributes redirectAttributes) {

        try {


            return "/unit/edit";


        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/";
        }

    }

    @PostMapping(value = "/update")
    public String update(RedirectAttributes redirectAttributes) {

        try {


            redirectAttributes.addFlashAttribute("message", "UnitInfo Edited Successfully");
            return "redirect:/unit/list";

        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/";
        }

    }

    @GetMapping(value = "/{unitId}")
    public String show(@PathVariable("unitId") Long unitId, ModelMap modelMap, RedirectAttributes redirectAttributes) {

        try {


        } catch (Exception e) {
            e.printStackTrace();
            return "redirct:/";
        }


        return "unit/show";
    }

    @GetMapping(value = "/delete")
    public String delete(@RequestParam("unit") long unitId, RedirectAttributes redirectAttributes) {

        try {

            return "redirect:/unit/list";

        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/";
        }

    }
}
