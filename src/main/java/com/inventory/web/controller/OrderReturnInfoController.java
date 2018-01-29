package com.inventory.web.controller;

import com.inventory.core.api.iapi.*;
import com.inventory.core.model.dto.FiscalYearInfoDTO;
import com.inventory.core.model.dto.InvUserDTO;
import com.inventory.core.model.dto.OrderInfoDTO;
import com.inventory.core.model.dto.OrderReturnInfoDTO;
import com.inventory.core.model.entity.OrderReturnInfo;
import com.inventory.core.model.enumconstant.Permission;
import com.inventory.core.model.enumconstant.SalesOrderStatus;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.util.Authorities;
import com.inventory.web.error.OrderError;
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

/**
 * Created by dhiraj on 1/17/18.
 */
@Controller
@RequestMapping("orderreturn")
public class OrderReturnInfoController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

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
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Store not assigned");
                return "redirect:/";//store not assigned page
            }

            FiscalYearInfoDTO currentFiscalYear = fiscalYearInfoApi.getCurrentFiscalYearByStoreInfo(currentUser.getStoreId());

            if (currentFiscalYear == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "please create current fiscal year");
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
            logger.error("Exception on order return controller : " + Arrays.toString(e.getStackTrace()));

            e.printStackTrace();
            return "redirect:/";
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
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Store not assigned");
                return "redirect:/";//store not assigned page
            }

            FiscalYearInfoDTO currentFiscalYear = fiscalYearInfoApi.getCurrentFiscalYearByStoreInfo(currentUser.getStoreId());

            if (currentFiscalYear == null){
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "please create current fiscal year");
                return "redirect:/fiscalyear/add";//store not assigned page
            }


        /*current user checking end*/

            synchronized (this.getClass()) {
                orderReturnInfo.setCreatedById(currentUser.getUserId());

                /*OrderError error = orderValidation.onSaleSave(orderInfoDTO, bindingResult);

                if (!error.isValid()) {

                    modelMap.put(StringConstants.ITEM_LIST, itemInfoApi.getAllByStatusAndStoreWithStock(Status.ACTIVE, currentUser.getStoreId()));
                    modelMap.put(StringConstants.ORDERNO, orderInfoApi.generatOrderNumber(currentUser.getStoreId()));
                    modelMap.put(StringConstants.CITY_LIST , cityInfoApi.list());
                    modelMap.put(StringConstants.ORDER_ERROR, error);
                    modelMap.put(StringConstants.ORDER, orderInfoDTO);

                    return "order/addSale";
                }*/

                orderReturnInfo = orderReturnInfoApi.save(orderReturnInfo);

                redirectAttributes.addFlashAttribute(StringConstants.MESSAGE , "order return saved successfullly");
            }

        } catch (Exception e) {
            logger.error("Exception on order controller : " + Arrays.toString(e.getStackTrace()));

            e.printStackTrace();
            return "redirect:/";
        }
        return "redirect:/orderreturn/add";
    }

}
