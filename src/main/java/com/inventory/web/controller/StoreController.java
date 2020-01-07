package com.inventory.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.inventory.core.api.iapi.ICityInfoApi;
import com.inventory.core.api.iapi.IStoreUserInfoApi;
import com.inventory.core.api.iapi.IUserApi;
import com.inventory.core.model.dto.InvUserDTO;
import com.inventory.core.model.dto.StoreInfoDTO;
import com.inventory.web.util.AuthenticationUtil;
import com.inventory.web.util.LoggerUtil;
import com.inventory.web.util.StringConstants;

@Controller
@RequestMapping("store")
public class StoreController {

	@Autowired
	private ICityInfoApi cityInfoApi;

	@Autowired
	private IUserApi userApi;

	@Autowired
	private IStoreUserInfoApi storeUserInfoApi;

	@GetMapping(value = "/list")
	@PreAuthorize("hasRole('ROLE_SUPERADMINISTRATOR')")
	public String list(ModelMap modelMap, RedirectAttributes redirectAttributes) {
		try {

			InvUserDTO currentUser = AuthenticationUtil.getCurrentUser(userApi);

			List<StoreInfoDTO> storeInfoDTOList = storeUserInfoApi.getAllStoreByUser(currentUser.getUserId());
			LoggerUtil.logMessage(this.getClass(), "total store size ===>> " + storeInfoDTOList.size());
			modelMap.put(StringConstants.CITY_LIST, cityInfoApi.list());

			modelMap.put(StringConstants.STORE_LIST, storeInfoDTOList);

			if (currentUser.getStoreId() == null) {
				modelMap.put(StringConstants.SUPERADMINSELECTSTORE, 0);
			} else {
				modelMap.put(StringConstants.SUPERADMINSELECTSTORE, currentUser.getStoreId());
			}

			return "store/listStore";

		} catch (Exception e) {
			LoggerUtil.logException(this.getClass(), e);
			return "redirect:/";
		}
	}

}
