package com.inventory.web.controller;

import com.inventory.core.api.iapi.*;
import com.inventory.core.api.impl.RecaptchaService;
import com.inventory.core.model.dto.InvUserDTO;
import com.inventory.core.model.dto.SubscriberDTO;
import com.inventory.core.model.dto.SubscriberServiceDTO;
import com.inventory.core.model.entity.Subscriber;
import com.inventory.core.model.enumconstant.Permission;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.util.Authorities;
import com.inventory.core.validation.SubscriberValidation;
import com.inventory.web.error.RenewError;
import com.inventory.web.error.SubscriberError;
import com.inventory.web.util.AuthenticationUtil;
import com.inventory.web.util.StringConstants;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dhiraj on 1/25/18.
 */
@Controller
@RequestMapping("subscriber")
public class SubscriberController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RecaptchaService captchaService;

    @Autowired
    private IUserApi userApi;

    @Autowired
    private ISubscriberApi subscriberApi;

    @Autowired
    private IServiceInfoApi serviceInfoApi;

    @Autowired
    private ICityInfoApi cityInfoApi;

    @Autowired
    private IStoreUserInfoApi storeUserInfoApi;

    @Autowired
    private SubscriberValidation subscriberValidation;

    @Autowired
    private ISendMailSSL mailApi;

    @Autowired
    private ISubscriberServiceApi subscriberServiceApi;

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

            modelMap.put(StringConstants.SUBSCRIBER_LIST, subscriberApi.list(Status.ACTIVE));

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

            modelMap.put(StringConstants.SERVICE_LIST, serviceInfoApi.list(Status.ACTIVE));
            modelMap.put(StringConstants.CITY_LIST, cityInfoApi.list());

        } catch (Exception e) {

            logger.error("Exception on subcategory controller : " + Arrays.toString(e.getStackTrace()));
            return "redirect:/500";
        }

        return "subscriber/add";
    }

    @PostMapping(value = "/save")
    public String save(@ModelAttribute("subscriber") SubscriberDTO subscriberDTO, ModelMap modelMap, RedirectAttributes redirectAttributes) {

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

        return "redirect:/subscriber/show?subscriberId=" + subscriberDTO.getSubscriberId();
    }

    @GetMapping(value = "/show")
    public String show(@RequestParam("subscriberId") long subscriberId, ModelMap modelMap, RedirectAttributes redirectAttributes) {

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

            SubscriberDTO subscriberDTO = subscriberApi.show(Status.ACTIVE, subscriberId);

            if (subscriberDTO == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "subscriber not found");

                return "redirect:/subscriber/list";
            }

            modelMap.put(StringConstants.SUBSCRIBER, subscriberDTO);
            modelMap.put(StringConstants.STORE_LIST, storeUserInfoApi.getAllStoreByUser(subscriberDTO.getUserId()));
            modelMap.put(StringConstants.USER_LIST, storeUserInfoApi.getAllUserBySuperAdmin(subscriberDTO.getUserId()));
            modelMap.put(StringConstants.SUBSCRIBER_SERVICE_LIST , subscriberServiceApi.list(Status.ACTIVE , subscriberId));
            modelMap.put(StringConstants.SERVICE_LIST , serviceInfoApi.list(Status.ACTIVE));

        } catch (Exception e) {

            e.printStackTrace();
            logger.error("Exception on subcategory controller : " + Arrays.toString(e.getStackTrace()));
            return "redirect:/500";
        }

        return "subscriber/show";
    }

    @GetMapping(value = "/service/renew")
    public String renew(@RequestParam("subscriberId") long subscriberId, @RequestParam("serviceId") long serviceId, ModelMap modelMap, RedirectAttributes redirectAttributes) {

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

        synchronized (this) {

            SubscriberDTO subscriberDTO = subscriberApi.show(Status.ACTIVE, subscriberId);

            if (subscriberDTO == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "subscriber not found");

                return "redirect:/subscriber/list";
            }

            RenewError error = subscriberValidation.onRenew(subscriberDTO.getUserId() , serviceId);

            if (!error.isValid()){

                redirectAttributes.addFlashAttribute(StringConstants.ERROR, error.getError());

                return "redirect:/subscriber/show?subscriberId=" + subscriberId;
            }

            SubscriberServiceDTO subscriberServiceDTO = subscriberServiceApi.save(serviceId , subscriberId);

            mailApi.sendHtmlMail(StringConstants.VerificationMainSender , subscriberDTO.getEmail() , getRenewMsg(subscriberServiceDTO.getServiceInfo().getTitle() ,  subscriberServiceDTO.getExpireOn() , subscriberDTO.getFullName() , subscriberServiceDTO.getServiceInfo().getTotalStore()) , "account renew");
            redirectAttributes.addFlashAttribute(StringConstants.MESSAGE, "successfully renewed");

        }

        } catch (Exception e) {

            e.printStackTrace();
            logger.error("Exception on subcategory controller : " + Arrays.toString(e.getStackTrace()));
            return "redirect:/500";
        }

        return "redirect:/subscriber/show?subscriberId=" + subscriberId;
    }

    private String getRenewMsg(String serviceName , Date expireOn, String subscribername , int totalStore){

        String msg = "";

        msg = "dear " + subscribername + " " + serviceName + " service is successfull renewed and will be expire on " + expireOn + " now your are able to manage " + totalStore + " stores thank you";

        return msg;
    }

    @GetMapping(value = "/register")
    public String register(ModelMap modelMap, RedirectAttributes redirectAttributes) {

        try {

            modelMap.put(StringConstants.CITY_LIST, cityInfoApi.list());

        } catch (Exception e) {

            logger.error("Exception on subcategory controller : " + Arrays.toString(e.getStackTrace()));
            return "redirect:/500";
        }

        return "subscriber/register";
    }

    @PostMapping("/register")
    public String signupDemo(@ModelAttribute("subscriber") SubscriberDTO subscriberDTO, @RequestParam(name = "g-recaptcha-response") String recaptchaResponse, HttpServletRequest request, RedirectAttributes redirectAttributes , ModelMap modelMap) {

        try {

            if (recaptchaResponse == null){
                modelMap.put(StringConstants.CITY_LIST, cityInfoApi.list());
                modelMap.put(StringConstants.SUBSCRIBER , subscriberDTO);
                modelMap.put(StringConstants.ERROR , "please verify captcha");

                return "subscriber/register";
            }

            synchronized (this) {

                SubscriberError error = subscriberValidation.onRegister(subscriberDTO);

                if (!error.isValid()){

                    modelMap.put(StringConstants.CITY_LIST, cityInfoApi.list());
                    modelMap.put(StringConstants.SUBSCRIBER , subscriberDTO);
                    modelMap.put(StringConstants.SUBSCRIBER_ERROR , error);

                    return "subscriber/register";

                }

                String ip = request.getRemoteAddr();

                String captchaVerifyMessage = captchaService.verifyRecaptcha(ip, recaptchaResponse);

                if (StringUtils.isNotEmpty(captchaVerifyMessage)) {

                    redirectAttributes.addFlashAttribute(StringConstants.ERROR, "invalid captcha ");

                    return "redirect:/subscriber/register";

                }


                String token = subscriberApi.register(subscriberDTO);

                mailApi.sendHtmlMail(StringConstants.VerificationMainSender , subscriberDTO.getEmail(), getVerificationMsg(token , request.getServerName()) , "email verification request");

                redirectAttributes.addFlashAttribute(StringConstants.MESSAGE, "successfully registered please check your email to activate account");

            }
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return "redirect:/login";
    }

    private String getVerificationMsg(String token , String contextPath){

        String msg = "";

        String url = "";

        if (contextPath.equals("localhost")){
            url = StringConstants.Local_url + "/user/activate?token=" + token;
        }else {
            url = StringConstants.Server_url + "/user/activate?token=" + token;;
        }

        msg = "to activate your account <a href='" + url + "' style='border-color: #367fa9; border-radius: 3px;'>click here</a>";

        return msg;
    }

}

