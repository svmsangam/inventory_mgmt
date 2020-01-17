package com.inventory.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.inventory.core.api.iapi.IItemInfoApi;
import com.inventory.core.api.iapi.IProductInfoApi;
import com.inventory.core.api.iapi.ISubcategoryInfoApi;
import com.inventory.core.api.iapi.IUnitInfoApi;
import com.inventory.core.api.iapi.IUserApi;
import com.inventory.core.model.dto.InvUserDTO;
import com.inventory.core.model.dto.ProductFilterDTO;
import com.inventory.core.model.dto.ProductInfoDTO;
import com.inventory.core.model.enumconstant.Permission;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.model.enumconstant.TrendingLevel;
import com.inventory.core.util.Authorities;
import com.inventory.core.validation.ProductInfoValidation;
import com.inventory.web.error.ProductInfoError;
import com.inventory.web.util.AuthenticationUtil;
import com.inventory.web.util.LoggerUtil;
import com.inventory.web.util.PageInfo;
import com.inventory.web.util.StringConstants;
import com.inventory.web.util.UIUtil;

/**
 * Created by dhiraj on 8/23/17.
 */
@Controller
@RequestMapping("product")
public class ProductInfoController {

	@Autowired
	private IUserApi userApi;

	@Autowired
	private IProductInfoApi productInfoApi;

	@Autowired
	private IUnitInfoApi unitInfoApi;

	@Autowired
	private ISubcategoryInfoApi subcategoryInfoApi;

	@Autowired
	private IItemInfoApi itemInfoApi;

	@Autowired
	private ProductInfoValidation productInfoValidation;

	@GetMapping(value = "/list")
	@PreAuthorize("hasAnyRole('ROLE_SUPERADMINISTRATOR','ROLE_ADMINISTRATOR','ROLE_USER,ROLE_AUTHENTICATED')")
	public String list(@RequestParam(value = "pageNo", required = false) Integer page, ModelMap modelMap,
			RedirectAttributes redirectAttributes) {

		try {

			/* current user checking start */
			InvUserDTO currentUser = AuthenticationUtil.getCurrentUser(userApi);

			if (currentUser.getUserauthority().contains(Authorities.USER)
					& !AuthenticationUtil.checkPermission(currentUser, Permission.PRODUCT_VIEW)) {
				redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Access deniled");
				return "redirect:/";// access deniled page
			}

			if (currentUser.getStoreId() == null) {
				redirectAttributes.addFlashAttribute(StringConstants.INFO, UIUtil.addStoreMessage());
				return "redirect:/store/list";// store not assigned page
			}

			/* current user checking end */

			if (page == null) {
				page = 1;
			}

			if (page < 1) {
				page = 1;
			}

			int currentpage = page - 1;

			long totalList = productInfoApi.countList(Status.ACTIVE, currentUser.getStoreId());

			int totalpage = (int) Math.ceil(totalList / PageInfo.pageList);

			if (currentpage > totalpage || currentpage < 0) {
				currentpage = 0;
			}

			List<Integer> pagesnumbers = PageInfo.PageLimitCalculator(page, totalpage, PageInfo.numberOfPage);
			modelMap.put(StringConstants.TRENDING_LIST, TrendingLevel.values());
			modelMap.put(StringConstants.SUBCATEGORY_LIST,
					subcategoryInfoApi.getTree(Status.ACTIVE, currentUser.getStoreId()));
			modelMap.put("lastpage", totalpage);
			modelMap.put("currentpage", page);
			modelMap.put("pagelist", pagesnumbers);
			modelMap.put(StringConstants.PRODUCT_LIST,
					productInfoApi.list(Status.ACTIVE, currentUser.getStoreId(), currentpage, (int) PageInfo.pageList));

		} catch (Exception e) {

			LoggerUtil.logException(this.getClass(), e);
			return "redirect:/500";
		}

		return "product/list";
	}

	@GetMapping(value = "/add")
	@PreAuthorize("hasAnyRole('ROLE_SUPERADMINISTRATOR','ROLE_ADMINISTRATOR','ROLE_USER,ROLE_AUTHENTICATED')")
	public String add(RedirectAttributes redirectAttributes, ModelMap modelMap) {

		try {

			/* current user checking start */
			InvUserDTO currentUser = AuthenticationUtil.getCurrentUser(userApi);

			if (currentUser.getUserauthority().contains(Authorities.USER)
					& !AuthenticationUtil.checkPermission(currentUser, Permission.PRODUCT_CREATE)) {
				redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Access deniled");
				return "redirect:/";// access deniled page
			}

			if (currentUser.getStoreId() == null) {
				redirectAttributes.addFlashAttribute(StringConstants.INFO, UIUtil.addStoreMessage());
				return "redirect:/store/list";// store not assigned page
			}
			/* current user checking end */

			modelMap.put(StringConstants.UNIT_LIST, unitInfoApi.list(Status.ACTIVE, currentUser.getStoreId()));

			modelMap.put(StringConstants.SUBCATEGORY_LIST,
					subcategoryInfoApi.getTree(Status.ACTIVE, currentUser.getStoreId()));

			modelMap.put(StringConstants.TRENDING_LIST, TrendingLevel.values());

		} catch (Exception e) {

			LoggerUtil.logException(this.getClass(), e);
			return "redirect:/500";
		}

		return "product/add";
	}

	@PostMapping(value = "/save")
	@PreAuthorize("hasAnyRole('ROLE_SUPERADMINISTRATOR','ROLE_ADMINISTRATOR','ROLE_USER,ROLE_AUTHENTICATED')")
	public String save(@ModelAttribute("product") ProductInfoDTO productInfoDTO, BindingResult bindingResult,
			ModelMap modelMap, RedirectAttributes redirectAttributes) {

		try {

			/* current user checking start */
			InvUserDTO currentUser = AuthenticationUtil.getCurrentUser(userApi);

			if (currentUser.getUserauthority().contains(Authorities.USER)
					& !AuthenticationUtil.checkPermission(currentUser, Permission.PRODUCT_CREATE)) {
				redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Access deniled");
				return "redirect:/";// access deniled page
			}

			if (currentUser.getStoreId() == null) {
				redirectAttributes.addFlashAttribute(StringConstants.INFO, UIUtil.addStoreMessage());
				return "redirect:/store/list";// store not assigned page
			}

			/* current user checking end */

			if (productInfoDTO == null) {
				redirectAttributes.addFlashAttribute(StringConstants.ERROR, "bad request");
				return "redirect:/product/add";
			}

			synchronized (this.getClass()) {
				productInfoDTO.setStoreInfoId(currentUser.getStoreId());
				productInfoDTO.setCreatedById(currentUser.getUserId());

				ProductInfoError error = productInfoValidation.onSave(productInfoDTO, bindingResult);

				if (!error.isValid()) {
					modelMap.put(StringConstants.PRODUCT_ERROR, error);
					modelMap.put(StringConstants.PRODUCT, productInfoDTO);
					modelMap.put(StringConstants.UNIT_LIST, unitInfoApi.list(Status.ACTIVE, currentUser.getStoreId()));
					modelMap.put(StringConstants.SUBCATEGORY_LIST,
							subcategoryInfoApi.getTree(Status.ACTIVE, currentUser.getStoreId()));
					modelMap.put(StringConstants.TRENDING_LIST, TrendingLevel.values());

					return "product/add";
				}

				productInfoDTO = productInfoApi.save(productInfoDTO);
				redirectAttributes.addFlashAttribute(StringConstants.MESSAGE, "product saved successfully");
			}

		} catch (Exception e) {

			LoggerUtil.logException(this.getClass(), e);
			return "redirect:/500";
		}

		return "redirect:/product/" + productInfoDTO.getProductId();
	}

	@GetMapping(value = "/{productId}")
	@PreAuthorize("hasAnyRole('ROLE_SUPERADMINISTRATOR','ROLE_ADMINISTRATOR','ROLE_USER,ROLE_AUTHENTICATED')")
	public String show(@PathVariable("productId") Long productId, RedirectAttributes redirectAttributes,
			ModelMap modelMap) {

		try {

			/* current user checking start */
			InvUserDTO currentUser = AuthenticationUtil.getCurrentUser(userApi);

			if (currentUser.getUserauthority().contains(Authorities.USER)
					& !AuthenticationUtil.checkPermission(currentUser, Permission.PRODUCT_VIEW)) {
				redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Access deniled");
				return "redirect:/";// access deniled page
			}

			if (currentUser.getStoreId() == null) {
				redirectAttributes.addFlashAttribute(StringConstants.INFO, UIUtil.addStoreMessage());
				return "redirect:/store/list";// store not assigned page
			}
			/* current user checking end */

			if (productId == null) {
				redirectAttributes.addFlashAttribute(StringConstants.ERROR, "invalid product");
				return "redirect:/product/list";// store not assigned page
			}

			if (productId < 1) {
				redirectAttributes.addFlashAttribute(StringConstants.ERROR, "invalid product");
				return "redirect:/product/list";// store not assigned page
			}

			ProductInfoDTO productInfoDTO = productInfoApi.getByIdAndStoreAndStatus(productId, currentUser.getStoreId(),
					Status.ACTIVE);

			if (productInfoDTO == null) {
				redirectAttributes.addFlashAttribute(StringConstants.ERROR, "product not found");
				return "redirect:/product/list";// store not assigned page
			}

			modelMap.put(StringConstants.PRODUCT, productInfoDTO);

			modelMap.put(StringConstants.ITEM_LIST,
					itemInfoApi.getAllByProductAndStatusAndStore(productId, Status.ACTIVE, currentUser.getStoreId()));

			modelMap.put(StringConstants.CATEGORY_LIST, productInfoApi.getAllCategory(productInfoDTO.getSubCategoryId(),
					currentUser.getStoreId(), Status.ACTIVE, new ArrayList<>()));

		} catch (Exception e) {

			LoggerUtil.logException(this.getClass(), e);
			return "redirect:/500";
		}

		return "product/show";
	}

	@GetMapping(value = "/edit")
	@PreAuthorize("hasAnyRole('ROLE_SUPERADMINISTRATOR','ROLE_ADMINISTRATOR','ROLE_USER,ROLE_AUTHENTICATED')")
	public String edit(@RequestParam("productId") long productId, RedirectAttributes redirectAttributes,
			ModelMap modelMap) {

		try {

			/* current user checking start */
			InvUserDTO currentUser = AuthenticationUtil.getCurrentUser(userApi);

			if (currentUser.getUserauthority().contains(Authorities.USER)
					& !AuthenticationUtil.checkPermission(currentUser, Permission.PRODUCT_UPDATE)) {
				redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Access deniled");
				return "redirect:/";// access deniled page
			}

			if (currentUser.getStoreId() == null) {
				redirectAttributes.addFlashAttribute(StringConstants.INFO, UIUtil.addStoreMessage());
				return "redirect:/store/list";// store not assigned page
			}
			/* current user checking end */

			if (productId < 0) {
				redirectAttributes.addFlashAttribute(StringConstants.ERROR, "bad request");
				return "redirect:/product/list";// store not assigned page
			}

			ProductInfoDTO productInfoDTO = productInfoApi.getByIdAndStoreAndStatus(productId, currentUser.getStoreId(),
					Status.ACTIVE);

			if (productInfoDTO == null) {
				redirectAttributes.addFlashAttribute(StringConstants.ERROR, "product not found");
				return "redirect:/product/list";// store not assigned page
			}

			modelMap.put(StringConstants.PRODUCT, productInfoDTO);

			modelMap.put(StringConstants.UNIT_LIST, unitInfoApi.list(Status.ACTIVE, currentUser.getStoreId()));

			modelMap.put(StringConstants.SUBCATEGORY_LIST,
					subcategoryInfoApi.getTree(Status.ACTIVE, currentUser.getStoreId()));

			modelMap.put(StringConstants.TRENDING_LIST, TrendingLevel.values());

		} catch (Exception e) {

			LoggerUtil.logException(this.getClass(), e);
			return "redirect:/500";
		}

		return "product/edit";
	}

	@PostMapping(value = "/update")
	@PreAuthorize("hasAnyRole('ROLE_SUPERADMINISTRATOR','ROLE_ADMINISTRATOR','ROLE_USER,ROLE_AUTHENTICATED')")
	public String update(@ModelAttribute("product") ProductInfoDTO productInfoDTO, BindingResult bindingResult,
			ModelMap modelMap, RedirectAttributes redirectAttributes) {

		try {

			/* current user checking start */
			InvUserDTO currentUser = AuthenticationUtil.getCurrentUser(userApi);

			if (currentUser.getUserauthority().contains(Authorities.USER)
					& !AuthenticationUtil.checkPermission(currentUser, Permission.PRODUCT_CREATE)) {
				redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Access deniled");
				return "redirect:/";// access deniled page
			}

			if (currentUser.getStoreId() == null) {
				redirectAttributes.addFlashAttribute(StringConstants.INFO, UIUtil.addStoreMessage());
				return "redirect:/store/list";// store not assigned page
			}

			/* current user checking end */

			if (productInfoDTO == null) {
				redirectAttributes.addFlashAttribute(StringConstants.ERROR, "bad request");
				return "redirect:/product/add";
			}

			synchronized (this.getClass()) {
				productInfoDTO.setStoreInfoId(currentUser.getStoreId());

				ProductInfoError error = productInfoValidation.onUpdate(productInfoDTO, bindingResult);

				if (!error.isValid()) {
					modelMap.put(StringConstants.PRODUCT_ERROR, error);
					modelMap.put(StringConstants.PRODUCT, productInfoDTO);
					modelMap.put(StringConstants.UNIT_LIST, unitInfoApi.list(Status.ACTIVE, currentUser.getStoreId()));
					modelMap.put(StringConstants.SUBCATEGORY_LIST,
							subcategoryInfoApi.getTree(Status.ACTIVE, currentUser.getStoreId()));
					modelMap.put(StringConstants.TRENDING_LIST, TrendingLevel.values());

					return "product/edit";
				}

				productInfoDTO = productInfoApi.update(productInfoDTO);
				redirectAttributes.addFlashAttribute(StringConstants.MESSAGE, "product updated successfully");
			}

		} catch (Exception e) {

			LoggerUtil.logException(this.getClass(), e);
			return "redirect:/500";
		}

		return "redirect:/product/" + productInfoDTO.getProductId();
	}

	@GetMapping(value = "/filter")
	@PreAuthorize("hasAnyRole('ROLE_SUPERADMINISTRATOR','ROLE_ADMINISTRATOR','ROLE_USER,ROLE_AUTHENTICATED')")
	public String filter(@ModelAttribute("terms") ProductFilterDTO filterTerms, BindingResult result, ModelMap modelMap,
			RedirectAttributes redirectAttributes) {

		try {

			/* current user checking start */
			InvUserDTO currentUser = AuthenticationUtil.getCurrentUser(userApi);

			if (currentUser.getUserauthority().contains(Authorities.USER)
					& !AuthenticationUtil.checkPermission(currentUser, Permission.PRODUCT_VIEW)) {
				redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Access deniled");
				return "redirect:/";// access deniled page
			}

			if (currentUser.getStoreId() == null) {
				redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Store not assigned");
				return "redirect:/";// store not assigned page
			}

			/* current user checking end */

			if (filterTerms == null) {
				redirectAttributes.addFlashAttribute(StringConstants.ERROR, "filter terms required");
				return "redirect:/product/list";

			}

			if (filterTerms.getName() == null & filterTerms.getSubCategoryId() < 1
					&& filterTerms.getTrendingLevel() == null
					&& (filterTerms.getGreaterThanInStock() < 1 || filterTerms.getLessThanInStock() < 1)) {
				redirectAttributes.addFlashAttribute(StringConstants.ERROR, "invalid filter terms");
				return "redirect:/product/list";

			}

			Integer page = filterTerms.getPage();

			if (page == null) {
				page = 1;
			}

			if (page < 1) {
				page = 1;
			}

			int currentpage = page - 1;

			filterTerms.setStatus(Status.ACTIVE);
			filterTerms.setStoreInfoId(currentUser.getStoreId());

			long totalList = productInfoApi.filterCount(filterTerms);

			int totalpage = (int) Math.ceil(totalList / PageInfo.pageList);

			if (currentpage > totalpage || currentpage < 0) {
				currentpage = 0;
			}

			List<Integer> pagesnumbers = PageInfo.PageLimitCalculator(page, totalpage, PageInfo.numberOfPage);

			filterTerms.setPage(currentpage);
			filterTerms.setSize((int) PageInfo.pageList);

			modelMap.put(StringConstants.PRODUCT_LIST, productInfoApi.filter(filterTerms));
			modelMap.put(StringConstants.TRENDING_LIST, TrendingLevel.values());
			modelMap.put("filterDTO", filterTerms);
			modelMap.put(StringConstants.SUBCATEGORY_LIST,
					subcategoryInfoApi.getTree(Status.ACTIVE, currentUser.getStoreId()));
			modelMap.put("lastpage", totalpage);
			modelMap.put("currentpage", page);
			modelMap.put("pagelist", pagesnumbers);
			modelMap.put("totalResult", totalList);

		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/";
		}

		return "product/filter";
	}

}
