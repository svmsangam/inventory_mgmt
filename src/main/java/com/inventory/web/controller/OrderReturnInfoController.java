package com.inventory.web.controller;

import com.inventory.core.api.iapi.*;
import com.inventory.core.model.dto.FiscalYearInfoDTO;
import com.inventory.core.model.dto.InvUserDTO;
import com.inventory.core.model.dto.OrderInfoDTO;
import com.inventory.core.model.dto.OrderReturnInfoDTO;
import com.inventory.core.model.enumconstant.Permission;
import com.inventory.core.model.enumconstant.SalesOrderStatus;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.model.liteentity.OrderReturnInfoDomain;
import com.inventory.core.util.Authorities;
import com.inventory.core.validation.OrderReturnValidation;
import com.inventory.web.error.OrderReturnError;
import com.inventory.web.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Created by dhiraj on 1/17/18.
 */
@Controller
@RequestMapping("orderreturn")
public class OrderReturnInfoController {

    @Autowired
    private IUserApi userApi;

    @Autowired
    private IOrderInfoApi orderInfoApi;

    @Autowired
    private IOrderItemInfoApi orderItemInfoApi;

    @Autowired
    private IFiscalYearInfoApi fiscalYearInfoApi;

    @Autowired
    private IInvoiceInfoApi invoiceInfoApi;

    @Autowired
    private IOrderReturnInfoApi orderReturnInfoApi;

    @Autowired
    private OrderReturnValidation orderReturnValidation;

    @Autowired
    private IReturnItemInfoApi returnItemInfoApi;

    @GetMapping(value = "/add")
    public String add(@RequestParam("orderInfoId") Long orderInfoId, ModelMap modelMap, RedirectAttributes redirectAttributes) {

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

            if (currentUser.getUserauthority().contains(Authorities.USER) & !AuthenticationUtil.checkPermission(currentUser, Permission.SALES_ORDER_RETURN_CREATE)) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Access deniled");
                return "redirect:/";//access deniled page
            }

            if (currentUser.getStoreId() == null) {
                redirectAttributes.addFlashAttribute(StringConstants.INFO, UIUtil.addStoreMessage());
                return "redirect:/store/list";//store not assigned page
            }

            FiscalYearInfoDTO currentFiscalYear = fiscalYearInfoApi.getCurrentFiscalYearByStoreInfo(currentUser.getStoreId());

            if (currentFiscalYear == null) {
                redirectAttributes.addFlashAttribute(StringConstants.INFO, "please create current fiscal year");
                return "redirect:/";//store not assigned page
            }


        /*current user checking end*/

            if (orderInfoId == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "bad request");

                return "redirect:/order/listSale";
            }

            if (orderInfoId < 0) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "bad request");

                return "redirect:/order/listSale";
            }

            OrderInfoDTO orderInfoDTO = orderInfoApi.show(Status.ACTIVE, orderInfoId, currentUser.getStoreId());

            if (orderInfoDTO == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "order not found");

                return "redirect:/order/listSale";
            }

            if (!orderInfoDTO.getSaleTrack().equals(SalesOrderStatus.DELIVERED)) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "order not delivered yet");

                return "redirect:/order/listSale";
            }

            modelMap.put(StringConstants.ORDER, orderInfoDTO);
            modelMap.put(StringConstants.ORDER_ITEM_LIST, orderItemInfoApi.getAllByStatusAndOrderInfo(Status.ACTIVE, orderInfoId));
            modelMap.put(StringConstants.INVOICE , invoiceInfoApi.getByOrderIdAndStatusAndStoreId(orderInfoId , Status.ACTIVE , currentUser.getStoreId()));



        } catch (Exception e) {
            LoggerUtil.logException(this.getClass() , e);
            return "redirect:/500";
        }

        return "orderReturn/add";
    }

    @PostMapping(value = "/save")
    public String saveSaleOrder(@ModelAttribute("orderReturn") OrderReturnInfoDTO orderReturnInfo, BindingResult bindingResult, ModelMap modelMap, RedirectAttributes redirectAttributes) {

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

            if (currentUser.getUserauthority().contains(Authorities.USER) & !AuthenticationUtil.checkPermission(currentUser, Permission.SALES_ORDER_RETURN_CREATE)) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Access deniled");
                return "redirect:/";//access deniled page
            }

            if (currentUser.getStoreId() == null) {
                redirectAttributes.addFlashAttribute(StringConstants.INFO, UIUtil.addStoreMessage());
                return "redirect:/store/list";//store not assigned page
            }

            FiscalYearInfoDTO currentFiscalYear = fiscalYearInfoApi.getCurrentFiscalYearByStoreInfo(currentUser.getStoreId());

            if (currentFiscalYear == null){
                redirectAttributes.addFlashAttribute(StringConstants.INFO, "please create current fiscal year");
                return "redirect:/fiscalyear/add";//store not assigned page
            }


        /*current user checking end*/


            if (orderReturnInfo.getOrderInfoId() == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "bad request");

                return "redirect:/order/listSale";
            }

            if (orderReturnInfo.getOrderInfoId() < 0) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "bad request");

                return "redirect:/order/listSale";
            }

            OrderInfoDTO orderInfoDTO = orderInfoApi.show(Status.ACTIVE, orderReturnInfo.getOrderInfoId(), currentUser.getStoreId());

            if (orderInfoDTO == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "order not found");

                return "redirect:/order/listSale";
            }

            if (!orderInfoDTO.getSaleTrack().equals(SalesOrderStatus.DELIVERED)) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "order not delivered yet");

                return "redirect:/order/listSale";
            }


            synchronized (this.getClass()) {

                orderReturnInfo.setCreatedById(currentUser.getUserId());
                orderReturnInfo.setStoreId(currentUser.getStoreId());

                OrderReturnError error = orderReturnValidation.onsave(orderReturnInfo, bindingResult);

                if (!error.isValid()) {

                    modelMap.put(StringConstants.ORDER, orderInfoDTO);
                    modelMap.put(StringConstants.ORDER_ITEM_LIST, orderItemInfoApi.getAllByStatusAndOrderInfo(Status.ACTIVE, orderInfoDTO.getOrderId()));
                    modelMap.put(StringConstants.INVOICE , invoiceInfoApi.getByOrderIdAndStatusAndStoreId(orderInfoDTO.getOrderId() , Status.ACTIVE , currentUser.getStoreId()));
                    modelMap.put(StringConstants.ORDER_RETURN_ERROR , error);

                    return "orderReturn/add";
                }

                orderReturnInfo = orderReturnInfoApi.save(orderReturnInfo);

                redirectAttributes.addFlashAttribute(StringConstants.MESSAGE , "order return saved successfullly");
            }

        } catch (Exception e) {
            LoggerUtil.logException(this.getClass() , e);
            return "redirect:/500";
        }
        return "redirect:/orderreturn/list";
    }


    @GetMapping(value = "/list")
    public String list(@RequestParam(value = "pageNo", required = false) Integer page, ModelMap modelMap, RedirectAttributes redirectAttributes) {

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

            if (currentUser.getUserauthority().contains(Authorities.USER) & !AuthenticationUtil.checkPermission(currentUser, Permission.SALES_ORDER_RETURN_CREATE)) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Access deniled");
                return "redirect:/";//access deniled page
            }

            if (currentUser.getStoreId() == null) {
                redirectAttributes.addFlashAttribute(StringConstants.INFO, UIUtil.addStoreMessage());
                return "redirect:/store/list";//store not assigned page
            }

            FiscalYearInfoDTO currentFiscalYear = fiscalYearInfoApi.getCurrentFiscalYearByStoreInfo(currentUser.getStoreId());

            if (currentFiscalYear == null) {
                redirectAttributes.addFlashAttribute(StringConstants.INFO, "please create current fiscal year");
                return "redirect:/fiscalyear/add";//store not assigned page
            }


        /*current user checking end*/

            if (page == null) {
                page = 1;
            }

            if (page < 1) {
                page = 1;
            }

            int currentpage = page - 1;

            long totalList = orderReturnInfoApi.countAllByStatusAndStoreInfo_Id(Status.ACTIVE, currentUser.getStoreId());

            int totalpage = (int) Math.ceil(totalList / PageInfo.pageList);

            if (currentpage > totalpage || currentpage < 0) {
                currentpage = 0;
            }

            List<Integer> pagesnumbers = PageInfo.PageLimitCalculator(page, totalpage, PageInfo.numberOfPage);

            modelMap.put(StringConstants.ORDER_RETURN_LIST, orderReturnInfoApi.list(Status.ACTIVE, currentUser.getStoreId() , currentpage, (int) PageInfo.pageList));
            modelMap.put("lastpage", totalpage);
            modelMap.put("currentpage", page);
            modelMap.put("pagelist", pagesnumbers);

        } catch (Exception e) {
            LoggerUtil.logException(this.getClass() , e);
            return "redirect:/500";
        }

        return "orderReturn/list";
    }

    @GetMapping(value = "show/{orderReturnId}")
    public String show(@PathVariable("orderReturnId") Long orderReturnId, ModelMap modelMap, RedirectAttributes redirectAttributes) {

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
                redirectAttributes.addFlashAttribute(StringConstants.INFO, UIUtil.addStoreMessage());
                return "redirect:/store/list";//store not assigned page
            }

        /*current user checking end*/

            if (orderReturnId == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Order not found");
                return "redirect:/orderreturn/list";
            }

            if (orderReturnId < 0) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Order not found");
                return "redirect:/orderreturn/list";
            }

            OrderReturnInfoDomain orderReturnInfoDomain = orderReturnInfoApi.show(orderReturnId , Status.ACTIVE, currentUser.getStoreId());

            if (orderReturnInfoDomain == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Order not found");
                return "redirect:/orderreturn/list";
            }

            modelMap.put(StringConstants.ORDER_RETURN, orderReturnInfoDomain);
            modelMap.put(StringConstants.ORDER_RETURN_ITEM_LIST, returnItemInfoApi.list(Status.ACTIVE, orderReturnId));

        } catch (Exception e) {
            LoggerUtil.logException(this.getClass() , e);
            return "redirect:/500";
        }
        return "orderReturn/show";
    }

}
