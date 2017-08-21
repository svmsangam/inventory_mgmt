package com.inventory.web.controller;

import com.inventory.core.api.iapi.ICategoryInfoApi;
import com.inventory.core.api.iapi.ISubcategoryInfoApi;
import com.inventory.core.api.iapi.IUserApi;
import com.inventory.core.model.dto.InvUserDTO;
import com.inventory.core.model.dto.SubCategoryInfoDTO;
import com.inventory.core.model.enumconstant.Permission;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.util.Authorities;
import com.inventory.core.validation.SubCategoryInfoValidation;
import com.inventory.web.error.SubCategoryInfoError;
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
@RequestMapping("/subcategory")
public class SubcategoryController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IUserApi userApi;

    @Autowired
    private ISubcategoryInfoApi subcategoryInfoApi;

    @Autowired
    private SubCategoryInfoValidation subCategoryInfoValidation;

    @Autowired
    private ICategoryInfoApi categoryInfoApi;


    @GetMapping(value = "/")
    public String index(RedirectAttributes redirectAttributes) {


        return "redirect:/subcategory/list";
    }

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

            if (!(currentUser.getUserauthority().contains(Authorities.USER) & AuthenticationUtil.checkPermission(currentUser, Permission.SUBCATEGORY_VIEW))) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Access deniled");
                return "redirect:/";//access deniled page
            }

            if (currentUser.getStoreId() == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Store not assigned");
                return "redirect:/";//store not assigned page
            }

        /*current user checking end*/

            modelMap.put(StringConstants.SUBCATEGORY_LIST, subcategoryInfoApi.list(Status.ACTIVE, currentUser.getStoreId()));

        } catch (Exception e) {

            logger.error("Exception on subcategory controller : " + Arrays.toString(e.getStackTrace()));
            return "redirect:/500";
        }

        return "subcategory/list";
    }

    @GetMapping(value = "/add")
    public String add(RedirectAttributes redirectAttributes, ModelMap modelMap) {

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

            if (!(currentUser.getUserauthority().contains(Authorities.USER) & AuthenticationUtil.checkPermission(currentUser, Permission.SUBCATEGORY_CREATE))) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Access deniled");
                return "redirect:/";//access deniled page
            }

            if (currentUser.getStoreId() == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Store not assigned");
                return "redirect:/";//store not assigned page
            }
        /*current user checking end*/

            modelMap.put(StringConstants.CATEGORY_LIST, categoryInfoApi.list(Status.ACTIVE, currentUser.getStoreId()));

        } catch (Exception e) {

            logger.error("Exception on category controller : " + Arrays.toString(e.getStackTrace()));
            return "redirect:/500";
        }


        return "subcategory/add";
    }

    @PostMapping(value = "/save")
    public String save(@ModelAttribute("subcategory") SubCategoryInfoDTO subCategoryInfoDTO, BindingResult bindingResult, ModelMap modelMap, RedirectAttributes redirectAttributes) {

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

            if (!(currentUser.getUserauthority().contains(Authorities.USER) & AuthenticationUtil.checkPermission(currentUser, Permission.SUBCATEGORY_CREATE))) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Access deniled");
                return "redirect:/";//access deniled page
            }

            if (currentUser.getStoreId() == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Store not assigned");
                return "redirect:/";//store not assigned page
            }

        /*current user checking end*/

            SubCategoryInfoError error = subCategoryInfoValidation.onSave(subCategoryInfoDTO, bindingResult);

            if (!error.isValid()) {
                modelMap.put(StringConstants.SUBCATEGORY_ERROR, error);
                modelMap.put(StringConstants.SUBCATEGORY, subCategoryInfoDTO);
                modelMap.put(StringConstants.CATEGORY_LIST, categoryInfoApi.list(Status.ACTIVE, currentUser.getStoreId()));
                return "subcategory/add";
            }

            subcategoryInfoApi.save(subCategoryInfoDTO);

        } catch (Exception e) {

            logger.error("Exception on subcategory controller : " + Arrays.toString(e.getStackTrace()));
            return "redirect:/500";
        }

        return "redirect:/subcategory/list";
    }

    @GetMapping(value = "/edit")
    public String edit(@RequestParam("subcategory") long subcategoryId, ModelMap modelMap, RedirectAttributes redirectAttributes) {


        return "/subcategory/edit";
    }

    @PostMapping(value = "/update")
    public String update(RedirectAttributes redirectAttributes) {

        return "redirect:/subcategory/list";
    }

    @GetMapping(value = "/show")
    public String show(RedirectAttributes redirectAttributes) {

        return "redirect:/subcategory/list";
    }

    @GetMapping(value = "/delete")
    public String delete(@RequestParam("subcategory") long subcategoryId, RedirectAttributes redirectAttributes) {

        return "redirect:/subcategory/list";
    }
}
