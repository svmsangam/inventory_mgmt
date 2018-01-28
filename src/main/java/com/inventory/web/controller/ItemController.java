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
@RequestMapping("/item")
public class ItemController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IUserApi userApi;

    @Autowired
    private IProductInfoApi productInfoApi;

    @Autowired
    private IItemInfoApi itemInfoApi;

    @Autowired
    private ITagInfoApi tagInfoApi;

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
    public String add(@RequestParam(value = "productId" , required = false) Long productId, ModelMap modelMap, RedirectAttributes redirectAttributes) {

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

            if (currentUser.getUserauthority().contains(Authorities.USER) & !AuthenticationUtil.checkPermission(currentUser, Permission.ITEM_CREATE)) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Access deniled");
                return "redirect:/";//access deniled page
            }

            if (currentUser.getStoreId() == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Store not assigned");
                return "redirect:/";//store not assigned page
            }

        /*current user checking end*/

            if (productId == null) {
                modelMap.put(StringConstants.PRODUCT_LIST, productInfoApi.list(Status.ACTIVE , currentUser.getStoreId()));
                modelMap.put(StringConstants.TAG_LIST, tagInfoApi.list(Status.ACTIVE, currentUser.getStoreId()));
                modelMap.put(StringConstants.LOT_LIST, lotInfoApi.list(Status.ACTIVE));

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
            modelMap.put(StringConstants.TAG_LIST, tagInfoApi.list(Status.ACTIVE, currentUser.getStoreId()));
            modelMap.put(StringConstants.LOT_LIST, lotInfoApi.list(Status.ACTIVE));

        } catch (Exception e) {
            logger.error("Exception on category controller : " + Arrays.toString(e.getStackTrace()));
            return "redirect:/500";
        }

        return "item/add";
    }

    @PostMapping(value = "/save")
    public String save(@RequestAttribute("item") ItemInfoDTO itemInfoDTO, BindingResult bindingResult, ModelMap modelMap, RedirectAttributes redirectAttributes) {

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

            if (currentUser.getUserauthority().contains(Authorities.USER) & !AuthenticationUtil.checkPermission(currentUser, Permission.ITEM_CREATE)) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Access deniled");
                return "redirect:/";//access deniled page
            }

            if (currentUser.getStoreId() == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Store not assigned");
                return "redirect:/";//store not assigned page
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
                    modelMap.put(StringConstants.TAG_LIST, tagInfoApi.list(Status.ACTIVE, currentUser.getStoreId()));
                    modelMap.put(StringConstants.LOT_LIST, lotInfoApi.list(Status.ACTIVE));
                    modelMap.put(StringConstants.ITEM_ERROR, error);
                    modelMap.put(StringConstants.ITEM, itemInfoDTO);
                    return "item/add";
                }

                itemInfoDTO = itemInfoApi.save(itemInfoDTO);
            }

        } catch (Exception e) {
            logger.error("Exception on category controller : " + Arrays.toString(e.getStackTrace()));
            return "redirect:/500";
        }

        redirectAttributes.addFlashAttribute(StringConstants.MESSAGE , "item successfully saved");
        return "redirect:/product/" + itemInfoDTO.getProductId();
    }

    @GetMapping(value = "/edit")
    public String edit(@RequestParam("itemId") Long itemId, ModelMap modelMap) {

        return "redirect:/item/list";
    }

    @PostMapping(value = "/update")
    public String update() {

        return "redirect:/item/list";
    }

    @GetMapping(value = "/{itemId}")
    public String show(@PathVariable("itemId") Integer itemId, ModelMap modelMap, RedirectAttributes redirectAttributes) {

        try {


        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/";
        }
        return "/item/show";
    }

    @GetMapping(value = "/delete")
    public String delete(@RequestParam("itemId") Integer itemId, RedirectAttributes redirectAttributes) {

        try {


        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/";
        }

        return "redirect:/item/listSale";
    }

}
