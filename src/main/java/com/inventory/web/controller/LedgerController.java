
package com.inventory.web.controller;

import com.inventory.core.api.iapi.*;
import com.inventory.core.model.dto.FiscalYearInfoDTO;
import com.inventory.core.model.dto.InvUserDTO;
import com.inventory.core.model.dto.LedgerFilterDTO;
import com.inventory.core.model.enumconstant.Permission;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.util.Authorities;
import com.inventory.web.util.AuthenticationUtil;
import com.inventory.web.util.PageInfo;
import com.inventory.web.util.StringConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
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
    private IFiscalYearInfoApi fiscalYearInfoApi;

    @GetMapping(value = "/")
    public String index() {

        return "redirect:/Ledger/list";
    }

    @GetMapping(value = "/list")
	@PreAuthorize("hasAnyRole('ROLE_SUPERADMINISTRATOR','ROLE_ADMINISTRATOR','ROLE_USER,ROLE_AUTHENTICATED')")
    public String list(@RequestParam(value = "pageNo", required = false) Integer page, ModelMap modelMap, RedirectAttributes redirectAttributes) {

        try {

             /*current user checking start*/
            InvUserDTO currentUser = AuthenticationUtil.getCurrentUser(userApi);

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

            long totalList = ledgerInfoApi.countAllByStatusAndStore(Status.ACTIVE, currentUser.getStoreId());

            int totalpage = (int) Math.ceil(totalList / PageInfo.pageList);

            if (currentpage > totalpage || currentpage < 0) {
                currentpage = 0;
            }

            List<Integer> pagesnumbers = PageInfo.PageLimitCalculator(page, totalpage, PageInfo.numberOfPage);

            modelMap.put(StringConstants.LEDGERLIST, ledgerInfoApi.list(Status.ACTIVE, currentUser.getStoreId(), currentpage, (int) PageInfo.pageList));
            modelMap.put(StringConstants.FISCAL_YEAR_LIST , fiscalYearInfoApi.list(Status.ACTIVE , currentUser.getStoreId() , 0 , 100));
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
	@PreAuthorize("hasAnyRole('ROLE_SUPERADMINISTRATOR','ROLE_ADMINISTRATOR','ROLE_USER,ROLE_AUTHENTICATED')")
    public String filter(@ModelAttribute("terms") LedgerFilterDTO filterTerms, BindingResult result , ModelMap modelMap, RedirectAttributes redirectAttributes) {

        try {

             /*current user checking start*/
            InvUserDTO currentUser = AuthenticationUtil.getCurrentUser(userApi);

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
                return "redirect:/fiscalyear/add";//store not assigned page
            }


        /*current user checking end*/

            if (filterTerms == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "filter terms required");
                return "redirect:/ledger/list";

            }

            if (filterTerms.getAccountId() == null & filterTerms.getFiscalYearId() == null & (filterTerms.getFrom() == null | filterTerms.getTo() == null)){
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "invalid filter terms");
                return "redirect:/ledger/list";

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
            filterTerms.setStoreId(currentUser.getStoreId());

            long totalList = ledgerInfoApi.countFilter(filterTerms);

            int totalpage = (int) Math.ceil(totalList / PageInfo.pageList);

            if (currentpage > totalpage || currentpage < 0) {
                currentpage = 0;
            }

            List<Integer> pagesnumbers = PageInfo.PageLimitCalculator(page, totalpage, PageInfo.numberOfPage);

            filterTerms.setPage(currentpage);
            filterTerms.setSize((int) PageInfo.pageList);


            modelMap.put(StringConstants.LEDGERLIST, ledgerInfoApi.filter(filterTerms ));
            modelMap.put(StringConstants.FISCAL_YEAR_LIST , fiscalYearInfoApi.list(Status.ACTIVE , currentUser.getStoreId() , 0 , 100));

            modelMap.put("lastpage", totalpage);
            modelMap.put("currentpage", page);
            modelMap.put("pagelist", pagesnumbers);
            modelMap.put("term" , filterTerms);

        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/";
        }

        return "ledger/filter";
    }


}
