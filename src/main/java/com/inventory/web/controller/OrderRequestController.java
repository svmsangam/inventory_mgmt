package com.inventory.web.controller;

import com.inventory.core.api.iapi.IUserApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/order")
public class OrderRequestController {

    @Autowired
    private IUserApi userApi;

    @GetMapping(value = "/")
    public String index(RedirectAttributes redirectAttributes) {

        return "redirect:/OrderInfo/list";
    }

    @GetMapping(value = "/list/saleOrder")
    public String listSale(@RequestParam(value = "pageNo", required = false) Integer page, @RequestParam(value = "sort", required = false) String sort, @RequestParam(value = "direction", required = false) String direction, ModelMap modelMap, RedirectAttributes redirectAttributes) {

        try {


        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/";
        }

        return "orderRequest/list";
    }

    @GetMapping(value = "/list/purchaseorder")
    public String listPurchase(@RequestParam(value = "pageNo", required = false) Integer page, @RequestParam(value = "sort", required = false) String sort, @RequestParam(value = "direction", required = false) String direction, ModelMap modelMap, RedirectAttributes redirectAttributes) {

        try {


        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/";
        }

        return "orderRequest/list";
    }

    @GetMapping(value = "/addsale")
    public String addOnSale(ModelMap modelMap, RedirectAttributes redirectAttributes) {

        try {


            return "orderRequest/addSale";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/";
        }
    }

    @GetMapping(value = "/addpurchase")
    public String addOnPurchase(ModelMap modelMap, RedirectAttributes redirectAttributes) {

        try {


            return "orderRequest/addPurchase";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/";
        }
    }

    @PostMapping(value = "/savesale")
    public String saveOnSale(ModelMap modelMap, RedirectAttributes redirectAttributes) {

        try {


        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/";
        }
        return "redirect:/order/";
    }

    @PostMapping(value = "/savepurchase")
    public String saveOnPurchase(ModelMap modelMap, RedirectAttributes redirectAttributes) {

        try {


        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/";
        }
        return "redirect:/order/";
    }

    @GetMapping(value = "/edit")
    public String edit() {

        return "redirect:/orderRequest/list";
    }

    @PostMapping(value = "/update")
    public String update() {

        return "redirect:/orderRequest/list";
    }

    @GetMapping(value = "/{orderId}")
    public String show(@PathVariable("orderId") Long orderId, ModelMap modelMap, RedirectAttributes redirectAttributes) {

        try {


        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/";
        }
        return "orderRequest/show";
    }

    @GetMapping(value = "/delete")
    public String delete() {

        return "redirect:/orderRequest/list";
    }
}



