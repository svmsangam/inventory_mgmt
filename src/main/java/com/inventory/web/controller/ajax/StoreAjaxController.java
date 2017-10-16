package com.inventory.web.controller.ajax;

import com.inventory.core.api.iapi.IStoreInfoApi;
import com.inventory.core.api.iapi.IUserApi;
import com.inventory.core.model.dto.InvUserDTO;
import com.inventory.core.model.dto.RestResponseDTO;
import com.inventory.core.model.dto.StoreInfoDTO;
import com.inventory.core.model.enumconstant.ResponseStatus;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.util.Authorities;
import com.inventory.core.validation.StoreInfoValidation;
import com.inventory.web.error.StoreInfoError;
import com.inventory.web.util.AuthenticationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by dhiraj on 8/14/17.
 */
@Controller
@RequestMapping("store")
@ResponseBody
public class StoreAjaxController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IStoreInfoApi storeInfoApi;

    @Autowired
    private StoreInfoValidation storeInfoValidation;

    @Autowired
    private IUserApi userApi;

    @PostMapping(value = "save", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RestResponseDTO> save(@RequestAttribute("store") StoreInfoDTO storeInfoDTO, BindingResult bindingResult, HttpServletRequest request) {
        RestResponseDTO result = new RestResponseDTO();

        try {

            InvUserDTO currentUser = AuthenticationUtil.getCurrentUser(userApi);

            if (currentUser == null) {
                request.getSession().invalidate();
                result.setStatus(ResponseStatus.FAILURE.getValue());
                result.setMessage("user authentication failed");
                return new ResponseEntity<RestResponseDTO>(result, HttpStatus.OK);
            }

            if (currentUser.getUserauthority().contains(Authorities.SUPERADMIN) && currentUser.getUserauthority().contains(Authorities.AUTHENTICATED)) {

                synchronized (this.getClass()) {
                    StoreInfoError error = new StoreInfoError();

                    error = storeInfoValidation.onSave(storeInfoDTO, bindingResult);

                    if (error.isValid()) {

                        storeInfoDTO = storeInfoApi.save(storeInfoDTO, currentUser.getUserId());

                        if (currentUser.getStoreId() == null) {
                            userApi.changeStore(currentUser.getUserId(), storeInfoDTO.getStoreId());
                        }

                        result.setStatus(ResponseStatus.SUCCESS.getValue());
                        result.setMessage("store successfully saved");
                        result.setDetail(storeInfoDTO);

                    } else {
                        result.setStatus(ResponseStatus.VALIDATION_FAILED.getValue());
                        result.setMessage("store validation failed");
                        result.setDetail(error);
                    }
                }

            } else {
                result.setStatus(ResponseStatus.FAILURE.getValue());
                result.setMessage("unauthorized user");
            }

        } catch (Exception e) {
            logger.error("Stack trace: " + e.getStackTrace());
            result.setStatus(ResponseStatus.FAILURE.getValue());
            result.setMessage("internal server error");
        }

        return new ResponseEntity<RestResponseDTO>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/show/{storeId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RestResponseDTO> save(@PathVariable("storeId") Long storeId, HttpServletRequest request) {
        RestResponseDTO result = new RestResponseDTO();

        try {

            InvUserDTO currentUser = AuthenticationUtil.getCurrentUser(userApi);

            if (currentUser == null) {
                request.getSession().invalidate();
                result.setStatus(ResponseStatus.FAILURE.getValue());
                result.setMessage("user authentication failed");
                return new ResponseEntity<RestResponseDTO>(result, HttpStatus.OK);
            }

            if (storeId == null) {
                result.setStatus(ResponseStatus.FAILURE.getValue());
                result.setMessage("store not found");
                return new ResponseEntity<RestResponseDTO>(result, HttpStatus.OK);
            }

            if (storeId < 0) {
                result.setStatus(ResponseStatus.FAILURE.getValue());
                result.setMessage("store not found");
                return new ResponseEntity<RestResponseDTO>(result, HttpStatus.OK);
            }

            if ((currentUser.getUserauthority().contains(Authorities.SUPERADMIN) || currentUser.getUserauthority().contains(Authorities.ADMINISTRATOR)) && currentUser.getUserauthority().contains(Authorities.AUTHENTICATED)) {

                StoreInfoDTO storeInfoDTO = storeInfoApi.show(storeId, Status.ACTIVE);

                if (storeInfoDTO == null) {
                    result.setStatus(ResponseStatus.FAILURE.getValue());
                    result.setMessage("store not found");
                    return new ResponseEntity<RestResponseDTO>(result, HttpStatus.OK);
                }

                result.setStatus(ResponseStatus.SUCCESS.getValue());
                result.setMessage("store successfully saved");
                result.setDetail(storeInfoDTO);

            }

        } catch (Exception e) {
            e.getStackTrace();
            logger.error("Stack trace: " + e.getStackTrace());
            result.setStatus(ResponseStatus.FAILURE.getValue());
            result.setMessage("internal server error");
        }

        return new ResponseEntity<RestResponseDTO>(result, HttpStatus.OK);
    }

    @PostMapping(value = "update", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RestResponseDTO> update(@RequestAttribute("store") StoreInfoDTO storeInfoDTO, BindingResult bindingResult, HttpServletRequest request) {
        RestResponseDTO result = new RestResponseDTO();

        try {

            InvUserDTO currentUser = AuthenticationUtil.getCurrentUser(userApi);

            if (currentUser == null) {
                request.getSession().invalidate();
                result.setStatus(ResponseStatus.FAILURE.getValue());
                result.setMessage("user authentication failed");
                return new ResponseEntity<RestResponseDTO>(result, HttpStatus.OK);
            }

            if (currentUser.getUserauthority().contains(Authorities.SUPERADMIN) && currentUser.getUserauthority().contains(Authorities.AUTHENTICATED)) {

                StoreInfoError error = new StoreInfoError();

                error = storeInfoValidation.onUpdate(storeInfoDTO, bindingResult);

                if (error.isValid()) {

                    storeInfoDTO = storeInfoApi.update(storeInfoDTO);

                    if (currentUser.getStoreId() == null) {
                        userApi.changeStore(currentUser.getUserId(), storeInfoDTO.getStoreId());
                    }

                    result.setStatus(ResponseStatus.SUCCESS.getValue());
                    result.setMessage("store successfully updated");
                    result.setDetail(storeInfoDTO);

                } else {
                    result.setStatus(ResponseStatus.VALIDATION_FAILED.getValue());
                    result.setMessage("store validation failed");
                    result.setDetail(error);
                }

            } else {
                result.setStatus(ResponseStatus.FAILURE.getValue());
                result.setMessage("unauthorized user");
            }

        } catch (Exception e) {
            e.getStackTrace();
            logger.error("Stack trace: " + e.getStackTrace());
            result.setStatus(ResponseStatus.FAILURE.getValue());
            result.setMessage("internal server error");
        }

        return new ResponseEntity<RestResponseDTO>(result, HttpStatus.OK);
    }


}
