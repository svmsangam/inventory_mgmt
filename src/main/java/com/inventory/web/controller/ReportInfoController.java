package com.inventory.web.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.inventory.core.api.iapi.IFiscalYearInfoApi;
import com.inventory.core.api.iapi.IInvoiceInfoApi;
import com.inventory.core.api.iapi.ILedgerInfoApi;
import com.inventory.core.api.iapi.IProductInfoApi;
import com.inventory.core.api.iapi.IReportServiceApi;
import com.inventory.core.api.iapi.IUserApi;
import com.inventory.core.model.dto.FiscalYearInfoDTO;
import com.inventory.core.model.dto.InvUserDTO;
import com.inventory.core.model.dto.InvoiceFilterDTO;
import com.inventory.core.model.dto.InvoiceInfoDTO;
import com.inventory.core.model.dto.LedgerFilterDTO;
import com.inventory.core.model.dto.LedgerInfoDTO;
import com.inventory.core.model.dto.ProductFilterDTO;
import com.inventory.core.model.dto.ProductInfoDTO;
import com.inventory.core.model.enumconstant.Permission;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.util.Authorities;
import com.inventory.core.util.ReportGeneratorUtil;
import com.inventory.web.util.AuthenticationUtil;
import com.inventory.web.util.LoggerUtil;
import com.inventory.web.util.PageInfo;
import com.inventory.web.util.StringConstants;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;

/**
 * Created by dhiraj on 10/9/17.
 */
@Controller
@RequestMapping("report")
public class ReportInfoController {

	@Autowired
	private IUserApi userApi;

	@Autowired
	private IFiscalYearInfoApi fiscalYearInfoApi;

	@Autowired
	private IReportServiceApi reportServiceApi;

	@Autowired
	private IInvoiceInfoApi invoiceInfoApi;

	@Autowired
	private ILedgerInfoApi ledgerInfoApi;

	@Autowired
	private IProductInfoApi productInfoApi;

	@GetMapping(value = "invoice/filter/pdf", produces = "application/pdf")
	@PreAuthorize("hasAnyRole('ROLE_SUPERADMINISTRATOR','ROLE_ADMINISTRATOR','ROLE_USER,ROLE_AUTHENTICATED')")
	public void filterPDF(@ModelAttribute("filter") InvoiceFilterDTO filterDTO, BindingResult bindingResult,
			ModelMap modelMap, RedirectAttributes redirectAttributes, HttpServletRequest request,
			HttpServletResponse response) throws ClassNotFoundException, IOException, JRException {

		try {

			/* current user checking start */
			InvUserDTO currentUser = AuthenticationUtil.getCurrentUser(userApi);

			if (currentUser.getUserauthority().contains(Authorities.USER)
					& !AuthenticationUtil.checkPermission(currentUser, Permission.INVOICE_VIEW)) {
				return;// access deniled page
			}

			if (currentUser.getStoreId() == null) {
				return;// store not assigned page
			}

			FiscalYearInfoDTO currentFiscalYear = fiscalYearInfoApi
					.getCurrentFiscalYearByStoreInfo(currentUser.getStoreId());

			if (currentFiscalYear == null) {
				return;// store not assigned page
			}

			/* current user checking end */

			if (filterDTO == null) {
				return;
			}

			Integer page = filterDTO.getPageNo();

			filterDTO.setStatus(Status.ACTIVE);
			filterDTO.setStoreInfoId(currentUser.getStoreId());

			if (page == null) {
				page = 1;
			}

			if (page < 1) {
				page = 1;
			}

			int currentpage = page - 1;

			/*
			 * long totalList = invoiceInfoApi.filterCount(filterDTO);//invoiceInfoApi.
			 * countAllByStatusAndStoreInfoAndInvoiceDateBetween(Status.ACTIVE,
			 * currentUser.getStoreId(), from, to);
			 * 
			 * int totalpage = (int) Math.ceil(totalList / PageInfo.pageList);
			 * 
			 * if (currentpage > totalpage || currentpage < 0) { currentpage = 0; }
			 * 
			 * List<Integer> pagesnumbers = PageInfo.PageLimitCalculator(page, totalpage,
			 * PageInfo.numberOfPage);
			 */
			filterDTO.setPageNo(currentpage);
			filterDTO.setSize((int) PageInfo.pageList);

			List<InvoiceInfoDTO> invoiceInfoDTOList = invoiceInfoApi.filter(filterDTO);

			ReportGeneratorUtil rp = new ReportGeneratorUtil();

			JasperPrint jp = rp.InvoiceReport(invoiceInfoDTOList, "Invoice", "Invoice " + new Date().toString());
			reportServiceApi.writePdfReport(jp, response, "invoice Report " + new Date().toString());

		} catch (Exception e) {
			LoggerUtil.logException(this.getClass(), e);
			throw e;
		}

		return;

	}

	@GetMapping(value = "invoice/filter/xls", produces = "application/xls")
	@PreAuthorize("hasAnyRole('ROLE_SUPERADMINISTRATOR','ROLE_ADMINISTRATOR','ROLE_USER,ROLE_AUTHENTICATED')")
	public void filterXLS(@ModelAttribute("filter") InvoiceFilterDTO filterDTO, BindingResult bindingResult,
			ModelMap modelMap, RedirectAttributes redirectAttributes, HttpServletRequest request,
			HttpServletResponse response) {

		try {

			/* current user checking start */
			InvUserDTO currentUser = AuthenticationUtil.getCurrentUser(userApi);

			if (currentUser.getUserauthority().contains(Authorities.USER)
					& !AuthenticationUtil.checkPermission(currentUser, Permission.INVOICE_VIEW)) {
				return;// access deniled page
			}

			if (currentUser.getStoreId() == null) {
				return;// store not assigned page
			}

			FiscalYearInfoDTO currentFiscalYear = fiscalYearInfoApi
					.getCurrentFiscalYearByStoreInfo(currentUser.getStoreId());

			if (currentFiscalYear == null) {
				return;// store not assigned page
			}

			/* current user checking end */

			if (filterDTO == null) {
				return;
			}

			Integer page = filterDTO.getPageNo();

			filterDTO.setStatus(Status.ACTIVE);
			filterDTO.setStoreInfoId(currentUser.getStoreId());

			if (page == null) {
				page = 1;
			}

			if (page < 1) {
				page = 1;
			}

			int currentpage = page - 1;

			/*
			 * long totalList = invoiceInfoApi.filterCount(filterDTO);//invoiceInfoApi.
			 * countAllByStatusAndStoreInfoAndInvoiceDateBetween(Status.ACTIVE,
			 * currentUser.getStoreId(), from, to);
			 * 
			 * int totalpage = (int) Math.ceil(totalList / PageInfo.pageList);
			 * 
			 * if (currentpage > totalpage || currentpage < 0) { currentpage = 0; }
			 * 
			 * List<Integer> pagesnumbers = PageInfo.PageLimitCalculator(page, totalpage,
			 * PageInfo.numberOfPage);
			 */
			filterDTO.setPageNo(currentpage);
			filterDTO.setSize((int) PageInfo.pageList);

			List<InvoiceInfoDTO> invoiceInfoDTOList = invoiceInfoApi.filter(filterDTO);

			ReportGeneratorUtil rp = new ReportGeneratorUtil();

			JasperPrint jp = rp.InvoiceReport(invoiceInfoDTOList, "Invoice", "Invoice " + new Date().toString());
			reportServiceApi.writeXlsReport(jp, response, "invoice Report " + new Date().toString());

		} catch (Exception e) {
			LoggerUtil.logException(this.getClass(), e);

			return;
		}

		return;

	}

	@GetMapping(value = "/ledger/filter/pdf", produces = "application/pdf")
	@PreAuthorize("hasAnyRole('ROLE_SUPERADMINISTRATOR','ROLE_ADMINISTRATOR','ROLE_USER,ROLE_AUTHENTICATED')")
	public void filterLedgerPdf(@ModelAttribute("terms") LedgerFilterDTO filterTerms, BindingResult result,
			ModelMap modelMap, RedirectAttributes redirectAttributes, HttpServletRequest request,
			HttpServletResponse response) throws ClassNotFoundException, IOException, JRException {

		try {

			/* current user checking start */
			InvUserDTO currentUser = AuthenticationUtil.getCurrentUser(userApi);

			if (currentUser.getUserauthority().contains(Authorities.USER)
					& !AuthenticationUtil.checkPermission(currentUser, Permission.REPORT_VIEW)) {
				request.getSession().invalidate();
				return;
			}

			if (currentUser.getStoreId() == null) {
				return;
			}

			FiscalYearInfoDTO currentFiscalYear = fiscalYearInfoApi
					.getCurrentFiscalYearByStoreInfo(currentUser.getStoreId());

			if (currentFiscalYear == null) {
				return;
			}

			/* current user checking end */

			if (filterTerms == null) {
				return;

			}

			if (filterTerms.getAccountId() == null & filterTerms.getFiscalYearId() == null
					& (filterTerms.getFrom() == null | filterTerms.getTo() == null)) {
				return;

			}

			Integer page = filterTerms.getPage();

			if (page == null) {
				page = 1;
			}

			if (page < 1) {
				page = 1;
			}

			int currentpage = page - 1;

			/*
			 * long totalList = ledgerInfoApi.countFilter(filterTerms);
			 * 
			 * int totalpage = (int) Math.ceil(totalList / PageInfo.pageList);
			 * 
			 * if (currentpage > totalpage || currentpage < 0) { currentpage = 0; }
			 * 
			 * List<Integer> pagesnumbers = PageInfo.PageLimitCalculator(page, totalpage,
			 * PageInfo.numberOfPage);
			 */

			filterTerms.setPage(currentpage);
			filterTerms.setSize((int) PageInfo.pageList);
			filterTerms.setStatus(Status.ACTIVE);
			filterTerms.setStoreId(currentUser.getStoreId());

			List<LedgerInfoDTO> ledgerFilterDTOS = ledgerInfoApi.filter(filterTerms);

			ReportGeneratorUtil rp = new ReportGeneratorUtil();

			JasperPrint jp = rp.ledgerReport(ledgerFilterDTOS, "Ledger", "Ledger " + new Date().toString());
			reportServiceApi.writePdfReport(jp, response, "Ledger Report " + new Date().toString());

		} catch (Exception e) {
			LoggerUtil.logException(this.getClass(), e);
			throw e;
		}
	}

	@GetMapping(value = "/ledger/filter/xls", produces = "application/xls")
	@PreAuthorize("hasAnyRole('ROLE_SUPERADMINISTRATOR','ROLE_ADMINISTRATOR','ROLE_USER,ROLE_AUTHENTICATED')")
	public void filterLedgerXls(@ModelAttribute("terms") LedgerFilterDTO filterTerms, BindingResult result,
			ModelMap modelMap, RedirectAttributes redirectAttributes, HttpServletRequest request,
			HttpServletResponse response) throws ClassNotFoundException, IOException, JRException {

		try {

			/* current user checking start */
			InvUserDTO currentUser = AuthenticationUtil.getCurrentUser(userApi);

			if (currentUser.getUserauthority().contains(Authorities.USER)
					& !AuthenticationUtil.checkPermission(currentUser, Permission.REPORT_VIEW)) {
				request.getSession().invalidate();
				return;
			}

			if (currentUser.getStoreId() == null) {
				return;
			}

			FiscalYearInfoDTO currentFiscalYear = fiscalYearInfoApi
					.getCurrentFiscalYearByStoreInfo(currentUser.getStoreId());

			if (currentFiscalYear == null) {
				return;
			}

			/* current user checking end */

			if (filterTerms == null) {
				return;

			}

			if (filterTerms.getAccountId() == null & filterTerms.getFiscalYearId() == null
					& (filterTerms.getFrom() == null | filterTerms.getTo() == null)) {
				return;

			}

			Integer page = filterTerms.getPage();

			if (page == null) {
				page = 1;
			}

			if (page < 1) {
				page = 1;
			}

			int currentpage = page - 1;

			/*
			 * long totalList = ledgerInfoApi.countFilter(filterTerms);
			 * 
			 * int totalpage = (int) Math.ceil(totalList / PageInfo.pageList);
			 * 
			 * if (currentpage > totalpage || currentpage < 0) { currentpage = 0; }
			 * 
			 * List<Integer> pagesnumbers = PageInfo.PageLimitCalculator(page, totalpage,
			 * PageInfo.numberOfPage);
			 */

			filterTerms.setPage(currentpage);
			filterTerms.setSize((int) PageInfo.pageList);
			filterTerms.setStatus(Status.ACTIVE);
			filterTerms.setStoreId(currentUser.getStoreId());

			List<LedgerInfoDTO> ledgerFilterDTOS = ledgerInfoApi.filter(filterTerms);

			ReportGeneratorUtil rp = new ReportGeneratorUtil();

			JasperPrint jp = rp.ledgerReport(ledgerFilterDTOS, "Ledger", "Ledger " + new Date().toString());
			reportServiceApi.writeXlsReport(jp, response, "Ledger Report " + new Date().toString());

		} catch (Exception e) {
			LoggerUtil.logException(this.getClass(), e);
			throw e;
		}
	}

	@GetMapping(value = "product/filter/pdf", produces = "application/pdf")
	@PreAuthorize("hasAnyRole('ROLE_SUPERADMINISTRATOR','ROLE_ADMINISTRATOR','ROLE_USER,ROLE_AUTHENTICATED')")
	public void filterProductPDF(@ModelAttribute("filter") ProductFilterDTO filterTerms, BindingResult bindingResult,
			ModelMap modelMap, RedirectAttributes redirectAttributes, HttpServletRequest request,
			HttpServletResponse response) throws ClassNotFoundException, IOException, JRException {

		try {

			InvUserDTO currentUser = AuthenticationUtil.getCurrentUser(userApi);

			if (currentUser.getUserauthority().contains(Authorities.USER)
					& !AuthenticationUtil.checkPermission(currentUser, Permission.PRODUCT_VIEW)) {
				redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Access deniled");
				return;// access deniled page
			}

			if (currentUser.getStoreId() == null) {
				redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Store not assigned");
				return;// store not assigned page
			}

			/* current user checking end */

			if (filterTerms == null) {
				redirectAttributes.addFlashAttribute(StringConstants.ERROR, "filter terms required");
				return;

			}

			if (filterTerms.getName() == null & filterTerms.getSubCategoryId() < 1
					&& filterTerms.getTrendingLevel() == null
					&& (filterTerms.getGreaterThanInStock() < 1 || filterTerms.getLessThanInStock() < 1)) {
				redirectAttributes.addFlashAttribute(StringConstants.ERROR, "invalid filter terms");
				return;

			}

			Integer page = filterTerms.getPage();

			if (page == null) {
				page = 1;
			}

			if (page < 1) {
				page = 1;
			}

			int currentpage = page - 1;

			filterTerms.setStatus(Status.ACTIVE);
			filterTerms.setStoreInfoId(currentUser.getStoreId());

			filterTerms.setPage(currentpage);
			filterTerms.setSize((int) PageInfo.pageList);

			List<ProductInfoDTO> productInfoDTOList = productInfoApi.filter(filterTerms);

			ReportGeneratorUtil rp = new ReportGeneratorUtil();

			JasperPrint jp = rp.productReport(productInfoDTOList, "Product", "Product " + new Date().toString());
			reportServiceApi.writePdfReport(jp, response, "Product Report " + new Date().toString());

		} catch (Exception e) {
			LoggerUtil.logException(this.getClass(), e);
			throw e;
		}

		return;

	}

	@GetMapping(value = "product/filter/xls", produces = "application/xls")
	@PreAuthorize("hasAnyRole('ROLE_SUPERADMINISTRATOR','ROLE_ADMINISTRATOR','ROLE_USER,ROLE_AUTHENTICATED')")
	public void filterProductXLS(@ModelAttribute("filter") ProductFilterDTO filterTerms, BindingResult bindingResult,
			ModelMap modelMap, RedirectAttributes redirectAttributes, HttpServletRequest request,
			HttpServletResponse response) {

		try {

			InvUserDTO currentUser = AuthenticationUtil.getCurrentUser(userApi);

			if (currentUser.getUserauthority().contains(Authorities.USER)
					& !AuthenticationUtil.checkPermission(currentUser, Permission.PRODUCT_VIEW)) {
				redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Access deniled");
				return;// access deniled page
			}

			if (currentUser.getStoreId() == null) {
				redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Store not assigned");
				return;// store not assigned page
			}

			/* current user checking end */

			if (filterTerms == null) {
				redirectAttributes.addFlashAttribute(StringConstants.ERROR, "filter terms required");
				return;

			}

			if (filterTerms.getName() == null & filterTerms.getSubCategoryId() < 1
					&& filterTerms.getTrendingLevel() == null
					&& (filterTerms.getGreaterThanInStock() < 1 || filterTerms.getLessThanInStock() < 1)) {
				redirectAttributes.addFlashAttribute(StringConstants.ERROR, "invalid filter terms");
				return;

			}

			Integer page = filterTerms.getPage();

			if (page == null) {
				page = 1;
			}

			if (page < 1) {
				page = 1;
			}

			int currentpage = page - 1;

			filterTerms.setStatus(Status.ACTIVE);
			filterTerms.setStoreInfoId(currentUser.getStoreId());

			filterTerms.setPage(currentpage);
			filterTerms.setSize((int) PageInfo.pageList);

			List<ProductInfoDTO> productInfoDTOList = productInfoApi.filter(filterTerms);

			ReportGeneratorUtil rp = new ReportGeneratorUtil();

			JasperPrint jp = rp.productReport(productInfoDTOList, "Product", "Product " + new Date().toString());
			reportServiceApi.writeXlsReport(jp, response, "Product Report " + new Date().toString());

		} catch (Exception e) {
			LoggerUtil.logException(this.getClass(), e);

			return;
		}

		return;

	}
}
