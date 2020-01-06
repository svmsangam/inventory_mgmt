package com.inventory.web.controller;

import com.inventory.core.api.iapi.*;
import com.inventory.core.model.dto.AccountInfoDTO;
import com.inventory.core.model.dto.ClientInfoDTO;
import com.inventory.core.model.dto.InvUserDTO;
import com.inventory.core.model.enumconstant.AccountAssociateType;
import com.inventory.core.model.enumconstant.ClientType;
import com.inventory.core.model.enumconstant.Permission;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.util.Authorities;
import com.inventory.core.validation.ClientInfoValidation;
import com.inventory.web.error.ClientInfoError;
import com.inventory.web.session.RequestCacheUtil;
import com.inventory.web.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * @author dhiraj
 */
@Controller
public class CustomerController {

    @Autowired
    private IClientInfoApi clientInfoApi;

    @Autowired
    private IUserApi userApi;

    @Autowired
    private ICityInfoApi cityInfoApi;

    @Autowired
    private ClientInfoValidation clientInfoValidation;

    @Autowired
    private IInvoiceInfoApi invoiceInfoApi;

    @Autowired
    private IAccountInfoApi accountInfoApi;

    @Autowired
    private IOrderInfoApi orderInfoApi;

    @GetMapping(value = "/customer/list")
    public String listCustomer(@RequestParam(value = "pageNo", required = false) Integer page, ModelMap modelMap, RedirectAttributes redirectAttributes, HttpServletRequest request , HttpServletResponse response) {

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

            if (currentUser.getUserauthority().contains(Authorities.USER) & !AuthenticationUtil.checkPermission(currentUser, Permission.CLIENT_VIEW)) {
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

            long totalList = clientInfoApi.countByStatusAndClientType(Status.ACTIVE, ClientType.CUSTOMER , currentUser.getStoreId());

            int totalpage = (int) Math.ceil(totalList / PageInfo.pageList);

            if (currentpage > totalpage || currentpage < 0) {
                currentpage = 0;
            }

            List<Integer> pagesnumbers = PageInfo.PageLimitCalculator(page, totalpage, PageInfo.numberOfPage);

            modelMap.put(StringConstants.CUSTOMER_LIST, clientInfoApi.list(Status.ACTIVE, ClientType.CUSTOMER, currentpage, (int) PageInfo.pageList , currentUser.getStoreId()));
            modelMap.put("lastpage", totalpage);
            modelMap.put("currentpage", page);
            modelMap.put("pagelist", pagesnumbers);

        } catch (Exception e) {
            LoggerUtil.logException(this.getClass() , e);
            return "redirect:/500";
        }

        return "customer/list";
    }

    @GetMapping(value = "/customer/show")
    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRATOR' , 'ROLE_SUPERADMINISTRATOR' , 'CLIENT_VIEW')")
    public String showCustomer(@RequestParam("customerId") Long clientId, ModelMap modelMap, RedirectAttributes redirectAttributes, HttpServletRequest request , HttpServletResponse response) {

        try {

            /*current user checking start*/
            InvUserDTO currentUser = AuthenticationUtil.getCurrentUser(userApi);

            if (currentUser == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Athentication failed");

                RequestCacheUtil.save(request , response);

                return "redirect:/login";
            }

            if (currentUser.getStoreId() == null) {
                redirectAttributes.addFlashAttribute(StringConstants.INFO, UIUtil.addStoreMessage());
                return "redirect:/store/list";//store not assigned page
            }

            /*current user checking end*/

            if (clientId == null){
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "bad request");
                return "redirect:/customer/list";
            }

            if (clientId <= 0){
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "bad request");
                return "redirect:/customer/list";
            }

            ClientInfoDTO clientInfoDTO = clientInfoApi.show(Status.ACTIVE , clientId , currentUser.getStoreId());

            if (clientInfoDTO == null){
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "provided customer not found");
                return "redirect:/customer/list";
            }

            modelMap.put(StringConstants.CUSTOMER , clientInfoDTO);
            modelMap.put(StringConstants.ACCOUNT , accountInfoApi.getByAssociateIdAndAccountAssociateType(clientId , AccountAssociateType.CUSTOMER));
            modelMap.put(StringConstants.ORDER_LIST , orderInfoApi.getAllOrderListOfCustomer(Status.ACTIVE,  currentUser.getStoreId(), clientId, 0,  500));
            modelMap.put(StringConstants.INVOICE_LIST , invoiceInfoApi.getAllReceivableByStatusAndBuyerAndStoreInfo(Status.ACTIVE,  currentUser.getStoreId(), clientId, 0,  500));

        } catch (Exception e) {
            LoggerUtil.logException(this.getClass() , e);
            return "redirect:/500";
        }

        return "customer/show";
    }

    @GetMapping(value = "/customer/search")
    public String searchCustomer(@RequestParam(value = "pageNo", required = false) Integer page, @RequestParam("q")String q , ModelMap modelMap, RedirectAttributes redirectAttributes, HttpServletRequest request , HttpServletResponse response) {

        try {

             /*current user checking start*/
            InvUserDTO currentUser = AuthenticationUtil.getCurrentUser(userApi);

            if (currentUser == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Athentication failed");

                return "redirect:/login";
            }

            if (!((currentUser.getUserauthority().contains(Authorities.SUPERADMIN) | currentUser.getUserauthority().contains(Authorities.ADMINISTRATOR) | currentUser.getUserauthority().contains(Authorities.USER)) && currentUser.getUserauthority().contains(Authorities.AUTHENTICATED))) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Athentication failed");
                return "redirect:/logout";
            }

            if (currentUser.getUserauthority().contains(Authorities.USER) & !AuthenticationUtil.checkPermission(currentUser, Permission.CLIENT_VIEW)) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Access deniled");
                return "redirect:/";//access deniled page
            }

            if (currentUser.getStoreId() == null) {
                redirectAttributes.addFlashAttribute(StringConstants.INFO, UIUtil.addStoreMessage());
                return "redirect:/store/list";//store not assigned page
            }

        /*current user checking end*/

            if (page == null) {
                page = 1;
            }

            if (page < 1) {
                page = 1;
            }

            int currentpage = page - 1;

            long totalList = clientInfoApi.searchCount(Status.ACTIVE, ClientType.CUSTOMER , q , currentUser.getStoreId());

            int totalpage = (int) Math.ceil(totalList / PageInfo.pageList);

            if (currentpage > totalpage || currentpage < 0) {
                currentpage = 0;
            }

            List<Integer> pagesnumbers = PageInfo.PageLimitCalculator(page, totalpage, PageInfo.numberOfPage);

            modelMap.put(StringConstants.CUSTOMER_LIST, clientInfoApi.search(Status.ACTIVE, ClientType.CUSTOMER, q , currentpage, (int) PageInfo.pageList , currentUser.getStoreId()));

            modelMap.put("lastpage", totalpage);
            modelMap.put("currentpage", page);
            modelMap.put("pagelist", pagesnumbers);
            modelMap.put("query" , q);

        } catch (Exception e) {
            LoggerUtil.logException(this.getClass() , e);
            return "redirect:/500";
        }

        return "customer/search";
    }

    @GetMapping(value = "/customer/add")
    public String addCustomer(ModelMap modelMap, RedirectAttributes redirectAttributes, HttpServletRequest request , HttpServletResponse response) {

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

            if (currentUser.getUserauthority().contains(Authorities.USER) & !AuthenticationUtil.checkPermission(currentUser, Permission.CLIENT_CREATE)) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Access deniled");
                return "redirect:/";//access deniled page
            }

            if (currentUser.getStoreId() == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Store not assigned");
                return "redirect:/";//store not assigned page
            }

        /*current user checking end*/
            modelMap.put(StringConstants.CITY_LIST, cityInfoApi.list());

        } catch (Exception e) {
            LoggerUtil.logException(this.getClass() , e);
            return "redirect:/500";
        }
        return "customer/add";
    }

    @PostMapping(value = "/customer/save")
    public String saveCustomer(@RequestAttribute("customer") ClientInfoDTO clientInfoDTO, ModelMap modelMap, RedirectAttributes redirectAttributes , HttpServletRequest request , HttpServletResponse response) {

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

            if (currentUser.getUserauthority().contains(Authorities.USER) & !AuthenticationUtil.checkPermission(currentUser, Permission.CLIENT_CREATE)) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Access deniled");
                return "redirect:/";//access deniled page
            }

            if (currentUser.getStoreId() == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Store not assigned");
                return "redirect:/";//store not assigned page
            }

        /*current user checking end*/

        synchronized (this.getClass()) {
            clientInfoDTO.setClientType(ClientType.CUSTOMER);
            clientInfoDTO.setCreatedById(currentUser.getUserId());
            clientInfoDTO.setStoreInfoId(currentUser.getStoreId());

            ClientInfoError error = clientInfoValidation.onSave(clientInfoDTO);

            if (!error.isValid()) {
                modelMap.put(StringConstants.CITY_LIST, cityInfoApi.list());
                modelMap.put(StringConstants.CUSTOMER, clientInfoDTO);
                modelMap.put(StringConstants.CUSTOMER_ERROR, error);
                return "customer/add";
            }

            clientInfoApi.save(clientInfoDTO);
        }

        } catch (Exception e) {
            LoggerUtil.logException(this.getClass() , e);
            return "redirect:/500";
        }
        return "redirect:/customer/list";
    }


    @GetMapping(value = "/vendor/list")
    public String listVendor(@RequestParam(value = "pageNo", required = false) Integer page, @RequestParam(value = "sort", required = false) String sort, @RequestParam(value = "direction", required = false) String direction, ModelMap modelMap, RedirectAttributes redirectAttributes, HttpServletRequest request , HttpServletResponse response) {

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

            if (currentUser.getUserauthority().contains(Authorities.USER) & !AuthenticationUtil.checkPermission(currentUser, Permission.CLIENT_VIEW)) {
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

            long totalList = clientInfoApi.countByStatusAndClientType(Status.ACTIVE, ClientType.VENDOR , currentUser.getStoreId());

            int totalpage = (int) Math.ceil(totalList / PageInfo.pageList);

            if (currentpage > totalpage || currentpage < 0) {
                currentpage = 0;
            }

            List<Integer> pagesnumbers = PageInfo.PageLimitCalculator(page, totalpage, PageInfo.numberOfPage);

            modelMap.put(StringConstants.VENDOR_LIST, clientInfoApi.list(Status.ACTIVE, ClientType.VENDOR, currentpage, (int) PageInfo.pageList , currentUser.getStoreId()));
            modelMap.put("lastpage", totalpage);
            modelMap.put("currentpage", page);
            modelMap.put("pagelist", pagesnumbers);

        } catch (Exception e) {
            LoggerUtil.logException(this.getClass() , e);
            return "redirect:/500";
        }

        return "vendor/list";
    }

    @GetMapping(value = "/vendor/add")
    public String addVendor(ModelMap modelMap, RedirectAttributes redirectAttributes, HttpServletRequest request , HttpServletResponse response) {

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

            if (currentUser.getUserauthority().contains(Authorities.USER) & !AuthenticationUtil.checkPermission(currentUser, Permission.CLIENT_CREATE)) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Access deniled");
                return "redirect:/";//access deniled page
            }

            if (currentUser.getStoreId() == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Store not assigned");
                return "redirect:/";//store not assigned page
            }

        /*current user checking end*/
            modelMap.put(StringConstants.CITY_LIST, cityInfoApi.list());

        } catch (Exception e) {
            LoggerUtil.logException(this.getClass() , e);
            return "redirect:/500";
        }
        return "vendor/add";
    }

    @PostMapping(value = "/vendor/save")
    public String saveVendor(@RequestAttribute("customer") ClientInfoDTO clientInfoDTO, ModelMap modelMap, RedirectAttributes redirectAttributes) {

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

            if (currentUser.getUserauthority().contains(Authorities.USER) & !AuthenticationUtil.checkPermission(currentUser, Permission.CLIENT_CREATE)) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Access deniled");
                return "redirect:/";//access deniled page
            }

            if (currentUser.getStoreId() == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Store not assigned");
                return "redirect:/";//store not assigned page
            }

        /*current user checking end*/

        synchronized (this.getClass()) {

            clientInfoDTO.setClientType(ClientType.VENDOR);
            clientInfoDTO.setCreatedById(currentUser.getUserId());
            clientInfoDTO.setStoreInfoId(currentUser.getStoreId());

            ClientInfoError error = clientInfoValidation.onSave(clientInfoDTO);

            if (!error.isValid()) {
                modelMap.put(StringConstants.CITY_LIST, cityInfoApi.list());
                modelMap.put(StringConstants.VENDOR, clientInfoDTO);
                modelMap.put(StringConstants.VENDOR_ERROR, error);
                return "vendor/add";
            }

            clientInfoApi.save(clientInfoDTO);
        }

        } catch (Exception e) {
            LoggerUtil.logException(this.getClass() , e);
            return "redirect:/500";
        }
        return "redirect:/vendor/list";
    }
}
