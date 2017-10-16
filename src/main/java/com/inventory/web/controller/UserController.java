package com.inventory.web.controller;

import com.inventory.core.api.iapi.IStoreInfoApi;
import com.inventory.core.api.iapi.IStoreUserInfoApi;
import com.inventory.core.api.iapi.IUserApi;
import com.inventory.core.api.iapi.IUserPermissionApi;
import com.inventory.core.model.dto.InvUserDTO;
import com.inventory.core.model.dto.UserPermissionDTO;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.model.enumconstant.UserType;
import com.inventory.core.util.Authorities;
import com.inventory.core.validation.UserValidation;
import com.inventory.web.error.UserManageError;
import com.inventory.web.util.AuthenticationUtil;
import com.inventory.web.util.StringConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("user")
public class UserController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IUserApi userApi;

    @Autowired
    private UserValidation userValidation;

    @Autowired
    private IUserPermissionApi userPermissionApi;

    @Autowired
    private IStoreInfoApi storeInfoApi;

    @Autowired
    private IStoreUserInfoApi storeUserInfoApi;

	/*@Autowired
    private SessionInfo sessionInfo;*/

    @Autowired
    private PasswordEncoder passwordEncoder;

    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping(value = "/list")
    public String list(ModelMap modelMap, HttpSession session, RedirectAttributes redirectAttributes) {
        try {

            InvUserDTO currentUser = AuthenticationUtil.getCurrentUser(userApi);

            if (currentUser == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Athentication failed");
                return "redirect:/logout";
            }

            if (currentUser.getUserauthority().contains(Authorities.SYSTEM) && currentUser.getUserauthority().contains(Authorities.AUTHENTICATED)) {

                List<UserType> userTypeList = new ArrayList<>();

                userTypeList.add(UserType.SUPERADMIN);

                modelMap.put(StringConstants.USERTYPE_LIST, userTypeList);

                modelMap.put(StringConstants.USER_LIST, userApi.getAllByStatusAndUserTypeIn(Status.ACTIVE, userTypeList));

                return "user/listUser";

            } else if (currentUser.getUserauthority().contains(Authorities.SUPERADMIN) && currentUser.getUserauthority().contains(Authorities.AUTHENTICATED)) {

                List<UserType> userTypeList = new ArrayList<>();

                userTypeList.add(UserType.ADMIN);
                userTypeList.add(UserType.USER);

                modelMap.put(StringConstants.USERTYPE_LIST, userTypeList);

                modelMap.put(StringConstants.USER_LIST, userApi.getAllByStatusAndUserTypeInAndSuperAdmin(Status.ACTIVE, userTypeList , currentUser.getUserId()));

                modelMap.put(StringConstants.STORE_LIST, storeUserInfoApi.getAllStoreByUser(currentUser.getUserId()));

                return "user/listUser";

            } else if (currentUser.getUserauthority().contains(Authorities.ADMINISTRATOR) && currentUser.getUserauthority().contains(Authorities.AUTHENTICATED)) {

                List<UserType> userTypeList = new ArrayList<>();

                userTypeList.add(UserType.USER);

                modelMap.put(StringConstants.USERTYPE_LIST, userTypeList);

                modelMap.put(StringConstants.USER_LIST, userApi.getAllByStatusAndUserTypeInAndStoreInfo(Status.ACTIVE, userTypeList, currentUser.getStoreId()));

                return "user/listUser";
            } else {

                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Access deniled");
                return "redirect:/";
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Stack trace: " + Arrays.toString(e.getStackTrace()));
            return "redirect:/";
        }
    }


    @GetMapping(value = "/manage")
    public String manage(@RequestParam("userId") long userId, ModelMap modelMap, RedirectAttributes redirectAttributes) {
        try {

            InvUserDTO currentUser = AuthenticationUtil.getCurrentUser(userApi);

            if (currentUser == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Athentication failed");
                return "redirect:/logout";
            }

            if ((currentUser.getUserauthority().contains(Authorities.ADMINISTRATOR) || currentUser.getUserauthority().contains(Authorities.SUPERADMIN)) && currentUser.getUserauthority().contains(Authorities.AUTHENTICATED)) {


                UserManageError error = userValidation.onManage(userId);

                if (!error.isValid()) {
                    redirectAttributes.addFlashAttribute(StringConstants.ERROR, error.getError());
                    return "redirect:/user/list";
                }

                InvUserDTO userDTO = userApi.getUserWithId(userId);

                modelMap.put(StringConstants.USER, userDTO);
                UserPermissionDTO userPermissionDTO = userPermissionApi.getByUserId(userId);

                if (userPermissionDTO != null) {
                    if (userPermissionDTO.getPermissionList() != null)
                        modelMap.put(StringConstants.USER_PERMISSION, userPermissionDTO.getPermissionList());
                }

                if (userDTO.getStoreId() != null) {
                    modelMap.put(StringConstants.STORE, storeInfoApi.show(userDTO.getStoreId(), Status.ACTIVE));
                }

                return "user/manageUser";

            } else {

                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Access deniled");
                return "redirect:/";
            }

        } catch (Exception e) {
            logger.error("Stack trace: " + e.getStackTrace());
            return "redirect:/";
        }
    }

    @PostMapping(value = "/manage")
    public String manage(@RequestAttribute("userpermission") UserPermissionDTO userPermissionDTO, ModelMap modelMap, RedirectAttributes redirectAttributes) {
        try {

            InvUserDTO currentUser = AuthenticationUtil.getCurrentUser(userApi);

            if (currentUser == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Athentication failed");
                return "redirect:/logout";
            }

            if ((currentUser.getUserauthority().contains(Authorities.ADMINISTRATOR) || currentUser.getUserauthority().contains(Authorities.SUPERADMIN)) && currentUser.getUserauthority().contains(Authorities.AUTHENTICATED)) {

                synchronized (this.getClass()) {
                    UserManageError error = userValidation.onManage(userPermissionDTO.getUserId());

                    if (!error.isValid()) {
                        redirectAttributes.addFlashAttribute(StringConstants.ERROR, error.getError());
                        return "redirect:/user/list";
                    }

                    UserPermissionDTO userPermissionDTO1 = userPermissionApi.getByUserId(userPermissionDTO.getUserId());

                    if (userPermissionDTO1 == null) {
                        userPermissionApi.save(userPermissionDTO);
                    } else {
                        userPermissionDTO.setUserPermissionId(userPermissionDTO1.getUserPermissionId());
                        userPermissionApi.update(userPermissionDTO);
                    }

                    redirectAttributes.addFlashAttribute(StringConstants.MESSAGE, "user managed successfully");

                    return "redirect:/user/manage?userId=" + userPermissionDTO.getUserId();
                }

            } else {

                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Access deniled");
                return "redirect:/";
            }

        } catch (Exception e) {
            logger.error("Stack trace: " + e.getStackTrace());
            return "redirect:/";
        }
    }

    @GetMapping(value = "/updateenable")
    public String updateEnable(@RequestParam("userId") long userId, ModelMap modelMap, RedirectAttributes redirectAttributes) {
        try {

            InvUserDTO currentUser = AuthenticationUtil.getCurrentUser(userApi);

            if (currentUser == null) {
                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Athentication failed");
                return "redirect:/logout";
            }
            if ((currentUser.getUserauthority().contains(Authorities.ADMINISTRATOR) || currentUser.getUserauthority().contains(Authorities.SUPERADMIN)) && currentUser.getUserauthority().contains(Authorities.AUTHENTICATED)) {

                synchronized (this.getClass()) {
                    UserManageError error = userValidation.onUpadteEnable(userId);

                    if (!error.isValid()) {
                        redirectAttributes.addFlashAttribute(StringConstants.ERROR, error.getError());
                        return "redirect:/user/list";
                    }

                    InvUserDTO userDTO = userApi.updateEnable(userId);
                }

				/*if (!userDTO.getEnable()){
					sessionInfo.listSale(userDTO.getInventoryuser());
				}*/

                redirectAttributes.addFlashAttribute(StringConstants.MESSAGE, "user updated successfully");
                return "redirect:/user/list";

            } else {

                redirectAttributes.addFlashAttribute(StringConstants.ERROR, "Access deniled");
                return "redirect:/";
            }

        } catch (Exception e) {
            logger.error("Stack trace: " + e.getStackTrace());
            return "redirect:/";
        }
    }
}
