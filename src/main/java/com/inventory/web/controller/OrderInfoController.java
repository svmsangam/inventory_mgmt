package com.inventory.web.controller;

import com.inventory.core.api.iapi.*;
import com.inventory.core.model.dto.*;
import com.inventory.core.model.enumconstant.PaymentMethod;
import com.inventory.core.model.enumconstant.Permission;
import com.inventory.core.model.enumconstant.SalesOrderStatus;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.util.Authorities;
import com.inventory.core.validation.OrderValidation;
import com.inventory.core.validation.PaymentInfoValidation;
import com.inventory.web.error.OrderError;
import com.inventory.web.error.PaymentInfoError;
import com.inventory.web.session.RequestCacheUtil;
import com.inventory.web.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    @Autowired
    private IInvoiceInfoApi invoiceInfoApi;

    @Autowired
    private OrderValidation orderValidation;

    @Autowired
    private IFiscalYearInfoApi fiscalYearInfoApi;

    @Autowired
    private ICityInfoApi cityInfoApi;

    @Autowired
    private PaymentInfoValidation paymentInfoValidation;

    @Autowired
    private ISendMailSSL sendMailSSL;

    @Autowired
    private INotificationApi notificationApi;

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

            if (currentUser.getUserauthority().contains(Authorities.USER) & !AuthenticationUtil.checkPermission(currentUser, Permission.SALES_ORDER_VIEW)) {
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

            if (page == null) {
                page = 1;
            }

            if (page < 1) {
                page = 1;
            }

            int currentpage = page - 1;

            long totalList = orderInfoApi.countListSale(Status.ACTIVE, currentUser.getStoreId());

            int totalpage = (int) Math.ceil(totalList / PageInfo.pageList);

            if (currentpage > totalpage || currentpage < 0) {
                currentpage = 0;
            }

            List<Integer> pagesnumbers = PageInfo.PageLimitCalculator(page, totalpage, PageInfo.numberOfPage);

            modelMap.put(StringConstants.ORDER_LIST, orderInfoApi.listSale(Status.ACTIVE, currentUser.getStoreId(), currentpage, (int) PageInfo.pageList));
            modelMap.put("lastpage", totalpage);
            modelMap.put("currentpage", page);
            modelMap.put("pagelist", pagesnumbers);
            modelMap.put(StringConstants.FISCAL_YEAR_LIST , fiscalYearInfoApi.list(Status.ACTIVE , currentUser.getStoreId() , 0 , 100));
            modelMap.put(StringConstants.SALE_TRACK_LIST , SalesOrderStatus.values());


        } catch (Exception e) {
            LoggerUtil.logException(this.getClass() , e);

            return "redirect:/";
        }

        return "order/listSale";
    }

    @GetMapping(value = "/sale/filter")
    public String filter(@ModelAttribute("filter")OrderFilterDTO filterDTO , BindingResult bindingResult , ModelMap modelMap, RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response) {

        try {

            /*current user checking start*/
            InvUserDTO currentUser = AuthenticationUtil.getCurrentUser(userApi);

            if (currentUser == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Athentication failed");

                RequestCacheUtil.save(request, response);

                return "redirect:/login";
            }

            if (!((currentUser.getUserauthority().contains(Authorities.SUPERADMIN) | currentUser.getUserauthority().contains(Authorities.ADMINISTRATOR) | currentUser.getUserauthority().contains(Authorities.USER)) && currentUser.getUserauthority().contains(Authorities.AUTHENTICATED))) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Athentication failed");
                return "redirect:/logout";
            }

            if (currentUser.getUserauthority().contains(Authorities.USER) & !AuthenticationUtil.checkPermission(currentUser, Permission.INVOICE_VIEW)) {
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

            if (filterDTO == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Invoice not found");
                return "redirect:/sale/list";
            }

            Integer page = filterDTO.getPageNo();

            filterDTO.setStatus(Status.ACTIVE);
            filterDTO.setStoreId(currentUser.getStoreId());

            if (page == null) {
                page = 1;
            }

            if (page < 1) {
                page = 1;
            }

            int currentpage = page - 1;

            long totalList = orderInfoApi.filterCount(filterDTO);//invoiceInfoApi.countAllByStatusAndStoreInfoAndInvoiceDateBetween(Status.ACTIVE, currentUser.getStoreId(), from, to);

            int totalpage = (int) Math.ceil(totalList / PageInfo.pageList);

            if (currentpage > totalpage || currentpage < 0) {
                currentpage = 0;
            }

            List<Integer> pagesnumbers = PageInfo.PageLimitCalculator(page, totalpage, PageInfo.numberOfPage);

            filterDTO.setPageNo(currentpage);
            filterDTO.setSize((int) PageInfo.pageList);

            modelMap.put(StringConstants.ORDER_LIST, orderInfoApi.filter(filterDTO));
            modelMap.put(StringConstants.SALE_TRACK_LIST , SalesOrderStatus.values());

            modelMap.put("lastpage", totalpage);
            modelMap.put("currentpage", page);
            modelMap.put("pagelist", pagesnumbers);
            modelMap.put("filterDTO" , filterDTO);
            modelMap.put("totalResult" , totalList);

        } catch (Exception e) {
            LoggerUtil.logException(this.getClass() , e);
            return "redirect:/";
        }

        return "order/filter";

    }

    @GetMapping(value = "/sale/inactive")
    public String listSaleInactive(@RequestParam(value = "pageNo", required = false) Integer page, ModelMap modelMap, RedirectAttributes redirectAttributes) {

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

            FiscalYearInfoDTO currentFiscalYear = fiscalYearInfoApi.getCurrentFiscalYearByStoreInfo(currentUser.getStoreId());

            if (currentFiscalYear == null){
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

            long totalList = orderInfoApi.countListSale(Status.INACTIVE, currentUser.getStoreId());

            int totalpage = (int) Math.ceil(totalList / PageInfo.pageList);

            if (currentpage > totalpage || currentpage < 0) {
                currentpage = 0;
            }

            List<Integer> pagesnumbers = PageInfo.PageLimitCalculator(page, totalpage, PageInfo.numberOfPage);

            modelMap.put(StringConstants.ORDER_LIST, orderInfoApi.listSale(Status.INACTIVE, currentUser.getStoreId(), currentpage, (int) PageInfo.pageList));
            modelMap.put("lastpage", totalpage);
            modelMap.put("currentpage", page);
            modelMap.put("pagelist", pagesnumbers);

        } catch (Exception e) {
            LoggerUtil.logException(this.getClass() , e);

            return "redirect:/";
        }

        return "order/listSaleInactive";
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

            if (currentUser.getUserauthority().contains(Authorities.USER) & !AuthenticationUtil.checkPermission(currentUser, Permission.SALES_ORDER_CREATE)) {
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

            modelMap.put(StringConstants.ORDERNO, orderInfoApi.generatOrderNumber(currentUser.getStoreId()));
            modelMap.put(StringConstants.CITY_LIST , cityInfoApi.list());

            return "order/addSale";
        } catch (Exception e) {
            LoggerUtil.logException(this.getClass() , e);

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

            if (currentUser.getUserauthority().contains(Authorities.USER) & !AuthenticationUtil.checkPermission(currentUser, Permission.SALES_ORDER_CREATE)) {
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

            synchronized (this.getClass()) {
                orderInfoDTO.setStoreInfoId(currentUser.getStoreId());
                orderInfoDTO.setCreatedById(currentUser.getUserId());

                OrderError error = orderValidation.onSaleSave(orderInfoDTO, bindingResult);

                if (!error.isValid()) {

                    modelMap.put(StringConstants.ORDERNO, orderInfoApi.generatOrderNumber(currentUser.getStoreId()));
                    modelMap.put(StringConstants.CITY_LIST , cityInfoApi.list());
                    modelMap.put(StringConstants.ORDER_ERROR, error);
                    modelMap.put(StringConstants.ORDER, orderInfoDTO);

                    return "order/addSale";
                }

                orderInfoDTO = orderInfoApi.save(orderInfoDTO);
                redirectAttributes.addFlashAttribute(StringConstants.MESSAGE , "order saved successfullly");

                /*if (orderInfoDTO.getClientInfo() != null) {
                    if (orderInfoDTO.getClientInfo().getEmail() != null && !orderInfoDTO.getClientInfo().getEmail().isEmpty()) {

                        sendMailSSL.sendHtmlMail("dhirajbadu50@gmail.com", orderInfoDTO.getClientInfo().getEmail(), getSendmsg(orderInfoDTO), "sale order created");
                    }
                }*/
            }

        } catch (Exception e) {
            LoggerUtil.logException(this.getClass() , e);
            return "redirect:/";
        }
        return "redirect:/order/sale/" + orderInfoDTO.getOrderId();
    }

    private String getSendmsg(OrderInfoDTO orderInfoDTO){

        String msg ="<html><head><style>div { background-color: lightblue;}</style></head><body><div>";

        msg = msg + "Dear sir/madam, \n\n";

        msg = msg + "<h1>" +orderInfoDTO.getClientInfo().getName() + "</h1>" + " your order is created of total amount : " + orderInfoDTO.getGrandTotal() + " \n\n thank you \n\n ";

        msg = msg + "</div></body></html>";
        return msg;
    }

    @GetMapping(value = "/sale/quick")
    public String addQuick(ModelMap modelMap, RedirectAttributes redirectAttributes) {

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

            if (currentUser.getUserauthority().contains(Authorities.USER) & !AuthenticationUtil.checkPermission(currentUser, Permission.INVOICE_CREATE)) {
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

            modelMap.put(StringConstants.ORDERNO, orderInfoApi.generatOrderNumber(currentUser.getStoreId()));
            modelMap.put(StringConstants.CITY_LIST , cityInfoApi.list());

            return "order/quick/add";
        } catch (Exception e) {
            LoggerUtil.logException(this.getClass() , e);

            return "redirect:/";
        }
    }

    @GetMapping(value = "/sale/cancel")
    public String cancelQuick(@RequestParam("orderId")long orderId , ModelMap modelMap, RedirectAttributes redirectAttributes) {

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

            if (currentUser.getUserauthority().contains(Authorities.USER) & !AuthenticationUtil.checkPermission(currentUser, Permission.INVOICE_CREATE)) {
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

            OrderInfoDTO orderInfoDTO = orderInfoApi.show(Status.INACTIVE , orderId , currentUser.getStoreId());

            if (orderInfoDTO == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Order not found");
                return "redirect:/order/sale/list";
            }

            synchronized (this){
                orderInfoApi.cancelQuickSale(orderId );
            }

            redirectAttributes.addFlashAttribute(StringConstants.MESSAGE, "Order canceled successfully");
            return "redirect:/order/sale/list";

        } catch (Exception e) {
            LoggerUtil.logException(this.getClass() , e);

            return "redirect:/";
        }
    }

    @GetMapping(value = "/sale/quit")
    public String quitQuick(@RequestParam("orderId")long orderId , ModelMap modelMap, RedirectAttributes redirectAttributes) {

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

            if (currentUser.getUserauthority().contains(Authorities.USER) & !AuthenticationUtil.checkPermission(currentUser, Permission.INVOICE_CREATE)) {
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

            OrderInfoDTO orderInfoDTO = orderInfoApi.show(Status.INACTIVE , orderId , currentUser.getStoreId());

            if (orderInfoDTO == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Order not found");
                return "redirect:/order/sale/inactive";
            }

            synchronized (this){
                orderInfoApi.cancelQuickSale(orderId );
            }

            redirectAttributes.addFlashAttribute(StringConstants.MESSAGE, "Order canceled successfully");
            return "redirect:/order/sale/inactive";

        } catch (Exception e) {
            LoggerUtil.logException(this.getClass() , e);

            return "redirect:/";
        }
    }

    @PostMapping(value = "/sale/quick")
    public String saveQuick(@ModelAttribute("order") OrderInfoDTO orderInfoDTO, BindingResult bindingResult, ModelMap modelMap, RedirectAttributes redirectAttributes) {

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

            if (currentUser.getUserauthority().contains(Authorities.USER) & !AuthenticationUtil.checkPermission(currentUser, Permission.INVOICE_CREATE)) {
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

            synchronized (this.getClass()) {
                orderInfoDTO.setStoreInfoId(currentUser.getStoreId());
                orderInfoDTO.setCreatedById(currentUser.getUserId());

                OrderError error = orderValidation.onSaleSave(orderInfoDTO, bindingResult);

                if (!error.isValid()) {

                    orderInfoDTO.setOrderItemInfoDTOList(orderItemInfoApi.getAllOnValidationFaild(orderInfoDTO.getOrderItemInfoDTOList() , currentUser.getStoreId()));
                    modelMap.put(StringConstants.ORDERNO, orderInfoApi.generatOrderNumber(currentUser.getStoreId()));
                    modelMap.put(StringConstants.CITY_LIST , cityInfoApi.list());
                    modelMap.put(StringConstants.ORDER_ERROR, error);
                    modelMap.put(StringConstants.ORDER, orderInfoDTO);

                    return "order/quick/add";
                }

                orderInfoDTO = orderInfoApi.saveQuickSale(orderInfoDTO);

                notificationApi.saveAndSendForSuperAdmin("order created" , "new order created " + orderInfoDTO.getOrderNo() , "/order/sale/" + orderInfoDTO.getOrderId() , currentUser.getStoreId());
            }

        } catch (Exception e) {
            LoggerUtil.logException(this.getClass() , e);
            return "redirect:/";
        }

        return "redirect:/order/sale/quick/comfirm?orderId=" + orderInfoDTO.getOrderId();
    }

    @GetMapping(value = "sale/quick/comfirm")
    public String confirmQuick(@RequestParam("orderId") Long orderId, ModelMap modelMap, RedirectAttributes redirectAttributes) {

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

            if (currentUser.getUserauthority().contains(Authorities.USER) & !AuthenticationUtil.checkPermission(currentUser, Permission.INVOICE_CREATE)) {
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

            if (orderId == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Order not found");
                return "redirect:/order/sale/quick";
            }

            if (orderId < 0) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Order not found");
                return "redirect:/order/sale/quick";
            }

            OrderInfoDTO orderInfoDTO = orderInfoApi.show(Status.INACTIVE, orderId, currentUser.getStoreId());

            if (orderInfoDTO == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Order not found");
                return "redirect:/order/sale/quick";
            }

            modelMap.put(StringConstants.ORDER, orderInfoDTO);
            modelMap.put(StringConstants.ORDER_ITEM_LIST, orderItemInfoApi.getAllByStatusAndOrderInfo(Status.ACTIVE, orderId));
            modelMap.put(StringConstants.PAYMENTMETHODLIST , PaymentMethod.values());

        } catch (Exception e) {
            LoggerUtil.logException(this.getClass() , e);

            return "redirect:/";
        }
        return "order/quick/confirm";
    }

    @PostMapping(value = "sale/quick/confirm")
    public String confirmQuick(@ModelAttribute("paymentInfo") PaymentInfoDTO paymentInfoDTO, BindingResult bindingResult, ModelMap modelMap, RedirectAttributes redirectAttributes) {

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

            if (currentUser.getUserauthority().contains(Authorities.USER) & ! AuthenticationUtil.checkPermission(currentUser, Permission.INVOICE_CREATE)) {
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

            if (paymentInfoDTO == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "bad request");
                return "redirect:/order/sale/quick";
            }

            if (paymentInfoDTO.getOrderInfoId() <= 0){
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "bad request");
                return "redirect:/order/sale/quick";
            }

            OrderInfoDTO orderInfoDTO = orderInfoApi.show(Status.INACTIVE , paymentInfoDTO.getOrderInfoId() , currentUser.getStoreId());

            if (orderInfoDTO == null){
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "bad request");
                return "redirect:/order/sale/quick";
            }

            synchronized (this.getClass()) {
                paymentInfoDTO.setStoreInfoId(currentUser.getStoreId());
                paymentInfoDTO.setCreatedById(currentUser.getUserId());

                PaymentInfoError error = paymentInfoValidation.onQuickSave(paymentInfoDTO , bindingResult);

                if (!error.isValid()) {
                    modelMap.put(StringConstants.PAYMENTERROR, error);
                    modelMap.put(StringConstants.PAYMENT, paymentInfoDTO);
                    modelMap.put(StringConstants.ORDER, orderInfoDTO);
                    modelMap.put(StringConstants.ORDER_ITEM_LIST, orderItemInfoApi.getAllByStatusAndOrderInfo(Status.ACTIVE, orderInfoDTO.getOrderId()));
                    modelMap.put(StringConstants.PAYMENTMETHODLIST , PaymentMethod.values());

                    return "order/quick/confirm";
                }

                InvoiceInfoDTO invoiceInfoDTO = invoiceInfoApi.saveQuickSale(paymentInfoDTO);

                paymentInfoDTO.setInvoiceInfoId(invoiceInfoDTO.getInvoiceId());
            }

        } catch (Exception e) {

            LoggerUtil.logException(this.getClass() , e);
            return "redirect:/500";
        }

        redirectAttributes.addFlashAttribute(StringConstants.MESSAGE , "invoice successfully created");
        return "redirect:/invoice/" + paymentInfoDTO.getInvoiceInfoId();
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
                redirectAttributes.addFlashAttribute(StringConstants.INFO, UIUtil.addStoreMessage());
                return "redirect:/store/list";//store not assigned page
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
            modelMap.put(StringConstants.INVOICE , invoiceInfoApi.getByOrderIdAndStatusAndStoreId(orderId , Status.ACTIVE , currentUser.getStoreId()));


        } catch (Exception e) {
            LoggerUtil.logException(this.getClass() , e);

            return "redirect:/500";
        }
        return "order/showSale";
    }
}



