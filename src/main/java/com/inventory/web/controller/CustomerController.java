package com.inventory.web.controller;

import com.inventory.core.api.iapi.ICityInfoApi;
import com.inventory.core.api.iapi.IClientInfoApi;
import com.inventory.core.api.iapi.IUserApi;
import com.inventory.core.model.dto.ClientInfoDTO;
import com.inventory.core.model.dto.InvUserDTO;
import com.inventory.core.model.enumconstant.ClientType;
import com.inventory.core.model.enumconstant.Permission;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.util.Authorities;
import com.inventory.core.validation.ClientInfoValidation;
import com.inventory.web.error.ClientInfoError;
import com.inventory.web.util.AuthenticationUtil;
import com.inventory.web.util.PageInfo;
import com.inventory.web.util.StringConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;

/**
 * @author dhiraj
 */
@Controller
public class CustomerController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IClientInfoApi clientInfoApi;

    @Autowired
    private IUserApi userApi;

    @Autowired
    private ICityInfoApi cityInfoApi;

    @Autowired
    private ClientInfoValidation clientInfoValidation;

    @GetMapping(value = "/")
    public String index(RedirectAttributes redirectAttributes) {


        return "redirect:/customer/list";
    }

    @GetMapping(value = "/customer/list")
    public String listCustomer(@RequestParam(value = "pageNo", required = false) Integer page, ModelMap modelMap, RedirectAttributes redirectAttributes) {

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

            long totalList = clientInfoApi.countByStatusAndClientType(Status.ACTIVE, ClientType.CUSTOMER);

            int totalpage = (int) Math.ceil(totalList / PageInfo.pageList);

            if (currentpage > totalpage || currentpage < 0) {
                currentpage = 0;
            }

            List<Integer> pagesnumbers = PageInfo.PageLimitCalculator(page, totalpage, PageInfo.numberOfPage);

            modelMap.put(StringConstants.CUSTOMER_LIST, clientInfoApi.list(Status.ACTIVE, ClientType.CUSTOMER, currentpage, (int) PageInfo.pageList));
            modelMap.put("lastpage", totalpage);
            modelMap.put("currentpage", page);
            modelMap.put("pagelist", pagesnumbers);

        } catch (Exception e) {
            logger.error("Exception on client controller : " + Arrays.toString(e.getStackTrace()));
            return "redirect:/500";
        }

        return "customer/list";
    }

    @GetMapping(value = "/customer/add")
    public String addCustomer(ModelMap modelMap, RedirectAttributes redirectAttributes) {

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
            modelMap.put(StringConstants.CITY_LIST, cityInfoApi.list());

        } catch (Exception e) {
            logger.error("Exception on client controller : " + Arrays.toString(e.getStackTrace()));
            return "redirect:/500";
        }
        return "customer/add";
    }

    @PostMapping(value = "/customer/save")
    public String saveCustomer(@RequestAttribute("customer") ClientInfoDTO clientInfoDTO, ModelMap modelMap, RedirectAttributes redirectAttributes) {

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

            ClientInfoError error = clientInfoValidation.onSave(clientInfoDTO);

            if (!error.isValid()) {
                modelMap.put(StringConstants.CITY_LIST, cityInfoApi.list());
                modelMap.put(StringConstants.CUSTOMER, clientInfoDTO);
                modelMap.put(StringConstants.CUSTOMER_ERROR, error);
                return "customer/add";
            }

            clientInfoDTO.setClientType(ClientType.CUSTOMER);

            clientInfoApi.save(clientInfoDTO);

        } catch (Exception e) {
            logger.error("Exception on client controller : " + Arrays.toString(e.getStackTrace()));
            return "redirect:/500";
        }
        return "redirect:/customer/list";
    }


    @GetMapping(value = "/vendor/list")
    public String listVendor(@RequestParam(value = "pageNo", required = false) Integer page, @RequestParam(value = "sort", required = false) String sort, @RequestParam(value = "direction", required = false) String direction, ModelMap modelMap, RedirectAttributes redirectAttributes) {

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

            long totalList = clientInfoApi.countByStatusAndClientType(Status.ACTIVE, ClientType.VENDOR);

            int totalpage = (int) Math.ceil(totalList / PageInfo.pageList);

            if (currentpage > totalpage || currentpage < 0) {
                currentpage = 0;
            }

            List<Integer> pagesnumbers = PageInfo.PageLimitCalculator(page, totalpage, PageInfo.numberOfPage);

            modelMap.put(StringConstants.VENDOR_LIST, clientInfoApi.list(Status.ACTIVE, ClientType.VENDOR, currentpage, (int) PageInfo.pageList));
            modelMap.put("lastpage", totalpage);
            modelMap.put("currentpage", page);
            modelMap.put("pagelist", pagesnumbers);

        } catch (Exception e) {
            logger.error("Exception on category controller : " + Arrays.toString(e.getStackTrace()));
            return "redirect:/500";
        }

        return "vendor/list";
    }

    @GetMapping(value = "/vendor/add")
    public String addVendor(ModelMap modelMap, RedirectAttributes redirectAttributes) {

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
            modelMap.put(StringConstants.CITY_LIST, cityInfoApi.list());

        } catch (Exception e) {
            logger.error("Exception on client controller : " + Arrays.toString(e.getStackTrace()));
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

            ClientInfoError error = clientInfoValidation.onSave(clientInfoDTO);

            if (!error.isValid()) {
                modelMap.put(StringConstants.CITY_LIST, cityInfoApi.list());
                modelMap.put(StringConstants.VENDOR, clientInfoDTO);
                modelMap.put(StringConstants.VENDOR_ERROR, error);
                return "vendor/add";
            }

            clientInfoDTO.setClientType(ClientType.VENDOR);

            clientInfoApi.save(clientInfoDTO);

        } catch (Exception e) {
            logger.error("Exception on client controller : " + Arrays.toString(e.getStackTrace()));
            return "redirect:/500";
        }
        return "redirect:/vendor/list";
    }
}
