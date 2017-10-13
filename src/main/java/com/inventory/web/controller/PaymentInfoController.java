package com.inventory.web.controller;

import com.inventory.core.api.iapi.IInvoiceInfoApi;
import com.inventory.core.api.iapi.IPaymentInfoApi;
import com.inventory.core.api.iapi.IUserApi;
import com.inventory.core.model.dto.InvUserDTO;
import com.inventory.core.model.dto.InvoiceInfoDTO;
import com.inventory.core.model.dto.PaymentInfoDTO;
import com.inventory.core.model.enumconstant.PaymentMethod;
import com.inventory.core.model.enumconstant.Permission;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.util.Authorities;
import com.inventory.core.validation.PaymentInfoValidation;
import com.inventory.web.error.PaymentInfoError;
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
import java.util.List;

/**
 * Created by dhiraj on 10/10/17.
 */
@Controller
@RequestMapping("paymentinfo")
public class PaymentInfoController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IUserApi userApi;

    @Autowired
    private IPaymentInfoApi paymentInfoApi;

    @Autowired
    private IInvoiceInfoApi invoiceInfoApi;

    @Autowired
    private PaymentInfoValidation paymentInfoValidation;

    @GetMapping(value = "/add")
    public String add(@RequestParam("invoiceId") Long invoiceId, ModelMap modelMap, RedirectAttributes redirectAttributes) {

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

            if (currentUser.getUserauthority().contains(Authorities.USER) & !AuthenticationUtil.checkPermission(currentUser, Permission.PAYMENT_CREATE)) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Access deniled");
                return "redirect:/";//access deniled page
            }

            if (currentUser.getStoreId() == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Store not assigned");
                return "redirect:/";//store not assigned page
            }

        /*current user checking end*/

            if (invoiceId == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "invoice required");
                return "redirect:/invoice/listSale";//store not assigned page
            }

            if (invoiceId <= 0) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "invoice required");
                return "redirect:/invoice/listSale";//store not assigned page
            }

            InvoiceInfoDTO invoiceInfoDTO = invoiceInfoApi.show(invoiceId, currentUser.getStoreId(), Status.ACTIVE);

            if (invoiceInfoDTO == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "invoice not found");
                return "redirect:/invoice/listSale";//store not assigned page
            }

            modelMap.put(StringConstants.INVOICE, invoiceInfoDTO);

            List<Status> statusList = new ArrayList<>();

            statusList.add(Status.ACTIVE);
            statusList.add(Status.INACTIVE);

            modelMap.put(StringConstants.PAYMENTLIST , paymentInfoApi.getAllByStatusInAndStoreAndInvoiceInfo(statusList , currentUser.getStoreId() , invoiceId));

            modelMap.put(StringConstants.PAYMENTMETHODLIST , PaymentMethod.values());

        } catch (Exception e) {

            logger.error("Exception on product controller : " + Arrays.toString(e.getStackTrace()));
            return "redirect:/500";
        }

        return "payment/add";
    }

    @PostMapping(value = "/save")
    public String save(@ModelAttribute("paymentInfo") PaymentInfoDTO paymentInfoDTO, BindingResult bindingResult, ModelMap modelMap, RedirectAttributes redirectAttributes) {

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

            if (currentUser.getUserauthority().contains(Authorities.USER) & ! AuthenticationUtil.checkPermission(currentUser, Permission.PAYMENT_CREATE)) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Access deniled");
                return "redirect:/";//access deniled page
            }

            if (currentUser.getStoreId() == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Store not assigned");
                return "redirect:/";//store not assigned page
            }

        /*current user checking end*/

            if (paymentInfoDTO == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "bad request");
                return "redirect:/invoice/list";
            }

            if (paymentInfoDTO.getInvoiceInfoId() <= 0){
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "bad request");
                return "redirect:/invoice/list";
            }

            paymentInfoDTO.setStoreInfoId(currentUser.getStoreId());
            paymentInfoDTO.setCreatedById(currentUser.getUserId());

            PaymentInfoError error = paymentInfoValidation.onSave(paymentInfoDTO  , currentUser.getStoreId() , paymentInfoDTO.getInvoiceInfoId(), bindingResult);

            if (!error.isValid()) {
                modelMap.put(StringConstants.PAYMENTERROR, error);
                modelMap.put(StringConstants.PAYMENT, paymentInfoDTO);
                modelMap.put(StringConstants.INVOICE, invoiceInfoApi.show(paymentInfoDTO.getInvoiceInfoId() , currentUser.getStoreId() , Status.ACTIVE));

                modelMap.put(StringConstants.PAYMENTMETHODLIST , PaymentMethod.values());

                List<Status> statusList = new ArrayList<>();

                statusList.add(Status.ACTIVE);
                statusList.add(Status.INACTIVE);

                modelMap.put(StringConstants.PAYMENTLIST , paymentInfoApi.getAllByStatusInAndStoreAndInvoiceInfo(statusList , currentUser.getStoreId() , paymentInfoDTO.getInvoiceInfoId()));


                return "payment/add";
            }

            paymentInfoApi.save(paymentInfoDTO);

        } catch (Exception e) {

            logger.error("Exception on payment controller : " + Arrays.toString(e.getStackTrace()));
            return "redirect:/500";
        }

        redirectAttributes.addFlashAttribute(StringConstants.MESSAGE , "payment made successfully");
        return "redirect:/paymentinfo/add?invoiceId=" + paymentInfoDTO.getInvoiceInfoId();
    }

    @GetMapping(value = "chuque/collect")
    public String collectChuque(@RequestParam("paymentId") Long paymentId, ModelMap modelMap, RedirectAttributes redirectAttributes) {

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

            if (currentUser.getUserauthority().contains(Authorities.USER) & !AuthenticationUtil.checkPermission(currentUser, Permission.PAYMENT_UPDATE)) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Access deniled");
                return "redirect:/";//access deniled page
            }

            if (currentUser.getStoreId() == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Store not assigned");
                return "redirect:/";//store not assigned page
            }

        /*current user checking end*/

            if (paymentId == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "payment required");
                return "redirect:/invoice/list";//store not assigned page
            }

            if (paymentId <= 0) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "payment required");
                return "redirect:/invoice/list";//store not assigned page
            }

            PaymentInfoDTO paymentInfoDTO = paymentInfoApi.getByIdAndStatus(paymentId , Status.INACTIVE);

            if (paymentInfoDTO == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "payment not found");
                return "redirect:/invoice/list";//store not assigned page
            }

            InvoiceInfoDTO invoiceInfoDTO = invoiceInfoApi.show(paymentInfoDTO.getInvoiceInfoId() , currentUser.getStoreId() , Status.ACTIVE);

            if (invoiceInfoDTO == null){
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "payment not found");
                return "redirect:/invoice/list";//store not assigned page
            }

            if (invoiceInfoDTO.getReceivableAmount() < paymentInfoDTO.getReceivedPayment().getAmount()){
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "amount greater than receivable amount");
                return "redirect:/paymentinfo/add?invoiceId=" + paymentInfoDTO.getInvoiceInfoId();
            }

            long invoiceId = paymentInfoApi.collectChuque(paymentId);

            return "redirect:/paymentinfo/add?invoiceId=" + invoiceId;

        } catch (Exception e) {

            logger.error("Exception on product controller : " + Arrays.toString(e.getStackTrace()));
            return "redirect:/500";
        }

    }
}
