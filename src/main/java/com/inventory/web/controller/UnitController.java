package com.inventory.web.controller;

import com.inventory.core.api.iapi.IUserApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/*
 * Created by dhiraj on 6/26/17.

**/

@Controller
@RequestMapping("/unit")
public class UnitController {

    @Autowired
    private IUserApi userApi;


    @GetMapping(value="/")
    public String index(RedirectAttributes redirectAttributes){

        // user checking end

        return "redirect:/unit/list";
    }

    @GetMapping(value="/list")
    public String list(ModelMap modelMap , RedirectAttributes redirectAttributes){

        return "unit/list";
    }

    @GetMapping(value="/add")
    public String add(RedirectAttributes redirectAttributes){

        // user checking end

        return "unit/add";
    }

    @PostMapping(value="/save")
    public String save( ModelMap modelMap , RedirectAttributes redirectAttributes){

        try{


        }catch (Exception e){
            e.printStackTrace();
            return "redirect:/";
        }
        return "redirect:/unit/";
    }

    @GetMapping(value="/edit")
    public String edit(@RequestParam("unit") long unitId,ModelMap modelMap , RedirectAttributes redirectAttributes){

        try {


            return "/unit/edit";


        }catch (Exception e){
            e.printStackTrace();
            return "redirect:/";
        }

    }

    @PostMapping(value="/update")
    public String update( RedirectAttributes redirectAttributes){

        try {


            redirectAttributes.addFlashAttribute("message", "UnitInfo Edited Successfully");
            return "redirect:/unit/list";

        }catch (Exception e){
            e.printStackTrace();
            return "redirect:/";
        }

    }

    @GetMapping(value="/{unitId}")
    public String show(@PathVariable("unitId") Long unitId , ModelMap modelMap , RedirectAttributes redirectAttributes){

        try{


        }catch (Exception e){
            e.printStackTrace();
            return "redirct:/";
        }


        return "unit/show";
    }

    @GetMapping(value="/delete")
    public String delete(@RequestParam("unit") long unitId, RedirectAttributes redirectAttributes){

        try {

            return "redirect:/unit/list";

        }catch (Exception e){
            e.printStackTrace();
            return "redirect:/";
        }

    }
}
