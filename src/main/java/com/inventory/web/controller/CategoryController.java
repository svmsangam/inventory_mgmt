package com.inventory.web.controller;

import com.inventory.core.api.iapi.ICategoryInfoApi;
import com.inventory.core.api.iapi.IUserApi;
import com.inventory.core.model.dto.CategoryInfoDTO;
import com.inventory.core.model.dto.InvUserDTO;
import com.inventory.core.model.enumconstant.Permission;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.util.Authorities;
import com.inventory.core.validation.CategoryInfoValidation;
import com.inventory.web.error.CategoryInfoError;
import com.inventory.web.session.RequestCacheUtil;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@Controller
@RequestMapping("/category")
public class CategoryController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IUserApi userApi;

    @Autowired
    private ICategoryInfoApi categoryInfoApi;

    @Autowired
    private CategoryInfoValidation categoryInfoValidation;

    @GetMapping(value = "/")
    public String index(RedirectAttributes redirectAttributes) {


        return "redirect:/CategoryInfo/list";
    }

    @GetMapping(value = "/list")
    public String list(ModelMap modelMap, RedirectAttributes redirectAttributes , HttpServletRequest request , HttpServletResponse response) {

        try {

        /*current user checking start*/
            InvUserDTO currentUser = AuthenticationUtil.getCurrentUser(userApi);

            if (currentUser == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Athentication failed");

                RequestCacheUtil.save(request , response);

                return "redirect:/login";
            }

            if (!((currentUser.getUserauthority().contains(Authorities.SUPERADMIN) | currentUser.getUserauthority().contains(Authorities.ADMINISTRATOR) | currentUser.getUserauthority().contains(Authorities.USER)) && currentUser.getUserauthority().contains(Authorities.AUTHENTICATED))) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Athentication failed");
                return "redirect:/logout";
            }

            if (currentUser.getUserauthority().contains(Authorities.USER) & ! AuthenticationUtil.checkPermission(currentUser, Permission.CATEGORY_VIEW)) {
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

        return "category/list";
    }

    @GetMapping(value = "/add")
    public String add(RedirectAttributes redirectAttributes , HttpServletRequest request , HttpServletResponse response) {

        try {

                /*current user checking start*/
            InvUserDTO currentUser = AuthenticationUtil.getCurrentUser(userApi);

            if (currentUser == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Athentication failed");

                RequestCacheUtil.save(request , response);

                return "redirect:/login";
            }

            if (!((currentUser.getUserauthority().contains(Authorities.SUPERADMIN) | currentUser.getUserauthority().contains(Authorities.ADMINISTRATOR) | currentUser.getUserauthority().contains(Authorities.USER)) && currentUser.getUserauthority().contains(Authorities.AUTHENTICATED))) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Athentication failed");
                return "redirect:/logout";
            }

            if (currentUser.getUserauthority().contains(Authorities.USER) & ! AuthenticationUtil.checkPermission(currentUser, Permission.CATEGORY_CREATE)) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Access deniled");
                return "redirect:/";//access deniled page
            }

            if (currentUser.getStoreId() == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Store not assigned");
                return "redirect:/";//store not assigned page
            }

        /*current user checking end*/
        } catch (Exception e) {

            logger.error("Exception on category controller : " + Arrays.toString(e.getStackTrace()));
            return "redirect:/500";
        }

        return "category/add";
    }

    @PostMapping(value = "/save")
    public String save(@ModelAttribute("category") CategoryInfoDTO categoryInfoDTO, BindingResult bindingResult, ModelMap modelMap, RedirectAttributes redirectAttributes) {

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

            if (currentUser.getUserauthority().contains(Authorities.USER) & ! AuthenticationUtil.checkPermission(currentUser, Permission.CATEGORY_CREATE)) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Access deniled");
                return "redirect:/";//access deniled page
            }

            if (currentUser.getStoreId() == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Store not assigned");
                return "redirect:/";//store not assigned page
            }

        /*current user checking end*/

            if (categoryInfoDTO == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "bad request");
                return "redirect:/category/add";
            }

            synchronized (this.getClass()) {

                categoryInfoDTO.setStoreInfoId(currentUser.getStoreId());
                categoryInfoDTO.setCreatedById(currentUser.getUserId());

                CategoryInfoError error = categoryInfoValidation.onSave(categoryInfoDTO, bindingResult);

                if (!error.isValid()) {
                    modelMap.put(StringConstants.CATEGORY_ERROR, error);
                    modelMap.put(StringConstants.CATEGORY, categoryInfoDTO);
                    return "category/add";
                }

                categoryInfoApi.save(categoryInfoDTO);
            }

        } catch (Exception e) {

            logger.error("Exception on category controller : " + Arrays.toString(e.getStackTrace()));
            return "redirect:/500";
        }

        return "redirect:/category/list";
    }

    @GetMapping(value = "/edit")
    public String edit(@RequestParam("category") long categoryId, ModelMap modelMap, RedirectAttributes redirectAttributes) {


        return "/category/edit";
    }

    @PostMapping(value = "/update")
    public String update(RedirectAttributes redirectAttributes) {

        return "redirect:/category/listSale";
    }

    @GetMapping(value = "/{categoryId}")
    public String show() {

        return "redirect:/category/listSale";
    }

    @GetMapping(value = "/delete")
    public String delete(@RequestParam("category") long categoryId, RedirectAttributes redirectAttributes) {

        return "redirect:/category/listSale";
    }
}



