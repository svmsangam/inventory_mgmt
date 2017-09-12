package com.inventory.web.controller;

import com.inventory.core.api.iapi.IItemInfoApi;
import com.inventory.core.api.iapi.IOrderInfoApi;
import com.inventory.core.api.iapi.IOrderItemInfoApi;
import com.inventory.core.api.iapi.IUserApi;
import com.inventory.core.model.dto.InvUserDTO;
import com.inventory.core.model.dto.OrderInfoDTO;
import com.inventory.core.model.enumconstant.Permission;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.util.Authorities;
import com.inventory.web.util.AuthenticationUtil;
import com.inventory.web.util.PageInfo;
import com.inventory.web.util.StringConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("order")
public class OrderInfoController {

    @Autowired
    private IUserApi userApi;

    @Autowired
    private IItemInfoApi itemInfoApi;

    @Autowired
    private IOrderInfoApi orderInfoApi;

    @Autowired
    private IOrderItemInfoApi orderItemInfoApi;

    @GetMapping(value = "/sale/list")
    public String listSale(@RequestParam(value = "pageNo", required = false) Integer page, ModelMap modelMap, RedirectAttributes redirectAttributes) {

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

            if (page == null) {
                page = 1;
            }

            if (page < 1) {
                page = 1;
            }

            int currentpage = page - 1;

            long totalList = orderInfoApi.countList(Status.ACTIVE, currentUser.getStoreId());

            int totalpage = (int) Math.ceil(totalList / PageInfo.pageList);

            if (currentpage > totalpage || currentpage < 0) {
                currentpage = 0;
            }

            List<Integer> pagesnumbers = PageInfo.PageLimitCalculator(page, totalpage, PageInfo.numberOfPage);

            modelMap.put(StringConstants.ORDER_LIST, orderInfoApi.list(Status.ACTIVE, currentUser.getStoreId(), currentpage, (int) PageInfo.pageList));
            modelMap.put("lastpage", totalpage);
            modelMap.put("currentpage", page);
            modelMap.put("pagelist", pagesnumbers);

        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/";
        }

        return "order/listSale";
    }

    @GetMapping(value = "/sale/add")
    public String addOnSale(ModelMap modelMap, RedirectAttributes redirectAttributes) {

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

            modelMap.put(StringConstants.ITEM_LIST, itemInfoApi.getAllByStatusAndStoreWithStock(Status.ACTIVE, currentUser.getStoreId()));
            modelMap.put(StringConstants.ORDERNO, orderInfoApi.generatOrderNumber(currentUser.getStoreId()));

            return "order/addSale";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/";
        }
    }

    @PostMapping(value = "/sale/save")
    public String saveSaleOrder(@ModelAttribute("order") OrderInfoDTO orderInfoDTO, BindingResult bindingResult, ModelMap modelMap, RedirectAttributes redirectAttributes) {

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

            orderInfoDTO.setStoreInfoId(currentUser.getStoreId());
            orderInfoDTO.setCreatedById(currentUser.getUserId());

            orderInfoApi.save(orderInfoDTO);

        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/";
        }
        return "redirect:/order/sale/list";
    }

    @GetMapping(value = "sale/{orderId}")
    public String show(@PathVariable("orderId") Long orderId, ModelMap modelMap, RedirectAttributes redirectAttributes) {

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

            if (currentUser.getUserauthority().contains(Authorities.USER) & !AuthenticationUtil.checkPermission(currentUser, Permission.SALES_ORDER_VIEW)) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Access deniled");
                return "redirect:/";//access deniled page
            }

            if (currentUser.getStoreId() == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Store not assigned");
                return "redirect:/";//store not assigned page
            }

        /*current user checking end*/

            if (orderId == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Order not found");
                return "redirect:/order/sale/list";
            }

            if (orderId < 0) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Order not found");
                return "redirect:/order/sale/list";
            }

            OrderInfoDTO orderInfoDTO = orderInfoApi.show(Status.ACTIVE, orderId, currentUser.getStoreId());

            if (orderInfoDTO == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Order not found");
                return "redirect:/order/sale/list";
            }

            modelMap.put(StringConstants.ORDER, orderInfoDTO);
            modelMap.put(StringConstants.ORDER_ITEM_LIST, orderItemInfoApi.getAllByStatusAndOrderInfo(Status.ACTIVE, orderId));


        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/";
        }
        return "order/showSale";
    }

    @GetMapping(value = "/purchaseorder/list")
    public String listPurchase(@RequestParam(value = "pageNo", required = false) Integer page, @RequestParam(value = "sort", required = false) String sort, @RequestParam(value = "direction", required = false) String direction, ModelMap modelMap, RedirectAttributes redirectAttributes) {

        try {


        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/";
        }

        return "orderRequest/list";
    }

    @GetMapping(value = "/addpurchase")
    public String addOnPurchase(ModelMap modelMap, RedirectAttributes redirectAttributes) {

        try {


            return "orderRequest/addPurchase";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/";
        }
    }


    @PostMapping(value = "/savepurchase")
    public String saveOnPurchase(ModelMap modelMap, RedirectAttributes redirectAttributes) {

        try {


        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/";
        }
        return "redirect:/order/";
    }

    @GetMapping(value = "/edit")
    public String edit() {

        return "redirect:/orderRequest/list";
    }

    @PostMapping(value = "/update")
    public String update() {

        return "redirect:/orderRequest/list";
    }

    @GetMapping(value = "/delete")
    public String delete() {

        return "redirect:/orderRequest/list";
    }
}



