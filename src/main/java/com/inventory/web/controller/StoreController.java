package com.inventory.web.controller;

import com.inventory.core.api.iapi.ICityInfoApi;
import com.inventory.core.api.iapi.IStoreInfoApi;
import com.inventory.core.api.iapi.IUserApi;
import com.inventory.core.model.dto.InvUserDTO;
import com.inventory.core.model.enumconstant.Status;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("store")
public class StoreController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IStoreInfoApi storeInfoApi;

    @Autowired
    private ICityInfoApi cityInfoApi;

    @Autowired
    private IUserApi userApi;

    @GetMapping( value = "/list")
    public String list(ModelMap modelMap , RedirectAttributes redirectAttributes) {
        try {

            InvUserDTO currentUser = AuthenticationUtil.getCurrentUser(userApi);

            if (currentUser == null){
                redirectAttributes.addFlashAttribute(StringConstants.ERROR , "Athentication failed");
                return "redirect:/logout";
            }

            if (currentUser.getUserauthority().contains(Authorities.SUPERADMIN) && currentUser.getUserauthority().contains(Authorities.AUTHENTICATED)) {

                modelMap.put(StringConstants.CITY_LIST, cityInfoApi.list());

                modelMap.put(StringConstants.STORE_LIST , storeInfoApi.list(Status.ACTIVE));

                return "store/listStore";
            }else {

                redirectAttributes.addFlashAttribute(StringConstants.ERROR , "Access deniled");
                return "redirect:/";
            }

        } catch (Exception e) {
            logger.error("Stack trace: " + e.getStackTrace());
            return "redirect:/";
        }
    }


}
