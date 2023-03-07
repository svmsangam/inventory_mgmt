package com.inventory.web.controller;

import com.inventory.core.api.iapi.*;
import com.inventory.core.model.dto.InvUserDTO;
import com.inventory.core.model.dto.ItemInfoDTO;
import com.inventory.core.model.enumconstant.Permission;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.util.Authorities;
import com.inventory.core.validation.ItemInfoValidation;
import com.inventory.web.error.ItemInfoError;
import com.inventory.web.util.AuthenticationUtil;
import com.inventory.web.util.LoggerUtil;
import com.inventory.web.util.StringConstants;
import com.inventory.web.util.UIUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private IUserApi userApi;

    @Autowired
    private IProductInfoApi productInfoApi;

    @Autowired
    private IItemInfoApi itemInfoApi;

    @Autowired
    private ICityInfoApi cityInfoApi;

    @Autowired
    private ILotInfoApi lotInfoApi;

    @Autowired
    private ItemInfoValidation itemInfoValidation;


    @GetMapping(value = "/")
    public String index(RedirectAttributes redirectAttributes) {


        return "redirect:/item/list";
    }

    @GetMapping(value = "/list")
    public String list(@RequestParam(value = "pageNo", required = false) Integer page, @RequestParam(value = "sort", required = false) String sort, @RequestParam(value = "direction", required = false) String direction, ModelMap modelMap, RedirectAttributes redirectAttributes) {

        try {


        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/";
        }
        return "item/list";
    }

    @GetMapping(value = "/add")
	@PreAuthorize("hasAnyRole('ROLE_SUPERADMINISTRATOR','ROLE_ADMINISTRATOR','ROLE_USER,ROLE_AUTHENTICATED')")
    public String add(@RequestParam(value = "productId" , required = false) Long productId, ModelMap modelMap, RedirectAttributes redirectAttributes) {

        try {

                 /*current user checking start*/
            InvUserDTO currentUser = AuthenticationUtil.getCurrentUser(userApi);

            if (currentUser.getUserauthority().contains(Authorities.USER) & !AuthenticationUtil.checkPermission(currentUser, Permission.ITEM_CREATE)) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Access deniled");
                return "redirect:/";//access deniled page
            }

            if (currentUser.getStoreId() == null) {
                redirectAttributes.addFlashAttribute(StringConstants.INFO, UIUtil.addStoreMessage());
                return "redirect:/store/list";//store not assigned page
            }

        /*current user checking end*/

            if (productId == null) {
                modelMap.put(StringConstants.PRODUCT_LIST, productInfoApi.list(Status.ACTIVE , currentUser.getStoreId()));
                modelMap.put(StringConstants.LOT_LIST, lotInfoApi.list(Status.ACTIVE));
                modelMap.put(StringConstants.CITY_LIST, cityInfoApi.list());

                return "item/addItem";
            }

            if (productId < 1) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "invalid product");
                return "redirect:/product/list";
            }

            if (productInfoApi.getByIdAndStoreAndStatus(productId, currentUser.getStoreId(), Status.ACTIVE) == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "product not found");
                return "redirect:/product/list";
            }

            modelMap.put(StringConstants.PRODUCT, productId);
            modelMap.put(StringConstants.LOT_LIST, lotInfoApi.list(Status.ACTIVE));
            modelMap.put(StringConstants.CITY_LIST, cityInfoApi.list());

        } catch (Exception e) {
            LoggerUtil.logException(this.getClass() , e);
            return "redirect:/500";
        }

        return "item/add";
    }

    @PostMapping(value = "/save")
	@PreAuthorize("hasAnyRole('ROLE_SUPERADMINISTRATOR','ROLE_ADMINISTRATOR','ROLE_USER,ROLE_AUTHENTICATED')")
    public String save(ItemInfoDTO itemInfoDTO, BindingResult bindingResult, ModelMap modelMap, RedirectAttributes redirectAttributes) {

        try {
       /*current user checking start*/
            InvUserDTO currentUser = AuthenticationUtil.getCurrentUser(userApi);

            if (currentUser.getUserauthority().contains(Authorities.USER) & !AuthenticationUtil.checkPermission(currentUser, Permission.ITEM_CREATE)) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Access deniled");
                return "redirect:/";//access deniled page
            }

            if (currentUser.getStoreId() == null) {
                redirectAttributes.addFlashAttribute(StringConstants.INFO, UIUtil.addStoreMessage());
                return "redirect:/store/list";//store not assigned page
            }

        /*current user checking end*/

            if (itemInfoDTO == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Bad request");
                return "redirect:/product/list";//store not assigned page
            }

            if (itemInfoDTO.getProductId() == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Bad request");
                return "redirect:/product/list";//store not assigned page
            }

            synchronized (this.getClass()) {
                ItemInfoError error = itemInfoValidation.onSave(itemInfoDTO, currentUser.getStoreId(), bindingResult);

                if (!error.isValid()) {
                    modelMap.put(StringConstants.PRODUCT, itemInfoDTO.getProductId());
                    modelMap.put(StringConstants.LOT_LIST, lotInfoApi.list(Status.ACTIVE));
                    modelMap.put(StringConstants.ITEM_ERROR, error);
                    modelMap.put(StringConstants.ITEM, itemInfoDTO);
                    modelMap.put(StringConstants.CITY_LIST, cityInfoApi.list());
                    return "item/add";
                }

                itemInfoDTO = itemInfoApi.save(itemInfoDTO);
            }

        } catch (Exception e) {
            LoggerUtil.logException(this.getClass() , e);
            return "redirect:/500";
        }

        redirectAttributes.addFlashAttribute(StringConstants.MESSAGE , "item successfully saved");
        return "redirect:/product/" + itemInfoDTO.getProductId();
    }

    @PostMapping(value = "/saveitem")
	@PreAuthorize("hasAnyRole('ROLE_SUPERADMINISTRATOR','ROLE_ADMINISTRATOR','ROLE_USER,ROLE_AUTHENTICATED')")
    public String saveItem(@RequestAttribute("item") ItemInfoDTO itemInfoDTO, BindingResult bindingResult, ModelMap modelMap, RedirectAttributes redirectAttributes) {

        try {
       /*current user checking start*/
            InvUserDTO currentUser = AuthenticationUtil.getCurrentUser(userApi);

            if (currentUser.getUserauthority().contains(Authorities.USER) & !AuthenticationUtil.checkPermission(currentUser, Permission.ITEM_CREATE)) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Access deniled");
                return "redirect:/";//access deniled page
            }

            if (currentUser.getStoreId() == null) {
                redirectAttributes.addFlashAttribute(StringConstants.INFO, UIUtil.addStoreMessage());
                return "redirect:/store/list";//store not assigned page
            }

        /*current user checking end*/

            if (itemInfoDTO == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Bad request");
                return "redirect:/product/list";//store not assigned page
            }

            if (itemInfoDTO.getProductId() == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "select any product");
                return "redirect:/item/add";//store not assigned page
            }

            synchronized (this.getClass()) {

                ItemInfoError error = itemInfoValidation.onSave(itemInfoDTO, currentUser.getStoreId(), bindingResult);

                if (!error.isValid()) {
                    modelMap.put(StringConstants.PRODUCT_LIST, productInfoApi.list(Status.ACTIVE , currentUser.getStoreId()));
                    modelMap.put(StringConstants.LOT_LIST, lotInfoApi.list(Status.ACTIVE));
                    modelMap.put(StringConstants.ITEM_ERROR, error);
                    modelMap.put(StringConstants.ITEM, itemInfoDTO);
                    modelMap.put(StringConstants.CITY_LIST, cityInfoApi.list());

                    return "item/addItem";
                }

                itemInfoDTO = itemInfoApi.save(itemInfoDTO);
            }

        } catch (Exception e) {
            LoggerUtil.logException(this.getClass() , e);
            return "redirect:/500";
        }

        redirectAttributes.addFlashAttribute(StringConstants.MESSAGE , "item successfully saved");
        return "redirect:/product/" + itemInfoDTO.getProductId();
    }

}
