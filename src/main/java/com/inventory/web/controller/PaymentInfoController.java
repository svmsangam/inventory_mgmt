package com.inventory.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
import com.inventory.web.util.LoggerUtil;
import com.inventory.web.util.StringConstants;

/**
 * Created by dhiraj on 10/10/17.
 */
@Controller
@RequestMapping("paymentinfo")
public class PaymentInfoController {

	@Autowired
	private IUserApi userApi;

	@Autowired
	private IPaymentInfoApi paymentInfoApi;

	@Autowired
	private IInvoiceInfoApi invoiceInfoApi;

	@Autowired
	private PaymentInfoValidation paymentInfoValidation;

	@GetMapping(value = "/add")
	@PreAuthorize("hasAnyRole('ROLE_SUPERADMINISTRATOR','ROLE_ADMINISTRATOR','ROLE_USER,ROLE_AUTHENTICATED')")
	public String add(@RequestParam("invoiceId") Long invoiceId, ModelMap modelMap,
			RedirectAttributes redirectAttributes) {

		try {

			/* current user checking start */
			InvUserDTO currentUser = AuthenticationUtil.getCurrentUser(userApi);

			if (currentUser.getUserauthority().contains(Authorities.USER)
					& !AuthenticationUtil.checkPermission(currentUser, Permission.PAYMENT_CREATE)) {
				redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Access deniled");
				return "redirect:/";// access deniled page
			}

			if (currentUser.getStoreId() == null) {
				redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Store not assigned");
				return "redirect:/";// store not assigned page
			}

			/* current user checking end */

			if (invoiceId == null) {
				redirectAttributes.addFlashAttribute(StringConstants.ERROR, "invoice required");
				return "redirect:/invoice/listSale";// store not assigned page
			}

			if (invoiceId <= 0) {
				redirectAttributes.addFlashAttribute(StringConstants.ERROR, "invoice required");
				return "redirect:/invoice/listSale";// store not assigned page
			}

			InvoiceInfoDTO invoiceInfoDTO = invoiceInfoApi.show(invoiceId, currentUser.getStoreId(), Status.ACTIVE);

			if (invoiceInfoDTO == null) {
				redirectAttributes.addFlashAttribute(StringConstants.ERROR, "invoice not found");
				return "redirect:/invoice/listSale";// store not assigned page
			}

			modelMap.put(StringConstants.INVOICE, invoiceInfoDTO);

			List<Status> statusList = new ArrayList<>();

			statusList.add(Status.ACTIVE);
			statusList.add(Status.INACTIVE);

			modelMap.put(StringConstants.PAYMENTLIST, paymentInfoApi.getAllByStatusInAndStoreAndInvoiceInfo(statusList,
					currentUser.getStoreId(), invoiceId));

			modelMap.put(StringConstants.PAYMENTMETHODLIST, PaymentMethod.values());

		} catch (Exception e) {

			LoggerUtil.logException(this.getClass(), e);
			return "redirect:/500";
		}

		return "payment/add";
	}

	@PostMapping(value = "/save")
	@PreAuthorize("hasAnyRole('ROLE_SUPERADMINISTRATOR','ROLE_ADMINISTRATOR','ROLE_USER,ROLE_AUTHENTICATED')")
	public String save(@ModelAttribute("paymentInfo") PaymentInfoDTO paymentInfoDTO, BindingResult bindingResult,
			ModelMap modelMap, RedirectAttributes redirectAttributes) {

		try {

			/* current user checking start */
			InvUserDTO currentUser = AuthenticationUtil.getCurrentUser(userApi);

			if (currentUser.getUserauthority().contains(Authorities.USER)
					& !AuthenticationUtil.checkPermission(currentUser, Permission.PAYMENT_CREATE)) {
				redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Access deniled");
				return "redirect:/";// access deniled page
			}

			if (currentUser.getStoreId() == null) {
				redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Store not assigned");
				return "redirect:/";// store not assigned page
			}

			/* current user checking end */

			if (paymentInfoDTO == null) {
				redirectAttributes.addFlashAttribute(StringConstants.ERROR, "bad request");
				return "redirect:/invoice/list";
			}

			if (paymentInfoDTO.getInvoiceInfoId() <= 0) {
				redirectAttributes.addFlashAttribute(StringConstants.ERROR, "bad request");
				return "redirect:/invoice/list";
			}

			synchronized (this.getClass()) {
				paymentInfoDTO.setStoreInfoId(currentUser.getStoreId());
				paymentInfoDTO.setCreatedById(currentUser.getUserId());

				PaymentInfoError error = paymentInfoValidation.onSave(paymentInfoDTO, currentUser.getStoreId(),
						paymentInfoDTO.getInvoiceInfoId(), bindingResult);

				if (!error.isValid()) {
					modelMap.put(StringConstants.PAYMENTERROR, error);
					modelMap.put(StringConstants.PAYMENT, paymentInfoDTO);
					modelMap.put(StringConstants.INVOICE, invoiceInfoApi.show(paymentInfoDTO.getInvoiceInfoId(),
							currentUser.getStoreId(), Status.ACTIVE));

					modelMap.put(StringConstants.PAYMENTMETHODLIST, PaymentMethod.values());

					List<Status> statusList = new ArrayList<>();

					statusList.add(Status.ACTIVE);
					statusList.add(Status.INACTIVE);

					modelMap.put(StringConstants.PAYMENTLIST, paymentInfoApi.getAllByStatusInAndStoreAndInvoiceInfo(
							statusList, currentUser.getStoreId(), paymentInfoDTO.getInvoiceInfoId()));

					return "payment/add";
				}

				invoiceInfoApi.savePayment(paymentInfoDTO);
			}

		} catch (Exception e) {
			LoggerUtil.logException(this.getClass(), e);
			return "redirect:/500";
		}

		redirectAttributes.addFlashAttribute(StringConstants.MESSAGE, "payment made successfully");
		return "redirect:/paymentinfo/add?invoiceId=" + paymentInfoDTO.getInvoiceInfoId();
	}

	@GetMapping(value = "chuque/collect")
	@PreAuthorize("hasAnyRole('ROLE_SUPERADMINISTRATOR','ROLE_ADMINISTRATOR','ROLE_USER,ROLE_AUTHENTICATED')")
	public String collectChuque(@RequestParam("paymentId") Long paymentId, ModelMap modelMap,
			RedirectAttributes redirectAttributes) {

		try {

			/* current user checking start */
			InvUserDTO currentUser = AuthenticationUtil.getCurrentUser(userApi);

			if (currentUser.getUserauthority().contains(Authorities.USER)
					& !AuthenticationUtil.checkPermission(currentUser, Permission.PAYMENT_UPDATE)) {
				redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Access deniled");
				return "redirect:/";// access deniled page
			}

			if (currentUser.getStoreId() == null) {
				redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Store not assigned");
				return "redirect:/";// store not assigned page
			}

			/* current user checking end */

			if (paymentId == null) {
				redirectAttributes.addFlashAttribute(StringConstants.ERROR, "payment required");
				return "redirect:/invoice/list";// store not assigned page
			}

			if (paymentId <= 0) {
				redirectAttributes.addFlashAttribute(StringConstants.ERROR, "payment required");
				return "redirect:/invoice/list";// store not assigned page
			}

			synchronized (this.getClass()) {

				PaymentInfoDTO paymentInfoDTO = paymentInfoApi.getByIdAndStatus(paymentId, Status.INACTIVE);

				if (paymentInfoDTO == null) {
					redirectAttributes.addFlashAttribute(StringConstants.ERROR, "payment not found");
					return "redirect:/invoice/list";// store not assigned page
				}

				InvoiceInfoDTO invoiceInfoDTO = invoiceInfoApi.show(paymentInfoDTO.getInvoiceInfoId(),
						currentUser.getStoreId(), Status.ACTIVE);

				if (invoiceInfoDTO == null) {
					redirectAttributes.addFlashAttribute(StringConstants.ERROR, "payment not found");
					return "redirect:/invoice/list";// store not assigned page
				}

				if (invoiceInfoDTO.getReceivableAmount() < paymentInfoDTO.getReceivedPayment().getAmount()) {
					redirectAttributes.addFlashAttribute(StringConstants.ERROR,
							"amount greater than receivable amount");
					return "redirect:/paymentinfo/add?invoiceId=" + paymentInfoDTO.getInvoiceInfoId();
				}

				long invoiceId = invoiceInfoApi.collectCheque(paymentId);

				return "redirect:/paymentinfo/add?invoiceId=" + invoiceId;
			}

		} catch (Exception e) {
			LoggerUtil.logException(this.getClass(), e);
			return "redirect:/500";
		}

	}
}
