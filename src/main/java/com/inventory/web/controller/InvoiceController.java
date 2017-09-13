package com.inventory.web.controller;

import com.inventory.core.api.iapi.IInvoiceInfoApi;
import com.inventory.core.api.iapi.IOrderItemInfoApi;
import com.inventory.core.api.iapi.IUserApi;
import com.inventory.core.model.dto.InvUserDTO;
import com.inventory.core.model.dto.InvoiceInfoDTO;
import com.inventory.core.model.enumconstant.Permission;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.util.Authorities;
import com.inventory.web.util.AuthenticationUtil;
import com.inventory.web.util.PageInfo;
import com.inventory.web.util.StringConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/invoice")
public class InvoiceController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IUserApi userApi;

    @Autowired
    private IInvoiceInfoApi invoiceInfoApi;

    @Autowired
    private IOrderItemInfoApi orderItemInfoApi;


    @GetMapping(value = "/")
    public String index() {

        return "redirect:/invoice/list";
    }

    @GetMapping(value = "/list")
    public String list(@RequestParam(value = "pageNo", required = false) Integer page, @RequestParam(value = "sort", required = false) String sort, @RequestParam(value = "direction", required = false) String direction, ModelMap modelMap, RedirectAttributes redirectAttributes) {

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

            if (page == null) {
                page = 1;
            }

            if (page < 1) {
                page = 1;
            }

            int currentpage = page - 1;

            long totalList = invoiceInfoApi.countlist(Status.ACTIVE, currentUser.getStoreId());

            int totalpage = (int) Math.ceil(totalList / PageInfo.pageList);

            if (currentpage > totalpage || currentpage < 0) {
                currentpage = 0;
            }

            List<Integer> pagesnumbers = PageInfo.PageLimitCalculator(page, totalpage, PageInfo.numberOfPage);

            modelMap.put(StringConstants.INVOICE_LIST, invoiceInfoApi.list(Status.ACTIVE, currentUser.getStoreId(), currentpage, (int) PageInfo.pageList));
            modelMap.put("lastpage", totalpage);
            modelMap.put("currentpage", page);
            modelMap.put("pagelist", pagesnumbers);

        } catch (Exception e) {
            logger.error("Exception on invoice controller : " + Arrays.toString(e.getStackTrace()));

            return "redirect:/";
        }
        return "invoice/list";
    }

    @GetMapping(value = "/{invoiceId}")
    public String show(@PathVariable("invoiceId") Long invoiceId, ModelMap modelMap, RedirectAttributes redirectAttributes) {

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

            if (currentUser.getUserauthority().contains(Authorities.USER) & !AuthenticationUtil.checkPermission(currentUser, Permission.INVOICE_VIEW)) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Access deniled");
                return "redirect:/";//access deniled page
            }

            if (currentUser.getStoreId() == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Store not assigned");
                return "redirect:/";//store not assigned page
            }

        /*current user checking end*/

            if (invoiceId == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Invoice not found");
                return "redirect:/invoice/list";
            }

            if (invoiceId < 0) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Invoice not found");
                return "redirect:/invoice/list";
            }

            InvoiceInfoDTO invoiceInfoDTO = invoiceInfoApi.show(invoiceId, currentUser.getStoreId() , Status.ACTIVE);

            if (invoiceInfoDTO == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Invoice not found");
                return "redirect:/order/sale/list";
            }

            modelMap.put(StringConstants.INVOICE, invoiceInfoDTO);
            modelMap.put(StringConstants.ORDER_ITEM_LIST, orderItemInfoApi.getAllByStatusAndOrderInfo(Status.ACTIVE, invoiceInfoDTO.getOrderInfoId()));


        } catch (Exception e) {
            logger.error("Exception on invoice controller : " + Arrays.toString(e.getStackTrace()));

            return "redirect:/";
        }

        return "invoice/show";
    }

    /*@GetMapping(value = "/add")
    public String add(ModelMap modelMap, RedirectAttributes redirectAttributes) {


        return "invoice/add";
    }

    @PostMapping(value = "/save")
    public String save(ModelMap modelMap, RedirectAttributes redirectAttributes) {

        try {


        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/";
        }

        return "redirect:/invoice/";
    }

    @PostMapping(value = "/generate")
    public String generate(ModelMap modelMap, @RequestParam("orderId") Long orderId, RedirectAttributes redirectAttributes) {


        return "invoice/generate";
    }

    @PostMapping(value = "/custom")
    public String saveCustom(ModelMap modelMap, RedirectAttributes redirectAttributes) {

        try {


        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/";
        }

        return "redirect:/invoice/";
    }

    @GetMapping(value = "/edit")
    public String edit() {

        return "redirect:/invoice/list";
    }

*/
    @PostMapping(value = "/payment")
    public String updatePayment(RedirectAttributes redirectAttributes) {

        try {
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/";
        }

        return "redirect:/invoice/";
    }

   /* @PostMapping(value = "/update")
    public String update() {


        return "redirect:/invoice/list";
    }*/
}



