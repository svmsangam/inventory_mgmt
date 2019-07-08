package com.inventory.web.controller;

import com.inventory.core.api.iapi.IUserApi;
import com.inventory.core.model.enumconstant.ClientType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Created by dhiraj on 7/30/17.
 */
@Controller
@RequestMapping("account")
public class AccountHeadController {

    @Autowired
    private IUserApi userApi;


    @GetMapping(value = "generate")
    public String generate(@RequestParam("associateId") Long associateId, @RequestParam("clientType") ClientType clientType, ModelMap modelMap, RedirectAttributes redirectAttributes) {

        return "redirect:/";
    }
}
