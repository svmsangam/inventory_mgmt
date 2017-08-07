package com.inventory.web.controller;

import com.inventory.core.api.iapi.IUserApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


	@Controller
	@RequestMapping("/tag")
	public class TagController {

		@Autowired
		private IUserApi userApi;


	    @GetMapping(value="/")
	    public String index(RedirectAttributes redirectAttributes){



			return "redirect:/tag/list";
	    }

	    @GetMapping(value="/list")
	    public String list(ModelMap modelMap , RedirectAttributes redirectAttributes){


	        return "tag/list";
	    }

	    @GetMapping(value="/add")
	    public String add(RedirectAttributes redirectAttributes){

			return "tag/add";
	    }

	    @PostMapping(value="/save")
	    public String save(ModelMap modelMap , RedirectAttributes redirectAttributes){

	        try{


	        }catch (Exception e){
	            e.printStackTrace();
	            return "redirect:/";
	        }
	        return "redirect:/tag/list";
	    }

	    @GetMapping(value="/edit")
	    public String edit(@RequestParam("tag") Long tagId,ModelMap modelMap , RedirectAttributes redirectAttributes){

	    	try {


				return "/tag/edit";

			}catch (Exception e){
	    		e.printStackTrace();;
	    		return "redirect:/";
			}


	    }

	    @PostMapping(value="/update")
	    public String update(RedirectAttributes redirectAttributes){

	    	try {

	    		return "redirect:/tag/list";

			}catch (Exception e){
	    		e.printStackTrace();
	    		return "redirect:/";
			}


	    }

	    @GetMapping(value="/{tagId}")
	    public String show(@PathVariable("tagId") Long tagId){

	        return "redirect:/tag/list";
	    }

	    @GetMapping(value="/delete")
	    public String delete(@RequestParam("tag") long tagId, RedirectAttributes redirectAttributes){

	    	try {

				return "redirect:/tag/list";
			}catch (Exception e){
	    		e.printStackTrace();
	    		return "redirect:/";
			}

	    }
	}



