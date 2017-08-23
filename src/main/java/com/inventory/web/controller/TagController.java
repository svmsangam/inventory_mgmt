package com.inventory.web.controller;

import com.inventory.core.api.iapi.ITagInfoApi;
import com.inventory.core.api.iapi.IUserApi;
import com.inventory.core.model.dto.InvUserDTO;
import com.inventory.core.model.dto.TagInfoDTO;
import com.inventory.core.model.enumconstant.Permission;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.util.Authorities;
import com.inventory.core.validation.TagInfoValidation;
import com.inventory.web.error.TagInfoError;
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


@Controller
@RequestMapping("/tag")
public class TagController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ITagInfoApi tagInfoApi;

    @Autowired
    private IUserApi userApi;

    @Autowired
    private TagInfoValidation tagInfoValidation;

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

            if (!(currentUser.getUserauthority().contains(Authorities.USER) & AuthenticationUtil.checkPermission(currentUser, Permission.TAG_VIEW))) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Access deniled");
                return "redirect:/";//access deniled page
            }

            if (currentUser.getStoreId() == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Store not assigned");
                return "redirect:/";//store not assigned page
            }

        /*current user checking end*/

            modelMap.put(StringConstants.TAG_LIST, tagInfoApi.list(Status.ACTIVE, currentUser.getStoreId()));

        } catch (Exception e) {

            logger.error("Exception on tag controller : " + Arrays.toString(e.getStackTrace()));
            return "redirect:/500";
        }

        return "tag/list";
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

            if (!(currentUser.getUserauthority().contains(Authorities.USER) & AuthenticationUtil.checkPermission(currentUser, Permission.TAG_CREATE))) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Access deniled");
                return "redirect:/";//access deniled page
            }

            if (currentUser.getStoreId() == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Store not assigned");
                return "redirect:/";//store not assigned page
            }

        /*current user checking end*/
        } catch (Exception e) {

            logger.error("Exception on tag controller : " + Arrays.toString(e.getStackTrace()));
            return "redirect:/500";
        }

        return "tag/add";
    }

    @PostMapping(value = "/save")
    public String save(@ModelAttribute("tag") TagInfoDTO tagInfoDTO, BindingResult bindingResult, ModelMap modelMap, RedirectAttributes redirectAttributes) {

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

            if (!(currentUser.getUserauthority().contains(Authorities.USER) & AuthenticationUtil.checkPermission(currentUser, Permission.TAG_CREATE))) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Access deniled");
                return "redirect:/";//access deniled page
            }

            if (currentUser.getStoreId() == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Store not assigned");
                return "redirect:/";//store not assigned page
            }

        /*current user checking end*/

            if (tagInfoDTO == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "bad request");
                return "redirect:/tag/add";
            }

            tagInfoDTO.setStoreInfoId(currentUser.getStoreId());

            TagInfoError error = tagInfoValidation.onSave(tagInfoDTO, bindingResult);

            if (!error.isValid()) {
                modelMap.put(StringConstants.TAG_ERROR, error);
                modelMap.put(StringConstants.TAG, tagInfoDTO);
                return "tag/add";
            }

            tagInfoApi.save(tagInfoDTO);

        } catch (Exception e) {

            logger.error("Exception on tag controller : " + Arrays.toString(e.getStackTrace()));
            return "redirect:/500";
        }

        return "redirect:/tag/list";
    }


    @GetMapping(value = "/edit")
    public String edit(@RequestParam("tag") Long tagId, ModelMap modelMap, RedirectAttributes redirectAttributes) {

        try {


            return "/tag/edit";

        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/";
        }


    }

    @PostMapping(value = "/update")
    public String update(RedirectAttributes redirectAttributes) {

        try {

            return "redirect:/tag/list";

        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/";
        }


    }

    @GetMapping(value = "/{tagId}")
    public String show(@PathVariable("tagId") Long tagId) {

        return "redirect:/tag/list";
    }

    @GetMapping(value = "/delete")
    public String delete(@RequestParam("tag") long tagId, RedirectAttributes redirectAttributes) {

        try {

            return "redirect:/tag/list";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/";
        }

    }
}



