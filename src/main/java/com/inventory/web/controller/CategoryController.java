package com.inventory.web.controller;

import com.inventory.core.api.iapi.IUserApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private IUserApi userApi;


    @GetMapping(value="/")
    public String index(RedirectAttributes redirectAttributes){


        return "redirect:/CategoryInfo/list";
    }

    @GetMapping(value="/list")
    public String list(ModelMap modelMap , RedirectAttributes redirectAttributes){


        return "category/list";
    }

    @GetMapping(value="/add")
    public String add(RedirectAttributes redirectAttributes){


        return "category/add";
    }

    @PostMapping(value="/save")
    public String save(ModelMap modelMap, RedirectAttributes redirectAttributes){

        return "redirect:/category/list";
    }

    @GetMapping(value="/edit")
    public String edit(@RequestParam("category") long categoryId,ModelMap modelMap , RedirectAttributes redirectAttributes){


        return "/category/edit";
    }

    @PostMapping(value="/update")
    public String update( RedirectAttributes redirectAttributes){

       return "redirect:/category/list";
    }

    @GetMapping(value="/{categoryId}")
    public String show(){

        return "redirect:/category/list";
    }

    @GetMapping(value="/delete")
    public String delete(@RequestParam("category") long categoryId, RedirectAttributes redirectAttributes){

        return "redirect:/category/list";
    }
}



