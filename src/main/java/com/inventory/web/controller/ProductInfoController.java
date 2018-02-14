package com.inventory.web.controller;

import com.inventory.core.api.iapi.*;
import com.inventory.core.model.dto.InvUserDTO;
import com.inventory.core.model.dto.ProductInfoDTO;
import com.inventory.core.model.enumconstant.Permission;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.model.enumconstant.TrendingLevel;
import com.inventory.core.util.Authorities;
import com.inventory.core.validation.ProductInfoValidation;
import com.inventory.web.error.ProductInfoError;
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

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by dhiraj on 8/23/17.
 */
@Controller
@RequestMapping("product")
public class ProductInfoController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IUserApi userApi;

    @Autowired
    private IProductInfoApi productInfoApi;

    @Autowired
    private IUnitInfoApi unitInfoApi;

    @Autowired
    private ISubcategoryInfoApi subcategoryInfoApi;

    @Autowired
    private IItemInfoApi itemInfoApi;

    @Autowired
    private ProductInfoValidation productInfoValidation;

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

            if (currentUser.getUserauthority().contains(Authorities.USER) & ! AuthenticationUtil.checkPermission(currentUser, Permission.PRODUCT_VIEW)) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Access deniled");
                return "redirect:/";//access deniled page
            }

            if (currentUser.getStoreId() == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Store not assigned");
                return "redirect:/";//store not assigned page
            }

        /*current user checking end*/

            modelMap.put(StringConstants.PRODUCT_LIST, productInfoApi.list(Status.ACTIVE, currentUser.getStoreId()));

        } catch (Exception e) {

            logger.error("Exception on product controller : " + Arrays.toString(e.getStackTrace()));
            return "redirect:/500";
        }

        return "product/list";
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

            if (currentUser.getUserauthority().contains(Authorities.USER) & ! AuthenticationUtil.checkPermission(currentUser, Permission.PRODUCT_CREATE)) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Access deniled");
                return "redirect:/";//access deniled page
            }

            if (currentUser.getStoreId() == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Store not assigned");
                return "redirect:/";//store not assigned page
            }
        /*current user checking end*/

            modelMap.put(StringConstants.UNIT_LIST, unitInfoApi.list(Status.ACTIVE, currentUser.getStoreId()));

            modelMap.put(StringConstants.SUBCATEGORY_LIST, subcategoryInfoApi.list(Status.ACTIVE, currentUser.getStoreId()));

            modelMap.put(StringConstants.TRENDING_LIST, TrendingLevel.values());

        } catch (Exception e) {

            logger.error("Exception on product controller : " + Arrays.toString(e.getStackTrace()));
            return "redirect:/500";
        }


        return "product/add";
    }

    @PostMapping(value = "/save")
    public String save(@ModelAttribute("product") ProductInfoDTO productInfoDTO, BindingResult bindingResult, ModelMap modelMap, RedirectAttributes redirectAttributes) {

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

            if (currentUser.getUserauthority().contains(Authorities.USER) & ! AuthenticationUtil.checkPermission(currentUser, Permission.PRODUCT_CREATE)) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Access deniled");
                return "redirect:/";//access deniled page
            }

            if (currentUser.getStoreId() == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Store not assigned");
                return "redirect:/";//store not assigned page
            }

        /*current user checking end*/

            if (productInfoDTO == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "bad request");
                return "redirect:/product/add";
            }

            synchronized (this.getClass()) {
                productInfoDTO.setStoreInfoId(currentUser.getStoreId());
                productInfoDTO.setCreatedById(currentUser.getUserId());

                ProductInfoError error = productInfoValidation.onSave(productInfoDTO, bindingResult);

                if (!error.isValid()) {
                    modelMap.put(StringConstants.PRODUCT_ERROR, error);
                    modelMap.put(StringConstants.PRODUCT, productInfoDTO);
                    modelMap.put(StringConstants.UNIT_LIST, unitInfoApi.list(Status.ACTIVE, currentUser.getStoreId()));
                    modelMap.put(StringConstants.SUBCATEGORY_LIST, subcategoryInfoApi.list(Status.ACTIVE, currentUser.getStoreId()));
                    modelMap.put(StringConstants.TRENDING_LIST, TrendingLevel.values());

                    return "product/add";
                }

                productInfoDTO = productInfoApi.save(productInfoDTO);
            }

        } catch (Exception e) {

            logger.error("Exception on product controller : " + Arrays.toString(e.getStackTrace()));
            return "redirect:/500";
        }

        return "redirect:/product/" + productInfoDTO.getProductId();
    }

    @GetMapping(value = "/{productId}")
    public String show(@PathVariable("productId") Long productId, RedirectAttributes redirectAttributes, ModelMap modelMap) {

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

            if (currentUser.getUserauthority().contains(Authorities.USER) & ! AuthenticationUtil.checkPermission(currentUser, Permission.PRODUCT_VIEW)) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Access deniled");
                return "redirect:/";//access deniled page
            }

            if (currentUser.getStoreId() == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Store not assigned");
                return "redirect:/";//store not assigned page
            }
        /*current user checking end*/

            if (productId == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "invalid product");
                return "redirect:/product/list";//store not assigned page
            }

            if (productId < 1) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "invalid product");
                return "redirect:/product/list";//store not assigned page
            }

            ProductInfoDTO productInfoDTO = productInfoApi.getByIdAndStoreAndStatus(productId, currentUser.getStoreId(), Status.ACTIVE);

            if (productInfoDTO == null){
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "product not found");
                return "redirect:/product/list";//store not assigned page
            }

            modelMap.put(StringConstants.PRODUCT, productInfoDTO);

            modelMap.put(StringConstants.ITEM_LIST, itemInfoApi.getAllByProductAndStatusAndStore(productId, Status.ACTIVE , currentUser.getStoreId()));

            modelMap.put(StringConstants.CATEGORY_LIST , productInfoApi.getAllCategory(productInfoDTO.getSubCategoryId() , currentUser.getStoreId() , Status.ACTIVE ,  new ArrayList<>()));

        } catch (Exception e) {

            logger.error("Exception on product controller : " + Arrays.toString(e.getStackTrace()));
            return "redirect:/500";
        }


        return "product/show";
    }
}
