
package com.inventory.web.controller;

import com.inventory.core.api.iapi.IUserApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/ledger")
public class LedgerController {

    @Autowired
    private IUserApi userApi;

    @GetMapping(value = "/")
    public String index() {

        return "redirect:/Ledger/list";
    }

    @GetMapping(value = "/list")
    public String list(ModelMap modelMap, RedirectAttributes redirectAttributes) {

        try {

        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/";
        }

        return "ledger/list";
    }


}
