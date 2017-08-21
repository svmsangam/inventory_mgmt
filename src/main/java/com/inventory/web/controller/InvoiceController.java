package com.inventory.web.controller;

import com.inventory.core.api.iapi.IUserApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/invoice")
public class InvoiceController {

    @Autowired
    private IUserApi userApi;


    @GetMapping(value = "/")
    public String index() {

        return "redirect:/invoice/list";
    }

    @GetMapping(value = "/list")
    public String list(@RequestParam(value = "pageNo", required = false) Integer page, @RequestParam(value = "sort", required = false) String sort, @RequestParam(value = "direction", required = false) String direction, ModelMap modelMap, RedirectAttributes redirectAttributes) {

        return "invoice/list";
    }

    @GetMapping(value = "/add")
    public String add(ModelMap modelMap, RedirectAttributes redirectAttributes) {


        return "invoice/add";
    }

    @PostMapping(value = "/save")
    public String save(ModelMap modelMap, RedirectAttributes redirectAttributes) {

        try {


        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/";
        }

        return "redirect:/invoice/";
    }

    @PostMapping(value = "/generate")
    public String generate(ModelMap modelMap, @RequestParam("orderId") Long orderId, RedirectAttributes redirectAttributes) {


        return "invoice/generate";
    }

    @PostMapping(value = "/custom")
    public String saveCustom(ModelMap modelMap, RedirectAttributes redirectAttributes) {

        try {


        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/";
        }

        return "redirect:/invoice/";
    }

    @GetMapping(value = "/edit")
    public String edit() {

        return "redirect:/invoice/list";
    }


    @PostMapping(value = "/payment")
    public String updatePayment(RedirectAttributes redirectAttributes) {

        try {
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/";
        }

        return "redirect:/invoice/";
    }

    @PostMapping(value = "/update")
    public String update() {


        return "redirect:/invoice/list";
    }

    @GetMapping(value = "/{invoiceId}")
    public String show(@PathVariable("invoiceId") Long invoiceId, ModelMap modelMap, RedirectAttributes redirectAttributes) {


        return "invoice/show";
    }

    @GetMapping(value = "/delete")
    public String delete() {

        return "redirect:/invoice/list";
    }
}



