package com.inventory.web.controller;

import com.inventory.core.api.iapi.IFiscalYearInfoApi;
import com.inventory.core.api.iapi.IUserApi;
import com.inventory.core.model.dto.FiscalYearInfoDTO;
import com.inventory.core.model.dto.InvUserDTO;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.util.Authorities;
import com.inventory.web.session.RequestCacheUtil;
import com.inventory.web.util.AuthenticationUtil;
import com.inventory.web.util.StringConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * Created by dhiraj on 12/24/17.
 */
@Controller
@RequestMapping("fiscalyear")
public class FiscalYearInfoController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IUserApi userApi;

    @Autowired
    private IFiscalYearInfoApi fiscalYearInfoApi;

    @GetMapping(value = "/")
    public String index() {


        return "redirect:/fiscalyear/list";
    }

    @GetMapping(value = "/list")
    public String list(ModelMap modelMap, RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response) {

        try {
                  /*current user checking start*/
            InvUserDTO currentUser = AuthenticationUtil.getCurrentUser(userApi);

            if (currentUser == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Athentication failed");

                RequestCacheUtil.save(request, response);

                return "redirect:/login";
            }

            if (!((currentUser.getUserauthority().contains(Authorities.SUPERADMIN) | currentUser.getUserauthority().contains(Authorities.ADMINISTRATOR)) && currentUser.getUserauthority().contains(Authorities.AUTHENTICATED))) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Athentication failed");
                return "redirect:/logout";
            }

            if (currentUser.getStoreId() == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Store not assigned");
                return "redirect:/";//store not assigned page
            }

        /*current user checking end*/

            modelMap.put(StringConstants.FISCAL_YEAR_LIST, fiscalYearInfoApi.list(Status.ACTIVE, currentUser.getStoreId(), 0, 100));

        } catch (Exception e) {

            logger.error("Exception on fiscal year controller : " + Arrays.toString(e.getStackTrace()));

            return "redirect:/500";
        }

        return "fiscalyear/list";
    }

    @GetMapping(value = "/add")
    public String add(ModelMap modelMap, RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response) {

        try {
                  /*current user checking start*/
            InvUserDTO currentUser = AuthenticationUtil.getCurrentUser(userApi);

            if (currentUser == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Athentication failed");

                RequestCacheUtil.save(request, response);

                return "redirect:/login";
            }

            if (!((currentUser.getUserauthority().contains(Authorities.SUPERADMIN) | currentUser.getUserauthority().contains(Authorities.ADMINISTRATOR)) && currentUser.getUserauthority().contains(Authorities.AUTHENTICATED))) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Athentication failed");
                return "redirect:/logout";
            }

            if (currentUser.getStoreId() == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Store not assigned");
                return "redirect:/";//store not assigned page
            }

        /*current user checking end*/

        } catch (Exception e) {

            logger.error("Exception on fiscal year controller : " + Arrays.toString(e.getStackTrace()));

            return "redirect:/500";
        }

        return "fiscalyear/add";
    }

    @PostMapping(value = "/save")
    public String save(@RequestAttribute("fiscalYearInfo") FiscalYearInfoDTO fiscalYearInfoDTO, ModelMap modelMap, RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response) {

        try {

                  /*current user checking start*/
            InvUserDTO currentUser = AuthenticationUtil.getCurrentUser(userApi);

            if (currentUser == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Athentication failed");

                RequestCacheUtil.save(request, response);

                return "redirect:/login";
            }

            if (!((currentUser.getUserauthority().contains(Authorities.SUPERADMIN) | currentUser.getUserauthority().contains(Authorities.ADMINISTRATOR)) && currentUser.getUserauthority().contains(Authorities.AUTHENTICATED))) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Athentication failed");
                return "redirect:/logout";
            }

            if (currentUser.getStoreId() == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Store not assigned");
                return "redirect:/";//store not assigned page
            }

        /*current user checking end*/

            if (fiscalYearInfoDTO == null) {

                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "bad request");

                return "redirect:/fiscalyear/add";
            }

            fiscalYearInfoDTO.setStoreInfoId(currentUser.getStoreId());

            fiscalYearInfoApi.save(fiscalYearInfoDTO);

            redirectAttributes.addFlashAttribute(StringConstants.MESSAGE, "fiscal year successfully saved");

        } catch (Exception e) {

            logger.error("Exception on fiscal year controller : " + Arrays.toString(e.getStackTrace()));

            return "redirect:/500";
        }

        return "redirect:/fiscalyear/list";
    }

}
