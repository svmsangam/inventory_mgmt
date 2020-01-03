package com.inventory.web.controller.ajax;

import com.inventory.core.api.iapi.IItemInfoApi;
import com.inventory.core.api.iapi.IUserApi;
import com.inventory.core.model.dto.InvUserDTO;
import com.inventory.core.model.dto.RestResponseDTO;
import com.inventory.core.model.enumconstant.ResponseStatus;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.util.Authorities;
import com.inventory.core.validation.ItemInfoValidation;
import com.inventory.web.error.ItemInfoError;
import com.inventory.web.util.AuthenticationUtil;
import com.inventory.web.util.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by dhiraj on 9/11/17.
 */
@Controller
@RequestMapping("item")
public class ItemAjaxController {

    @Autowired
    private IItemInfoApi itemInfoApi;

    @Autowired
    private IUserApi userApi;

    @Autowired
    private ItemInfoValidation itemInfoValidation;

    @GetMapping(value = "show", produces = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasAnyRole('ROLE_SUPERADMINISTRATOR' , 'ROLE_ADMINISTRATOR', 'ROLE_ITEM_VIEW')")
    public ResponseEntity<RestResponseDTO> searchCustomer(@RequestParam("itemId") Long itemId, HttpServletRequest request) {
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

            if (itemId == null){
                result.setStatus(ResponseStatus.FAILURE.getValue());
                result.setMessage("Item validation failed");
                return new ResponseEntity<RestResponseDTO>(result, HttpStatus.OK);
            }

            if (itemId < 1){
                result.setStatus(ResponseStatus.FAILURE.getValue());
                result.setMessage("Item validation failed");
                return new ResponseEntity<RestResponseDTO>(result, HttpStatus.OK);
            }

            result.setStatus(ResponseStatus.SUCCESS.getValue());
            result.setMessage("store successfully saved");
            result.setDetail(itemInfoApi.getByIdAndStoreAndStatus(itemId , currentUser.getStoreId() , Status.ACTIVE));


        } catch (Exception e) {
            LoggerUtil.logException(this.getClass() , e);
            result.setStatus(ResponseStatus.FAILURE.getValue());
            result.setMessage("internal server error");
        }

        return new ResponseEntity<RestResponseDTO>(result, HttpStatus.OK);
    }


    @GetMapping(value = "/search", produces = {MediaType.APPLICATION_JSON_VALUE})
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
            result.setDetail(itemInfoApi.search(Status.ACTIVE , term , 0 , 50 , currentUser.getStoreId()));


        } catch (Exception e) {
            LoggerUtil.logException(this.getClass() , e);
            result.setStatus(ResponseStatus.FAILURE.getValue());
            result.setMessage("internal server error");
        }

        return new ResponseEntity<RestResponseDTO>(result, HttpStatus.OK);
    }

    @GetMapping(value = "addUpQuantity", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RestResponseDTO> addUpQuantity(@RequestParam("itemIdList") List<Long> itemIdList , @RequestParam("quantity")Integer quantity, HttpServletRequest request) {
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

            synchronized (this.getClass()) {
                if (itemIdList == null) {
                    result.setStatus(ResponseStatus.FAILURE.getValue());
                    result.setMessage("Item validation failed");
                    return new ResponseEntity<RestResponseDTO>(result, HttpStatus.OK);
                }

                if (quantity < 1) {
                    result.setStatus(ResponseStatus.FAILURE.getValue());
                    result.setMessage("Quantity must be equalsTo or greater than 1");
                    return new ResponseEntity<RestResponseDTO>(result, HttpStatus.OK);
                }

                ItemInfoError error = itemInfoValidation.onAddUpQuantity(itemIdList, currentUser.getStoreId());

                if (!error.isValid()) {
                    result.setStatus(ResponseStatus.FAILURE.getValue());
                    result.setMessage(error.getProductId());
                    return new ResponseEntity<RestResponseDTO>(result, HttpStatus.OK);
                }

                itemInfoApi.addUpQuantity(itemIdList , quantity);
                result.setStatus(ResponseStatus.SUCCESS.getValue());
                result.setMessage("quantity updated successfully");
            }

        } catch (Exception e) {
            LoggerUtil.logException(this.getClass() , e);
            result.setStatus(ResponseStatus.FAILURE.getValue());
            result.setMessage("internal server error");
        }

        return new ResponseEntity<RestResponseDTO>(result, HttpStatus.OK);
    }

}
