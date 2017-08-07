package com.inventory.web.controller;

import com.inventory.web.error.CityError;
import com.inventory.web.util.StringConstants;
import com.inventory.core.api.iapi.ICityInfoApi;
import com.inventory.core.api.iapi.IStateInfoApi;
import com.inventory.core.model.dto.CityInfoDTO;
import com.inventory.core.model.dto.PageDTO;
import com.inventory.core.model.dto.PageableDTO;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.validation.CityInfoValidation;
import com.inventory.web.util.AuthenticationUtil;
import com.inventory.web.util.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/city")
public class CityController {

	@Autowired
	private ICityInfoApi cityInfoApi;

	@Autowired
	private IStateInfoApi stateInfoApi;
	
	@Autowired
	private CityInfoValidation cityInfoValidation;
	
	private Logger logger = LoggerFactory.getLogger(CityController.class);
	
	@GetMapping( value = "/list")
	public String listCity(/*@RequestAttribute(value = "pageable" , required = false)PageableDTO pageableDTO,*/ ModelMap modelMap) {
		try {
			if (AuthenticationUtil.getCurrentUser() != null) {

				//pageableDTO = pageableChecker(pageableDTO);

				//modelMap.put(StringConstants.CITY_LIST, cityInfoApi.list(pageableDTO));
				modelMap.put(StringConstants.CITY_LIST, cityInfoApi.list());

				//modelMap.put(StringConstants.PAGE, getPage(pageableDTO));

				return "city/listCities";

			}

			return "redirect:/";
		} catch (Exception e) {
			logger.error("Stack trace: " + e.getStackTrace());
			return "redirect:/";
		}
	}
	
	
	@GetMapping(value = "/delete")
	public String removeCity(ModelMap modelMap, @RequestParam("id") long id, RedirectAttributes redirectAttributes) {
		try {
			if (AuthenticationUtil.getCurrentUser() != null) {
				cityInfoApi.delete(id);
				return "redirect:/city/list";
			}
			return "redirect:/";
		} catch (Exception e) {
			logger.error("Stack trace: " + e.getStackTrace());
			return "redirect:/";
		}
	}
	
	@GetMapping(value = "/add")
	public String addCity(ModelMap modelMap) {
		try {
			if (AuthenticationUtil.getCurrentUser() != null) {
				modelMap.put(StringConstants.STATE_LIST , stateInfoApi.list());
				return "city/addCity";

			}
			return "redirect:/";
		} catch (Exception e) {
			logger.error("Stack trace: " + e.getStackTrace());
			return "redirect:/";
		}
	}
	
	@PostMapping(value = "/save")
	public String addCity(ModelMap modelMap, @ModelAttribute("cityDto") CityInfoDTO cityDto,
			RedirectAttributes redirectAttributes) {
		try {
			if (AuthenticationUtil.getCurrentUser() != null) {

				CityError cityError = new CityError();
				cityError = cityInfoValidation.cityValidationOnSave(cityDto);
				if (cityError.isValid()) {
					cityInfoApi.save(cityDto);
					return "redirect:/city/list";
				} else {
					modelMap.put(StringConstants.ERROR , cityError);
					modelMap.put(StringConstants.STATE_LIST , stateInfoApi.list());
					modelMap.put(StringConstants.CITY , cityDto);
					return "city/addCity";
				}

			}
			return "redirect:/";
		} catch (Exception e) {
			logger.error("Stack trace: " + e.getStackTrace());
			return "redirect:/";
		}
	}
	
	@GetMapping(value = "/edit")
	public String editCity(ModelMap modelMap, @RequestParam("cityId") long cityId , RequestAttributes requestAttribute) {
		try {
			if (AuthenticationUtil.getCurrentUser() != null) {

				CityInfoDTO cityDto = cityInfoApi.show(cityId);

				if (cityDto == null){
					return "redirect:/city/list";
				}

				modelMap.put(StringConstants.STATE_LIST , stateInfoApi.list());
				modelMap.put(StringConstants.CITY , cityDto);
				return "city/editCity";

			}
			return "redirect:/";
		} catch (Exception e) {
			logger.error("Stack trace: " + e.getStackTrace());
			return "redirect:/";
		}
	}
	
	@PostMapping(value = "/update")
	public String updateCity(ModelMap modelMap, @ModelAttribute("cityDto") CityInfoDTO cityDto,
			RedirectAttributes redirectAttributes) {
		try {
			if (AuthenticationUtil.getCurrentUser() != null) {
				CityError cityError = new CityError();
				//cityError = cityInfoValidation.cityValidationOnUpdate(cityDto);
				//if (cityError.isValid()) {
					cityInfoApi.update(cityDto);
					return "redirect:/city/list";
				/*} else {
					modelMap.put(StringConstants.ERROR , cityError);
					modelMap.put(StringConstants.STATE_LIST , stateService.list());
					modelMap.put(StringConstants.CITY , cityDto);
					return "city/editCity";
				}*/

			}
			return "redirect:/";
		} catch (Exception e) {
			logger.error("Stack trace: " + e.getStackTrace());
			return "redirect:/";
		}
	}

	private PageableDTO pageableChecker(PageableDTO pageableDTO){

		if(pageableDTO.getPage() == null | 1 > pageableDTO.getPage()){
			pageableDTO.setPage(1);
		}

		pageableDTO.setPage(pageableDTO.getPage()-1);

		long totalList = cityInfoApi.cityCount(Status.ACTIVE);
		int totalpage =(int) Math.ceil(totalList/ PageInfo.pageList);

		if(pageableDTO.getPage() > totalpage){
			pageableDTO.setPage(0);
		}

		if (pageableDTO.getProperties() == null){
			pageableDTO.setProperties("id");
		}

		if (Objects.equals(pageableDTO.getProperties(), "state")){

			pageableDTO.setProperties("stateInfo.name");
		}

		if (Objects.equals(pageableDTO.getProperties(), "country")){

			pageableDTO.setProperties("stateInfo.countryInfo.name");
		}

		if (!(pageableDTO.getProperties().equals("id") | Objects.equals(pageableDTO.getProperties(), "name") | Objects.equals(pageableDTO.getProperties(), "stateInfo.name") | Objects.equals(pageableDTO.getProperties() , "stateInfo.countryInfo.name") )){

			pageableDTO.setProperties("id");
		}

		if (pageableDTO.getDirection() == null ){
			pageableDTO.setDirection("desc");
		}

		if ("desc".equals(pageableDTO.getDirection())) {

			pageableDTO.getDirectionSort(Sort.Direction.DESC);

		}else if (pageableDTO.getDirection().equals("asc")){

			pageableDTO.setDirectionSort(Sort.Direction.ASC);

		}else {
			pageableDTO.setDirectionSort(Sort.Direction.DESC);
		}

		return pageableDTO;
	}

	private PageDTO getPage(PageableDTO pageableDTO){

		PageDTO page = new PageDTO();

		long totalList = cityInfoApi.cityCount(Status.ACTIVE);

		int totalpage =(int) Math.ceil(totalList/ PageInfo.pageList);

		List<Integer> pagesnumbers = PageInfo.PageLimitCalculator(pageableDTO.getPage()+1, totalpage, PageInfo.numberOfPage);

		if ("desc".equals(pageableDTO.getDirection())) {
			page.setDirectionPage("asc");

		}else if (pageableDTO.equals("asc")){
			page.setDirectionPage("desc");

		}else {

			page.setDirectionPage("asc");
		}

		page.setLastpage(totalpage);
		page.setCurrentPage(pageableDTO.getPage()+1);
		page.setPagelist(pagesnumbers);
		page.setDirection(pageableDTO.getDirection());
		page.setSort(pageableDTO.getProperties());

		return page;
	}

}

	

