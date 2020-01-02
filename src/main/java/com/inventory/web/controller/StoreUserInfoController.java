package com.inventory.web.controller;

import com.inventory.core.api.iapi.IStoreUserInfoApi;
import com.inventory.core.api.iapi.IUserApi;
import com.inventory.core.model.dto.InvUserDTO;
import com.inventory.core.util.Authorities;
import com.inventory.web.util.AuthenticationUtil;
import com.inventory.web.util.StringConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String store(@RequestParam("userId")long userid , ModelMap map , RedirectAttributes redirectAttributes) {
/*current user checking start*/

        try {

            InvUserDTO currentUser = AuthenticationUtil.getCurrentUser(userApi);

            if (currentUser == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Athentication failed");
                return "redirect:/logout";
            }

            if (!(currentUser.getUserauthority().contains(Authorities.SYSTEM) && currentUser.getUserauthority().contains(Authorities.AUTHENTICATED))) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Athentication failed");
                return "redirect:/logout";
            }

            if (userid < 0){
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "bad request");
                return "redirect:/";
            }

            map.put(StringConstants.STORE_LIST , storeUserInfoApi.getAllStoreByUser(userid));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "store/list";
    }

}
