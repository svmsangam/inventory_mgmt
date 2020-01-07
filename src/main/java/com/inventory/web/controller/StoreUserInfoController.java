package com.inventory.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.inventory.core.api.iapi.IStoreUserInfoApi;
import com.inventory.core.api.iapi.IUserApi;
import com.inventory.core.model.dto.InvUserDTO;
import com.inventory.web.util.AuthenticationUtil;
import com.inventory.web.util.StringConstants;

/**
 * Created by dhiraj on 1/21/18.
 */
@Controller
@RequestMapping("user")
public class StoreUserInfoController {

	@Autowired
	private IUserApi userApi;

	@Autowired
	private IStoreUserInfoApi storeUserInfoApi;

	@GetMapping(value = "/store")
	@PreAuthorize("hasRole('ROLE_SYSTEM')")
	public String store(@RequestParam("userId") long userid, ModelMap map, RedirectAttributes redirectAttributes) {
		/* current user checking start */

		try {

			InvUserDTO currentUser = AuthenticationUtil.getCurrentUser(userApi);

			if (userid < 0) {
				redirectAttributes.addFlashAttribute(StringConstants.ERROR, "bad request");
				return "redirect:/";
			}

			map.put(StringConstants.STORE_LIST, storeUserInfoApi.getAllStoreByUser(userid));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "store/list";
	}

}
