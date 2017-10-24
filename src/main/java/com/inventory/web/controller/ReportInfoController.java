package com.inventory.web.controller;

import com.inventory.core.api.iapi.*;
import com.inventory.core.model.dto.*;
import com.inventory.core.model.enumconstant.AccountAssociateType;
import com.inventory.core.model.enumconstant.AccountEntryType;
import com.inventory.core.model.enumconstant.Permission;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.util.Authorities;
import com.inventory.core.util.ReportGeneratorUtil;
import com.inventory.web.util.AuthenticationUtil;
import com.inventory.web.util.StringConstants;
import net.sf.jasperreports.engine.JasperPrint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by dhiraj on 10/9/17.
 */
@Controller
@RequestMapping("report")
public class ReportInfoController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IUserApi userApi;

    @Autowired
    private IReportServiceApi reportServiceApi;

    @Autowired
    private ILedgerInfoApi ledgerInfoApi;

    @Autowired
    private IAccountInfoApi accountInfoApi;

    @Autowired
    private IClientInfoApi clientInfoApi;

    @Autowired
    private IInvoiceInfoApi invoiceInfoApi;

    @Autowired
    private IOrderInfoApi orderInfoApi;

    @Autowired
    private IOrderItemInfoApi orderItemInfoApi;

    @GetMapping(value = "invoice/pdf", produces = "application/pdf")
    public void getpdf(@RequestParam("invoiceId") Long invoiceId, HttpServletRequest request , final HttpServletResponse response) {

        try {

            InvUserDTO currentUser = AuthenticationUtil.getCurrentUser(userApi);

            if (currentUser == null) {
                request.getSession().invalidate();

                return ;
            }

            if (!((currentUser.getUserauthority().contains(Authorities.SUPERADMIN) | currentUser.getUserauthority().contains(Authorities.ADMINISTRATOR) | currentUser.getUserauthority().contains(Authorities.USER)) && currentUser.getUserauthority().contains(Authorities.AUTHENTICATED))) {

                request.getSession().invalidate();

                return ;
            }


            if (currentUser.getUserauthority().contains(Authorities.USER) & !AuthenticationUtil.checkPermission(currentUser, Permission.REPORT_VIEW)) {
                request.getSession().invalidate();

                return ;
            }

            if (currentUser.getStoreId() == null) {
                request.getSession().invalidate();

                return ;
            }

            if (invoiceId == null){
                return;
            }

            if (invoiceId < 0){
                return;
            }

            InvoiceInfoDTO invoiceInfoDTO = invoiceInfoApi.show(invoiceId , currentUser.getStoreId() , Status.ACTIVE);

            ReportGeneratorUtil rp = new ReportGeneratorUtil();

            //JasperPrint jp = rp.invoiceReport(invoiceInfoDTO, "Invoice", "Invoice " + new Date().toString());
           // reportServiceApi.writePdfReport(jp, response, "invoice Report " + new Date().toString());

        } catch (Exception e) {
            logger.error("Stack trace: " + e.getStackTrace());

            e.printStackTrace();
        }

        return ;
    }

    @GetMapping(value = "invoice/xls", produces = "application/xls")
    public void getXLS(@RequestParam("invoiceId") Long invoiceId, HttpServletRequest request , final HttpServletResponse response) {

        try {


        } catch (Exception e) {
            logger.error("Stack trace: " + e.getStackTrace());

            e.printStackTrace();
        }

        return ;
    }

    @GetMapping(value = "/ledger/filter/xls")
    public void filterXls(@ModelAttribute("terms") LedgerFilter filterTerms, final HttpServletResponse response , RedirectAttributes redirectAttributes) {

        try {

             /*current user checking start*/
            InvUserDTO currentUser = AuthenticationUtil.getCurrentUser(userApi);

            if (currentUser == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Athentication failed");
                return;
            }

            if (!((currentUser.getUserauthority().contains(Authorities.SUPERADMIN) | currentUser.getUserauthority().contains(Authorities.ADMINISTRATOR) | currentUser.getUserauthority().contains(Authorities.USER)) && currentUser.getUserauthority().contains(Authorities.AUTHENTICATED))) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Athentication failed");
                return;
            }

            if (currentUser.getUserauthority().contains(Authorities.USER) & !AuthenticationUtil.checkPermission(currentUser, Permission.REPORT_VIEW)) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Access deniled");
                return;//access deniled page
            }

            if (currentUser.getStoreId() == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Store not assigned");
                return;//store not assigned page
            }

        /*current user checking end*/

            if (filterTerms == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "filter terms required");
                return;

            }

            if (filterTerms.getClientId() <= 0) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "client required");
                return ;
            }

            if (filterTerms.getFrom() == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "from date required");
                return;
            }

            if (filterTerms.getTo() == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "to date required");
                return;
            }

            ClientInfoDTO clientInfoDTO = clientInfoApi.show(Status.ACTIVE , filterTerms.getClientId());

            if (clientInfoDTO == null){
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "client not found");
                return;
            }

            AccountInfoDTO accountInfoDTO = accountInfoApi.getByAssociateIdAndAccountAssociateType(filterTerms.getClientId(), AccountAssociateType.CUSTOMER);

            if (accountInfoDTO == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "account not found");
                return;
            }



            List<LedgerInfoDTO> ledgerInfoDTOList = ledgerInfoApi.filterReport(Status.ACTIVE, currentUser.getStoreId(), accountInfoDTO.getAccountId(), filterTerms.getFrom(), filterTerms.getTo());

            String clientName = "";

            if (clientInfoDTO.getCompanyName() != null) {
                clientName =  clientInfoDTO.getCompanyName();
            } else {
                clientName = clientInfoDTO.getName();
            }

   /*         modelMap.put("totalFilterDr" , ledgerInfoApi.filterTotalAmount(Status.ACTIVE , currentUser.getStoreId() , accountInfoDTO.getAccountId() , filterTerms.getFrom() , filterTerms.getTo() , AccountEntryType.DEBIT));
            modelMap.put("totalFilterCr" , ledgerInfoApi.filterTotalAmount(Status.ACTIVE , currentUser.getStoreId() , accountInfoDTO.getAccountId() , filterTerms.getFrom() , filterTerms.getTo() , AccountEntryType.CREDIT));

            modelMap.put("totalDr" , ledgerInfoApi.getTotalAmountByStatusAndStoreInfoIdAndAccountInfoAndAccountEntryType(Status.ACTIVE , currentUser.getStoreId() , accountInfoDTO.getAccountId() , AccountEntryType.DEBIT));
            modelMap.put("totalCr" , ledgerInfoApi.getTotalAmountByStatusAndStoreInfoIdAndAccountInfoAndAccountEntryType(Status.ACTIVE , currentUser.getStoreId() , accountInfoDTO.getAccountId() , AccountEntryType.CREDIT));

        */

            double balance = ledgerInfoApi.getTotalAmountByStatusAndStoreInfoIdAndAccountInfoAndAccountEntryType(Status.ACTIVE , currentUser.getStoreId() , accountInfoDTO.getAccountId() , AccountEntryType.CREDIT) - ledgerInfoApi.getTotalAmountByStatusAndStoreInfoIdAndAccountInfoAndAccountEntryType(Status.ACTIVE , currentUser.getStoreId() , accountInfoDTO.getAccountId() , AccountEntryType.DEBIT);

            ReportGeneratorUtil rp = new ReportGeneratorUtil();

            JasperPrint jp = rp.ledgerReport(ledgerInfoDTOList, clientName, "total balance ( " + balance + " )");
            reportServiceApi.writeXlsReport(jp, response, "Ledger Report " + accountInfoDTO.getAcountNumber() + " " + filterTerms.getFrom() + " to " + filterTerms.getTo()  );


        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

    }


    @GetMapping(value = "/ledger/filter/pdf")
    public void filterPdf(@ModelAttribute("terms") LedgerFilter filterTerms, final HttpServletResponse response , RedirectAttributes redirectAttributes) {

        try {

             /*current user checking start*/
            InvUserDTO currentUser = AuthenticationUtil.getCurrentUser(userApi);

            if (currentUser == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Athentication failed");
                return;
            }

            if (!((currentUser.getUserauthority().contains(Authorities.SUPERADMIN) | currentUser.getUserauthority().contains(Authorities.ADMINISTRATOR) | currentUser.getUserauthority().contains(Authorities.USER)) && currentUser.getUserauthority().contains(Authorities.AUTHENTICATED))) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Athentication failed");
                return;
            }

            if (currentUser.getUserauthority().contains(Authorities.USER) & !AuthenticationUtil.checkPermission(currentUser, Permission.REPORT_VIEW)) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Access deniled");
                return;//access deniled page
            }

            if (currentUser.getStoreId() == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Store not assigned");
                return;//store not assigned page
            }

        /*current user checking end*/

            if (filterTerms == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "filter terms required");
                return;

            }

            if (filterTerms.getClientId() <= 0) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "client required");
                return ;
            }

            if (filterTerms.getFrom() == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "from date required");
                return;
            }

            if (filterTerms.getTo() == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "to date required");
                return;
            }

            ClientInfoDTO clientInfoDTO = clientInfoApi.show(Status.ACTIVE , filterTerms.getClientId());

            if (clientInfoDTO == null){
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "client not found");
                return;
            }

            AccountInfoDTO accountInfoDTO = accountInfoApi.getByAssociateIdAndAccountAssociateType(filterTerms.getClientId(), AccountAssociateType.CUSTOMER);

            if (accountInfoDTO == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "account not found");
                return;
            }



            List<LedgerInfoDTO> ledgerInfoDTOList = ledgerInfoApi.filterReport(Status.ACTIVE, currentUser.getStoreId(), accountInfoDTO.getAccountId(), filterTerms.getFrom(), filterTerms.getTo());

            String clientName = "";

            if (clientInfoDTO.getCompanyName() != null) {
                clientName =  clientInfoDTO.getCompanyName();
            } else {
                clientName = clientInfoDTO.getName();
            }

            double balance = ledgerInfoApi.getTotalAmountByStatusAndStoreInfoIdAndAccountInfoAndAccountEntryType(Status.ACTIVE , currentUser.getStoreId() , accountInfoDTO.getAccountId() , AccountEntryType.CREDIT) - ledgerInfoApi.getTotalAmountByStatusAndStoreInfoIdAndAccountInfoAndAccountEntryType(Status.ACTIVE , currentUser.getStoreId() , accountInfoDTO.getAccountId() , AccountEntryType.DEBIT);

   /*         modelMap.put("totalFilterDr" , ledgerInfoApi.filterTotalAmount(Status.ACTIVE , currentUser.getStoreId() , accountInfoDTO.getAccountId() , filterTerms.getFrom() , filterTerms.getTo() , AccountEntryType.DEBIT));
            modelMap.put("totalFilterCr" , ledgerInfoApi.filterTotalAmount(Status.ACTIVE , currentUser.getStoreId() , accountInfoDTO.getAccountId() , filterTerms.getFrom() , filterTerms.getTo() , AccountEntryType.CREDIT));

            modelMap.put("totalDr" , ledgerInfoApi.getTotalAmountByStatusAndStoreInfoIdAndAccountInfoAndAccountEntryType(Status.ACTIVE , currentUser.getStoreId() , accountInfoDTO.getAccountId() , AccountEntryType.DEBIT));
            modelMap.put("totalCr" , ledgerInfoApi.getTotalAmountByStatusAndStoreInfoIdAndAccountInfoAndAccountEntryType(Status.ACTIVE , currentUser.getStoreId() , accountInfoDTO.getAccountId() , AccountEntryType.CREDIT));

        */


            ReportGeneratorUtil rp = new ReportGeneratorUtil();

            JasperPrint jp = rp.ledgerReport(ledgerInfoDTOList, clientName, "total balance ( " + balance + " )");
            reportServiceApi.writePdfReport(jp, response, "Ledger Report " + accountInfoDTO.getAcountNumber() + " " + filterTerms.getFrom() + " to " + filterTerms.getTo()  );


        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

    }
}
