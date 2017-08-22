package com.inventory.web.controller;

import com.inventory.core.api.iapi.IUserApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author uttam
 */
@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private IUserApi userApi;

    @GetMapping(value = "/")
    public String index(RedirectAttributes redirectAttributes) {


        return "redirect:/customer/list";
    }

    @GetMapping(value = "/list")
    public String list(@RequestParam(value = "pageNo", required = false) Integer page, @RequestParam(value = "sort", required = false) String sort, @RequestParam(value = "direction", required = false) String direction, ModelMap modelMap, RedirectAttributes redirectAttributes) {

        return "customer/list";
    }

    @GetMapping(value = "/add")
    public String add(ModelMap modelMap, RedirectAttributes redirectAttributes) {

        return "customer/add";
    }

    @PostMapping(value = "/save")
    public String save(ModelMap modelMap, RedirectAttributes redirectAttributes) {

        try {

        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/";
        }
        return "redirect:/customer/list";
    }

    @GetMapping(value = "/edit")
    public String edit() {

        return "redirect:/customer/list";
    }

    @PostMapping(value = "/update")
    public String update() {

        return "redirect:/customer/list";
    }

    @GetMapping(value = "/{customerId}")
    public String show(@PathVariable("customerId") Long customerId, ModelMap modelMap, RedirectAttributes redirectAttributes) {

        try {


        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/";
        }

        return "customer/show";
    }

    @GetMapping(value = "/delete")
    public String delete() {

        return "redirect:/customer/list";
    }
}
