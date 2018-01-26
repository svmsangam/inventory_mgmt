package com.inventory.web.controller;

import com.inventory.core.api.iapi.ICityInfoApi;
import com.inventory.core.api.iapi.IServiceInfoApi;
import com.inventory.core.api.iapi.ISubscriberApi;
import com.inventory.core.api.iapi.IUserApi;
import com.inventory.core.model.dto.InvUserDTO;
import com.inventory.core.model.dto.SubscriberDTO;
import com.inventory.core.model.enumconstant.Permission;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.util.Authorities;
import com.inventory.web.util.AuthenticationUtil;
import com.inventory.web.util.StringConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;

/**
 * Created by dhiraj on 1/25/18.
 */
@Controller
@RequestMapping("subscriber")
public class SubscriberController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IUserApi userApi;

    @Autowired
    private ISubscriberApi subscriberApi;

    @Autowired
    private IServiceInfoApi serviceInfoApi;

    @Autowired
    private ICityInfoApi cityInfoApi;

    @GetMapping(value = "/list")
    public String list(ModelMap modelMap, RedirectAttributes redirectAttributes) {

        try {

        /*current user checking start*/
            InvUserDTO currentUser = AuthenticationUtil.getCurrentUser(userApi);

            if (currentUser == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Athentication failed");
                return "redirect:/logout";
            }

            if (!(currentUser.getUserauthority().contains(Authorities.SYSTEM) && currentUser.getUserauthority().contains(Authorities.AUTHENTICATED))) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Athentication failed");
                return "redirect:/logout";
            }

            modelMap.put(StringConstants.SUBSCRIBER_LIST , subscriberApi.list(Status.ACTIVE));

        /*current user checking end*/

        } catch (Exception e) {

            logger.error("Exception on subcategory controller : " + Arrays.toString(e.getStackTrace()));
            return "redirect:/500";
        }

        return "subscriber/list";
    }

    @GetMapping(value = "/add")
    public String add(ModelMap modelMap, RedirectAttributes redirectAttributes) {

        try {

        /*current user checking start*/
            InvUserDTO currentUser = AuthenticationUtil.getCurrentUser(userApi);

            if (currentUser == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Athentication failed");
                return "redirect:/logout";
            }

            if (!(currentUser.getUserauthority().contains(Authorities.SYSTEM) && currentUser.getUserauthority().contains(Authorities.AUTHENTICATED))) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Athentication failed");
                return "redirect:/logout";
            }

            /*current user checking end*/

            modelMap.put(StringConstants.SERVICE_LIST , serviceInfoApi.list(Status.ACTIVE));
            modelMap.put(StringConstants.CITY_LIST , cityInfoApi.list());

        } catch (Exception e) {

            logger.error("Exception on subcategory controller : " + Arrays.toString(e.getStackTrace()));
            return "redirect:/500";
        }

        return "subscriber/add";
    }

    @PostMapping(value = "/save")
    public String save(@ModelAttribute("subscriber")SubscriberDTO subscriberDTO , ModelMap modelMap, RedirectAttributes redirectAttributes) {

        try {

        /*current user checking start*/
            InvUserDTO currentUser = AuthenticationUtil.getCurrentUser(userApi);

            if (currentUser == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Athentication failed");
                return "redirect:/logout";
            }

            if (!(currentUser.getUserauthority().contains(Authorities.SYSTEM) && currentUser.getUserauthority().contains(Authorities.AUTHENTICATED))) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Athentication failed");
                return "redirect:/logout";
            }

            /*current user checking end*/

            subscriberDTO.setCreatedById(currentUser.getUserId());

            subscriberDTO = subscriberApi.save(subscriberDTO);

        } catch (Exception e) {

            e.printStackTrace();
            logger.error("Exception on subcategory controller : " + Arrays.toString(e.getStackTrace()));
            return "redirect:/500";
        }

        return "redirect:/show?subscriberId=" + subscriberDTO.getSubscriberId();
    }

    @GetMapping(value = "/show")
    public String show(@RequestParam("subscriberId")long subscriberId ,  ModelMap modelMap, RedirectAttributes redirectAttributes) {

        try {

        /*current user checking start*/
            InvUserDTO currentUser = AuthenticationUtil.getCurrentUser(userApi);

            if (currentUser == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Athentication failed");
                return "redirect:/logout";
            }

            if (!(currentUser.getUserauthority().contains(Authorities.SYSTEM) && currentUser.getUserauthority().contains(Authorities.AUTHENTICATED))) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Athentication failed");
                return "redirect:/logout";
            }


        /*current user checking end*/

        modelMap.put(StringConstants.SUBSCRIBER , subscriberApi.show(Status.ACTIVE , subscriberId));

        } catch (Exception e) {

            logger.error("Exception on subcategory controller : " + Arrays.toString(e.getStackTrace()));
            return "redirect:/500";
        }

        return "subscriber/show";
    }

}

