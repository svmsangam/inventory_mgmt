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
@RequestMapping("/subcategory")
public class SubcategoryController {

    @Autowired
    private IUserApi userApi;


    @GetMapping(value="/")
    public String index(RedirectAttributes redirectAttributes){


        return "redirect:/subcategory/list";
    }

    @GetMapping(value="/list")
    public String list(ModelMap modelMap , RedirectAttributes redirectAttributes){


        return "subcategory/list";
    }

    @GetMapping(value="/add")
    public String add(ModelMap  modelMap , RedirectAttributes redirectAttributes){

        return "subcategory/add";
    }

    @PostMapping(value="/save")
    public String save(ModelMap modelMap , RedirectAttributes redirectAttributes){

        try{


        }catch (Exception e){
            e.printStackTrace();
            return "redirect:/";
        }
        return "redirect:/subcategory/list";
    }

    @GetMapping(value="/edit")
    public String edit(@RequestParam("subcategory") long subcategoryId,ModelMap modelMap , RedirectAttributes redirectAttributes){


        return "/subcategory/edit";
    }

    @PostMapping(value="/update")
    public String update(RedirectAttributes redirectAttributes){

       return "redirect:/subcategory/list";
    }

    @GetMapping(value="/show")
    public String show(RedirectAttributes redirectAttributes){

        return "redirect:/subcategory/list";
    }

    @GetMapping(value="/delete")
    public String delete(@RequestParam("subcategory") long subcategoryId, RedirectAttributes redirectAttributes){

       return "redirect:/subcategory/list";
    }
}
