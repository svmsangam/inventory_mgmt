package com.inventory.web.controller;

import com.inventory.core.api.iapi.*;
import com.inventory.core.model.dto.FiscalYearInfoDTO;
import com.inventory.core.model.dto.InvUserDTO;
import com.inventory.core.model.dto.InvoiceFilterDTO;
import com.inventory.core.model.dto.InvoiceInfoDTO;
import com.inventory.core.model.enumconstant.LogType;
import com.inventory.core.model.enumconstant.PaymentMethod;
import com.inventory.core.model.enumconstant.Permission;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.util.Authorities;
import com.inventory.web.session.RequestCacheUtil;
import com.inventory.web.util.AuthenticationUtil;
import com.inventory.web.util.PageInfo;
import com.inventory.web.util.StringConstants;
import com.itextpdf.text.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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

    @Autowired
    private ILoggerApi loggerApi;

    @Autowired
    private IPaymentInfoApi paymentInfoApi;

    @Autowired
    private IReportServiceApi reportServiceApi;

    @Autowired
    private IFiscalYearInfoApi fiscalYearInfoApi;


    @GetMapping(value = "/")
    public String index() {


        return "redirect:/invoice/list";
    }

    @GetMapping(value = "/filter")
    public String filter(@ModelAttribute("filter")InvoiceFilterDTO filterDTO , BindingResult bindingResult , ModelMap modelMap, RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response) {

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
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Store not assigned");
                return "redirect:/";//store not assigned page
            }

            FiscalYearInfoDTO currentFiscalYear = fiscalYearInfoApi.getCurrentFiscalYearByStoreInfo(currentUser.getStoreId());

            if (currentFiscalYear == null){
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "please create current fiscal year");
                return "redirect:/fiscalyear/add";//store not assigned page
            }


        /*current user checking end*/

            if (filterDTO == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Invoice not found");
                return "redirect:/invoice/list";
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

            long totalList = invoiceInfoApi.filterCount(filterDTO);//invoiceInfoApi.countAllByStatusAndStoreInfoAndInvoiceDateBetween(Status.ACTIVE, currentUser.getStoreId(), from, to);

            int totalpage = (int) Math.ceil(totalList / PageInfo.pageList);

            if (currentpage > totalpage || currentpage < 0) {
                currentpage = 0;
            }

            List<Integer> pagesnumbers = PageInfo.PageLimitCalculator(page, totalpage, PageInfo.numberOfPage);

            filterDTO.setPageNo(currentpage);
            filterDTO.setSize((int) PageInfo.pageList);

            modelMap.put(StringConstants.INVOICE_LIST, invoiceInfoApi.filter(filterDTO));
            modelMap.put("lastpage", totalpage);
            modelMap.put("currentpage", page);
            modelMap.put("pagelist", pagesnumbers);
            modelMap.put("filterDTO" , filterDTO);
            modelMap.put("totalResult" , totalList);

        } catch (Exception e) {

            e.printStackTrace();
            logger.error("Exception on invoice controller : " + Arrays.toString(e.getStackTrace()));

            return "redirect:/";
        }

        return "invoice/filter";

    }

    @GetMapping(value = "/print")
    public String print(@RequestParam("invoiceId") long invoiceId, ModelMap modelMap, RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response) {

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
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Store not assigned");
                return "redirect:/";//store not assigned page
            }

            FiscalYearInfoDTO currentFiscalYear = fiscalYearInfoApi.getCurrentFiscalYearByStoreInfo(currentUser.getStoreId());

            if (currentFiscalYear == null){
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "please create current fiscal year");
                return "redirect:/fiscalyear/add";//store not assigned page
            }


        /*current user checking end*/

            if (invoiceId < 0) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Invoice not found");
                return "redirect:/invoice/list";
            }

            synchronized (this.getClass()) {


                InvoiceInfoDTO invoiceInfoDTO = invoiceInfoApi.show(invoiceId, currentUser.getStoreId(), Status.ACTIVE);

                if (invoiceInfoDTO == null) {
                    redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Invoice not found");
                    return "redirect:/order/sale/listSale";
                }

                loggerApi.save(invoiceId, LogType.Invoice_Print, currentUser.getStoreId(), currentUser.getUserId(), "invoice printed");

                modelMap.put(StringConstants.INVOICE, invoiceInfoDTO);
                modelMap.put(StringConstants.ORDER_ITEM_LIST, orderItemInfoApi.getAllByStatusAndOrderInfo(Status.ACTIVE, invoiceInfoDTO.getOrderInfoId()));
            }

        } catch (Exception e) {
            logger.error("Exception on invoice controller : " + Arrays.toString(e.getStackTrace()));

            return "redirect:/";
        }

        return "invoice/print";
    }

    @GetMapping(value = "/list")
    public String list(@RequestParam(value = "pageNo", required = false) Integer page, @RequestParam(value = "sort", required = false) String sort, @RequestParam(value = "direction", required = false) String direction, ModelMap modelMap, RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response) {

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
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Store not assigned");
                return "redirect:/";//store not assigned page
            }

            FiscalYearInfoDTO currentFiscalYear = fiscalYearInfoApi.getCurrentFiscalYearByStoreInfo(currentUser.getStoreId());

            if (currentFiscalYear == null){
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "please create current fiscal year");
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
            modelMap.put(StringConstants.FISCAL_YEAR_LIST , fiscalYearInfoApi.list(Status.ACTIVE , currentUser.getStoreId() , 0 , 100));

        } catch (Exception e) {
            logger.error("Exception on invoice controller : " + Arrays.toString(e.getStackTrace()));

            return "redirect:/500";
        }
        return "invoice/list";
    }

    @GetMapping(value = "/{invoiceId}")
    public String show(@PathVariable("invoiceId") Long invoiceId, ModelMap modelMap, RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response) {

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
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Store not assigned");
                return "redirect:/";//store not assigned page
            }

            FiscalYearInfoDTO currentFiscalYear = fiscalYearInfoApi.getCurrentFiscalYearByStoreInfo(currentUser.getStoreId());

            if (currentFiscalYear == null){
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "please create current fiscal year");
                return "redirect:/fiscalyear/add";//store not assigned page
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

            InvoiceInfoDTO invoiceInfoDTO = invoiceInfoApi.show(invoiceId, currentUser.getStoreId(), Status.ACTIVE);

            if (invoiceInfoDTO == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Invoice not found");
                return "redirect:/invoice/list";
            }

            modelMap.put(StringConstants.INVOICE, invoiceInfoDTO);
            modelMap.put(StringConstants.ORDER_ITEM_LIST, orderItemInfoApi.getAllByStatusAndOrderInfo(Status.ACTIVE, invoiceInfoDTO.getOrderInfoId()));
            modelMap.put(StringConstants.PAYMENTMETHODLIST, PaymentMethod.values());
            modelMap.put(StringConstants.LOGGER, loggerApi.getAllByStatusAndAssociateIdAndTypeAndStore(Status.ACTIVE, invoiceId, LogType.Invoice_Print, currentUser.getStoreId()));

            List<Status> statusList = new ArrayList<>();

            statusList.add(Status.ACTIVE);
            statusList.add(Status.INACTIVE);

            modelMap.put(StringConstants.PAYMENTLIST, paymentInfoApi.getAllByStatusInAndStoreAndInvoiceInfo(statusList, currentUser.getStoreId(), invoiceId));


        } catch (Exception e) {

            e.printStackTrace();
            logger.error("Exception on invoice controller : " + Arrays.toString(e.getStackTrace()));

            return "redirect:/500";
        }

        return "invoice/show";
    }

    @PostMapping(value = "/cancel")
    public String cancel(@RequestParam("invoiceId") Long invoiceId, @RequestParam("note")String note , @RequestParam("version")int version , ModelMap modelMap, RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response) {

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
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Store not assigned");
                return "redirect:/";//store not assigned page
            }

            FiscalYearInfoDTO currentFiscalYear = fiscalYearInfoApi.getCurrentFiscalYearByStoreInfo(currentUser.getStoreId());

            if (currentFiscalYear == null){
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "please create current fiscal year");
                return "redirect:/fiscalyear/add";//store not assigned page
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

            InvoiceInfoDTO invoiceInfoDTO = invoiceInfoApi.show(invoiceId, currentUser.getStoreId(), Status.ACTIVE);

            if (invoiceInfoDTO == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Invoice not found");
                return "redirect:/invoice/list";
            }

            if (invoiceInfoDTO.isCanceled()){
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Invoice already canceled");
                return "redirect:/invoice/" + invoiceId;
            }

            if (invoiceInfoDTO.getVersion() != version){
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Another User updated Invoice already");
                return "redirect:/invoice/" + invoiceId;
            }

            if (note == null){
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "please provide any reason to cancel invoice");
                return "redirect:/invoice/" + invoiceId;
            }

            if (note.trim().equals("")){
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "please provide any reason to cancel invoice");
                return "redirect:/invoice/" + invoiceId;
            }

            invoiceInfoApi.cancel(invoiceId , note , currentUser.getUserId());
            redirectAttributes.addFlashAttribute(StringConstants.MESSAGE, "invoice canceled successfully");

        } catch (Exception e) {
            logger.error("Exception on invoice controller : " + Arrays.toString(e.getStackTrace()));

            return "redirect:/500";
        }

        return "redirect:/invoice/" + invoiceId;
    }


    @GetMapping(value = "/pdf")
    public void pdf(@RequestParam("invoiceId") Long invoiceId, RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response) {

        try {

                      /*current user checking start*/
            InvUserDTO currentUser = AuthenticationUtil.getCurrentUser(userApi);

            if (currentUser != null) {

                if ((currentUser.getUserauthority().contains(Authorities.SUPERADMIN) & currentUser.getUserauthority().contains(Authorities.AUTHENTICATED)) | (currentUser.getUserauthority().contains(Authorities.ADMINISTRATOR) & currentUser.getUserauthority().contains(Authorities.AUTHENTICATED)) | (currentUser.getUserauthority().contains(Authorities.USER) & currentUser.getUserauthority().contains(Authorities.AUTHENTICATED))) {

                    boolean valid = true;

                    if (currentUser.getUserauthority().contains(Authorities.USER)) {

                        if (!AuthenticationUtil.checkPermission(currentUser, Permission.INVOICE_VIEW)) {

                            valid = false;

                        }
                    }

                    if (valid) {


                        if (currentUser.getStoreId() != null) {

        /*current user checking end*/

                            if (invoiceId != null) {


                                if (invoiceId > 0) {

                                    InvoiceInfoDTO invoiceInfoDTO = invoiceInfoApi.show(invoiceId, currentUser.getStoreId(), Status.ACTIVE);

                                    if (invoiceInfoDTO != null) {


                                        synchronized (this.getClass()) {
                                            String file = Long.toString(System.currentTimeMillis());

                                            String path = reportServiceApi.pdfWriterForInvoice(invoiceInfoDTO);

                                            int BUFF_SIZE = 1024;
                                            byte[] buffer = new byte[BUFF_SIZE];
                                            response.setContentType("application/pdf");
                                            response.setHeader("Content-Type", "application/pdf");
                                            File pdfFile = new File(path);
                                            FileInputStream fis = new FileInputStream(pdfFile);
                                            OutputStream os = response.getOutputStream();
                                            try {
                                                response.setContentLength((int) pdfFile.length());
                                                int byteRead = 0;
                                                while ((byteRead = fis.read()) != -1) {
                                                    os.write(byteRead);
                                                }
                                                os.flush();

                                                loggerApi.save(invoiceId, LogType.Invoice_Print, currentUser.getStoreId(), currentUser.getUserId(), "pdf generated of invoice");

                                            } catch (Exception excp) {
                                                excp.printStackTrace();
                                            } finally {
                                                os.close();
                                                fis.close();
                                                pdfFile.delete();
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    @GetMapping(value = "/xls")
    public void xls(@RequestParam("invoiceId") Long invoiceId, RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response) {

        try {
                      /*current user checking start*/
            InvUserDTO currentUser = AuthenticationUtil.getCurrentUser(userApi);

            if (currentUser != null) {

                if ((currentUser.getUserauthority().contains(Authorities.SUPERADMIN) & currentUser.getUserauthority().contains(Authorities.AUTHENTICATED)) | (currentUser.getUserauthority().contains(Authorities.ADMINISTRATOR) & currentUser.getUserauthority().contains(Authorities.AUTHENTICATED)) | (currentUser.getUserauthority().contains(Authorities.USER) & currentUser.getUserauthority().contains(Authorities.AUTHENTICATED))) {

                    boolean valid = true;

                    if (currentUser.getUserauthority().contains(Authorities.USER)) {

                        if (!AuthenticationUtil.checkPermission(currentUser, Permission.INVOICE_VIEW)) {

                            valid = false;

                        }
                    }

                    if (valid) {


                        if (currentUser.getStoreId() != null) {

        /*current user checking end*/

                            if (invoiceId != null) {


                                if (invoiceId > 0) {

                                    InvoiceInfoDTO invoiceInfoDTO = invoiceInfoApi.show(invoiceId, currentUser.getStoreId(), Status.ACTIVE);

                                    if (invoiceInfoDTO != null) {

                                        synchronized (this.getClass()) {
                                            reportServiceApi.writeXlsReport(invoiceId, currentUser.getStoreId(), response, request);

                                            loggerApi.save(invoiceId, LogType.Invoice_Print, currentUser.getStoreId(), currentUser.getUserId(), "excel generated of invoice");
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



