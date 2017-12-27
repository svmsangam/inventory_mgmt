
package com.inventory.web.controller;

import com.inventory.core.api.iapi.*;
import com.inventory.core.model.dto.*;
import com.inventory.core.model.enumconstant.AccountAssociateType;
import com.inventory.core.model.enumconstant.AccountEntryType;
import com.inventory.core.model.enumconstant.Permission;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.util.Authorities;
import com.inventory.web.util.AuthenticationUtil;
import com.inventory.web.util.PageInfo;
import com.inventory.web.util.StringConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/ledger")
public class LedgerController {

    @Autowired
    private IUserApi userApi;

    @Autowired
    private ILedgerInfoApi ledgerInfoApi;

    @Autowired
    private IAccountInfoApi accountInfoApi;

    @Autowired
    private IClientInfoApi clientInfoApi;

    @Autowired
    private IFiscalYearInfoApi fiscalYearInfoApi;

    @GetMapping(value = "/")
    public String index() {

        return "redirect:/Ledger/list";
    }

    @GetMapping(value = "/list")
    public String list(@RequestParam(value = "pageNo", required = false) Integer page, ModelMap modelMap, RedirectAttributes redirectAttributes) {

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

            if (currentUser.getUserauthority().contains(Authorities.USER) & !AuthenticationUtil.checkPermission(currentUser, Permission.REPORT_VIEW)) {
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

            long totalList = ledgerInfoApi.countAllByStatusAndStore(Status.ACTIVE, currentUser.getStoreId());

            int totalpage = (int) Math.ceil(totalList / PageInfo.pageList);

            if (currentpage > totalpage || currentpage < 0) {
                currentpage = 0;
            }

            List<Integer> pagesnumbers = PageInfo.PageLimitCalculator(page, totalpage, PageInfo.numberOfPage);

            modelMap.put(StringConstants.LEDGERLIST, ledgerInfoApi.list(Status.ACTIVE, currentUser.getStoreId(), currentpage, (int) PageInfo.pageList));
            modelMap.put("lastpage", totalpage);
            modelMap.put("currentpage", page);
            modelMap.put("pagelist", pagesnumbers);

        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/";
        }

        return "ledger/list";
    }


    @GetMapping(value = "/filter")
    public String filter(@RequestParam(value = "pageNo", required = false) Integer page, @ModelAttribute("terms") LedgerFilter filterTerms, ModelMap modelMap, RedirectAttributes redirectAttributes) {

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

            if (currentUser.getUserauthority().contains(Authorities.USER) & !AuthenticationUtil.checkPermission(currentUser, Permission.REPORT_VIEW)) {
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
                return "redirect:/";//store not assigned page
            }


        /*current user checking end*/

            if (filterTerms == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "filter terms required");
                return "redirect:/ledger/list";

            }

            if (filterTerms.getClientId() <= 0) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "client required");
                return "redirect:/ledger/list";
            }

            if (filterTerms.getFrom() == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "from date required");
                return "redirect:/ledger/list";
            }

            if (filterTerms.getTo() == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "to date required");
                return "redirect:/ledger/list";
            }

            ClientInfoDTO clientInfoDTO = clientInfoApi.show(Status.ACTIVE , filterTerms.getClientId());

            if (clientInfoDTO == null){
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "client not found");
                return "redirect:/ledger/list";
            }

            AccountInfoDTO accountInfoDTO = accountInfoApi.getByAssociateIdAndAccountAssociateType(filterTerms.getClientId(), AccountAssociateType.CUSTOMER);

            if (accountInfoDTO == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "account not found");
                return "redirect:/ledger/list";
            }

            if (page == null) {
                page = 1;
            }

            if (page < 1) {
                page = 1;
            }

            int currentpage = page - 1;

            long totalList = ledgerInfoApi.filterCount(Status.ACTIVE, currentUser.getStoreId(), accountInfoDTO.getAccountId(), filterTerms.getFrom(), filterTerms.getTo());

            int totalpage = (int) Math.ceil(totalList / PageInfo.pageList);

            if (currentpage > totalpage || currentpage < 0) {
                currentpage = 0;
            }

            List<Integer> pagesnumbers = PageInfo.PageLimitCalculator(page, totalpage, PageInfo.numberOfPage);

            modelMap.put(StringConstants.LEDGERLIST, ledgerInfoApi.filter(Status.ACTIVE, currentUser.getStoreId(), accountInfoDTO.getAccountId(), filterTerms.getFrom(), filterTerms.getTo(), currentpage, (int) PageInfo.pageList));

            modelMap.put("lastpage", totalpage);
            modelMap.put("currentpage", page);
            modelMap.put("pagelist", pagesnumbers);
            modelMap.put("term" , filterTerms);

            modelMap.put("totalFilterDr" , ledgerInfoApi.filterTotalAmount(Status.ACTIVE , currentUser.getStoreId() , accountInfoDTO.getAccountId() , filterTerms.getFrom() , filterTerms.getTo() , AccountEntryType.DEBIT));
            modelMap.put("totalFilterCr" , ledgerInfoApi.filterTotalAmount(Status.ACTIVE , currentUser.getStoreId() , accountInfoDTO.getAccountId() , filterTerms.getFrom() , filterTerms.getTo() , AccountEntryType.CREDIT));

            modelMap.put("totalDr" , ledgerInfoApi.getTotalAmountByStatusAndStoreInfoIdAndAccountInfoAndAccountEntryType(Status.ACTIVE , currentUser.getStoreId() , accountInfoDTO.getAccountId() , AccountEntryType.DEBIT));
            modelMap.put("totalCr" , ledgerInfoApi.getTotalAmountByStatusAndStoreInfoIdAndAccountInfoAndAccountEntryType(Status.ACTIVE , currentUser.getStoreId() , accountInfoDTO.getAccountId() , AccountEntryType.CREDIT));

            modelMap.put("accountNo" , accountInfoDTO.getAcountNumber());
            if (clientInfoDTO.getCompanyName() != null) {
                modelMap.put("clientName", clientInfoDTO.getCompanyName());
            } else {
                modelMap.put("clientName", clientInfoDTO.getName());
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/";
        }

        return "ledger/filter";
    }


}
