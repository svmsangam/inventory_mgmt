package com.inventory.web.controller.ajax;

import com.inventory.core.api.iapi.IClientInfoApi;
import com.inventory.core.api.iapi.IUserApi;
import com.inventory.core.model.dto.ClientInfoDTO;
import com.inventory.core.model.dto.InvUserDTO;
import com.inventory.core.model.dto.RestResponseDTO;
import com.inventory.core.model.enumconstant.ClientType;
import com.inventory.core.model.enumconstant.Permission;
import com.inventory.core.model.enumconstant.ResponseStatus;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.util.Authorities;
import com.inventory.core.validation.ClientInfoValidation;
import com.inventory.web.error.ClientInfoError;
import com.inventory.web.util.AuthenticationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * Created by dhiraj on 9/10/17.
 */
@Controller
@RequestMapping("client")
@ResponseBody
public class ClientInfoAjaxController {



    @Autowired
    private IUserApi userApi;

    @Autowired
    private IClientInfoApi clientInfoApi;

    @Autowired
    private ClientInfoValidation clientInfoValidation;

    @GetMapping(value = "customer/search", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RestResponseDTO> searchCustomer(@RequestParam("term") String term, HttpServletRequest request) {
        RestResponseDTO result = new RestResponseDTO();

        try {

            InvUserDTO currentUser = AuthenticationUtil.getCurrentUser(userApi);

            if (currentUser == null) {
                request.getSession().invalidate();
                result.setStatus(ResponseStatus.FAILURE.getValue());
                result.setMessage("user authentication failed");
                return new ResponseEntity<RestResponseDTO>(result, HttpStatus.OK);
            }

            if (!((currentUser.getUserauthority().contains(Authorities.SUPERADMIN) | currentUser.getUserauthority().contains(Authorities.ADMINISTRATOR) | currentUser.getUserauthority().contains(Authorities.USER)) && currentUser.getUserauthority().contains(Authorities.AUTHENTICATED))) {

                request.getSession().invalidate();
                result.setStatus(ResponseStatus.FAILURE.getValue());
                result.setMessage("user authentication failed");
                return new ResponseEntity<RestResponseDTO>(result, HttpStatus.OK);
            }


            result.setStatus(ResponseStatus.SUCCESS.getValue());
            result.setMessage("store successfully saved");
            result.setDetail(clientInfoApi.search(Status.ACTIVE , ClientType.CUSTOMER , term , 0 , 50 , currentUser.getStoreId()));


        } catch (Exception e) {
            LoggerUtil.logException(this.getClass() , e);("Stack trace: " + e.getStackTrace());
            result.setStatus(ResponseStatus.FAILURE.getValue());
            result.setMessage("internal server error");
        }

        return new ResponseEntity<RestResponseDTO>(result, HttpStatus.OK);
    }

    @GetMapping(value = "vendor/search", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RestResponseDTO> searchVendor(@RequestParam("term") String term, HttpServletRequest request) {
        RestResponseDTO result = new RestResponseDTO();

        try {

            InvUserDTO currentUser = AuthenticationUtil.getCurrentUser(userApi);

            if (currentUser == null) {
                request.getSession().invalidate();
                result.setStatus(ResponseStatus.FAILURE.getValue());
                result.setMessage("user authentication failed");
                return new ResponseEntity<RestResponseDTO>(result, HttpStatus.OK);
            }

            if (!((currentUser.getUserauthority().contains(Authorities.SUPERADMIN) | currentUser.getUserauthority().contains(Authorities.ADMINISTRATOR) | currentUser.getUserauthority().contains(Authorities.USER)) && currentUser.getUserauthority().contains(Authorities.AUTHENTICATED))) {

                request.getSession().invalidate();
                result.setStatus(ResponseStatus.FAILURE.getValue());
                result.setMessage("user authentication failed");
                return new ResponseEntity<RestResponseDTO>(result, HttpStatus.OK);
            }

            result.setStatus(ResponseStatus.SUCCESS.getValue());
            result.setMessage("store successfully saved");
            result.setDetail(clientInfoApi.search(Status.ACTIVE , ClientType.VENDOR , term , 0 , 50 , currentUser.getStoreId()));


        } catch (Exception e) {
            LoggerUtil.logException(this.getClass() , e);("Stack trace: " + e.getStackTrace());
            result.setStatus(ResponseStatus.FAILURE.getValue());
            result.setMessage("internal server error");
        }

        return new ResponseEntity<RestResponseDTO>(result, HttpStatus.OK);
    }


    @GetMapping(value = "search", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RestResponseDTO> search(@RequestParam("term") String term, HttpServletRequest request) {
        RestResponseDTO result = new RestResponseDTO();

        try {

            InvUserDTO currentUser = AuthenticationUtil.getCurrentUser(userApi);

            if (currentUser == null) {
                request.getSession().invalidate();
                result.setStatus(ResponseStatus.FAILURE.getValue());
                result.setMessage("user authentication failed");
                return new ResponseEntity<RestResponseDTO>(result, HttpStatus.OK);
            }

            if (!((currentUser.getUserauthority().contains(Authorities.SUPERADMIN) | currentUser.getUserauthority().contains(Authorities.ADMINISTRATOR) | currentUser.getUserauthority().contains(Authorities.USER)) && currentUser.getUserauthority().contains(Authorities.AUTHENTICATED))) {

                request.getSession().invalidate();
                result.setStatus(ResponseStatus.FAILURE.getValue());
                result.setMessage("user authentication failed");
                return new ResponseEntity<RestResponseDTO>(result, HttpStatus.OK);
            }


            result.setStatus(ResponseStatus.SUCCESS.getValue());
            result.setMessage("store successfully saved");
            result.setDetail(clientInfoApi.search(Status.ACTIVE , term , 0 , 50 , currentUser.getStoreId()));


        } catch (Exception e) {
            LoggerUtil.logException(this.getClass() , e);("Stack trace: " + e.getStackTrace());
            result.setStatus(ResponseStatus.FAILURE.getValue());
            result.setMessage("internal server error");
        }

        return new ResponseEntity<RestResponseDTO>(result, HttpStatus.OK);
    }

    @PostMapping(value = "customer/save", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RestResponseDTO> saveCustomer(@RequestAttribute("client") ClientInfoDTO clientInfoDTO, HttpServletRequest request) {
        RestResponseDTO result = new RestResponseDTO();

        try {
       /*current user checking start*/
            InvUserDTO currentUser = AuthenticationUtil.getCurrentUser(userApi);

            if (currentUser == null) {
                result.setStatus(ResponseStatus.FAILURE.getValue());
                result.setMessage("user authentication failed");
                return new ResponseEntity<RestResponseDTO>(result, HttpStatus.OK);
            }

            if (!((currentUser.getUserauthority().contains(Authorities.SUPERADMIN) | currentUser.getUserauthority().contains(Authorities.ADMINISTRATOR) | currentUser.getUserauthority().contains(Authorities.USER)) && currentUser.getUserauthority().contains(Authorities.AUTHENTICATED))) {
                result.setStatus(ResponseStatus.FAILURE.getValue());
                result.setMessage("user authentication failed");
                return new ResponseEntity<RestResponseDTO>(result, HttpStatus.OK);
            }

            if (currentUser.getUserauthority().contains(Authorities.USER) & !AuthenticationUtil.checkPermission(currentUser, Permission.CLIENT_CREATE)) {
                result.setStatus(ResponseStatus.FAILURE.getValue());
                result.setMessage("access deniled");
                return new ResponseEntity<RestResponseDTO>(result, HttpStatus.OK);
            }

            if (currentUser.getStoreId() == null) {
                result.setStatus(ResponseStatus.FAILURE.getValue());
                result.setMessage("store not assigned");
                return new ResponseEntity<RestResponseDTO>(result, HttpStatus.OK);
            }

        /*current user checking end*/

            synchronized (this.getClass()) {

                clientInfoDTO.setClientType(ClientType.CUSTOMER);
                clientInfoDTO.setCreatedById(currentUser.getUserId());
                clientInfoDTO.setStoreInfoId(currentUser.getStoreId());

                ClientInfoError error = clientInfoValidation.onSave(clientInfoDTO);

                if (!error.isValid()) {
                    result.setStatus(ResponseStatus.VALIDATION_FAILED.getValue());
                    result.setMessage("customer validation failur");
                    result.setDetail(error);
                    return new ResponseEntity<RestResponseDTO>(result, HttpStatus.OK);
                }

                ClientInfoDTO saved = clientInfoApi.save(clientInfoDTO);

                result.setStatus(ResponseStatus.SUCCESS.getValue());
                result.setMessage("customer saved successfully");
                result.setDetail(saved);
            }

        } catch (Exception e) {
            LoggerUtil.logException(this.getClass() , e);("Exception on client controller : " + Arrays.toString(e.getStackTrace()));
            result.setStatus(ResponseStatus.FAILURE.getValue());
            result.setMessage("internal server error");
        }
        return new ResponseEntity<RestResponseDTO>(result, HttpStatus.OK);
    }

    @PostMapping(value = "vendor/save", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RestResponseDTO> saveVendor(@RequestAttribute("vendor") ClientInfoDTO clientInfoDTO, HttpServletRequest request) {
        RestResponseDTO result = new RestResponseDTO();

        try {
            /*current user checking start*/
            InvUserDTO currentUser = AuthenticationUtil.getCurrentUser(userApi);

            if (currentUser == null) {
                result.setStatus(ResponseStatus.FAILURE.getValue());
                result.setMessage("user authentication failed");
                return new ResponseEntity<RestResponseDTO>(result, HttpStatus.OK);
            }

            if (!((currentUser.getUserauthority().contains(Authorities.SUPERADMIN) | currentUser.getUserauthority().contains(Authorities.ADMINISTRATOR) | currentUser.getUserauthority().contains(Authorities.USER)) && currentUser.getUserauthority().contains(Authorities.AUTHENTICATED))) {
                result.setStatus(ResponseStatus.FAILURE.getValue());
                result.setMessage("user authentication failed");
                return new ResponseEntity<RestResponseDTO>(result, HttpStatus.OK);
            }

            if (currentUser.getUserauthority().contains(Authorities.USER) & !AuthenticationUtil.checkPermission(currentUser, Permission.CLIENT_CREATE)) {
                result.setStatus(ResponseStatus.FAILURE.getValue());
                result.setMessage("access deniled");
                return new ResponseEntity<RestResponseDTO>(result, HttpStatus.OK);
            }

            if (currentUser.getStoreId() == null) {
                result.setStatus(ResponseStatus.FAILURE.getValue());
                result.setMessage("store not assigned");
                return new ResponseEntity<RestResponseDTO>(result, HttpStatus.OK);
            }

            /*current user checking end*/

            synchronized (this.getClass()) {

                clientInfoDTO.setClientType(ClientType.VENDOR);
                clientInfoDTO.setCreatedById(currentUser.getUserId());
                clientInfoDTO.setStoreInfoId(currentUser.getStoreId());

                ClientInfoError error = clientInfoValidation.onSave(clientInfoDTO);

                if (!error.isValid()) {
                    result.setStatus(ResponseStatus.VALIDATION_FAILED.getValue());
                    result.setMessage("supplier validation failur");
                    result.setDetail(error);
                    return new ResponseEntity<RestResponseDTO>(result, HttpStatus.OK);
                }

                ClientInfoDTO saved = clientInfoApi.save(clientInfoDTO);

                result.setStatus(ResponseStatus.SUCCESS.getValue());
                result.setMessage("supplier saved successfully");
                result.setDetail(saved);
            }

        } catch (Exception e) {
            LoggerUtil.logException(this.getClass() , e);("Exception on client controller : " + Arrays.toString(e.getStackTrace()));
            result.setStatus(ResponseStatus.FAILURE.getValue());
            result.setMessage("internal server error");
        }
        return new ResponseEntity<RestResponseDTO>(result, HttpStatus.OK);
    }


}
