package com.inventory.web.controller.ajax;

import com.inventory.core.api.iapi.IUserApi;
import com.inventory.core.model.dto.InvUserDTO;
import com.inventory.core.model.dto.RestResponseDTO;
import com.inventory.core.model.enumconstant.ResponseStatus;
import com.inventory.core.util.Authorities;
import com.inventory.web.util.AuthenticationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by dhiraj on 1/19/18.
 */
@Controller
@RequestMapping("notification")
public class NotificationAjaxController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IUserApi userApi;

    @PostMapping(value = "/updateToken", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RestResponseDTO> searchCustomer(@RequestParam("token") String token, HttpServletRequest request) {
        RestResponseDTO result = new RestResponseDTO();

        try {

            InvUserDTO currentUser = AuthenticationUtil.getCurrentUser(userApi);

            if (currentUser == null) {
                request.getSession().invalidate();
                result.setStatus(ResponseStatus.FAILURE.getValue());
                result.setMessage("user authentication failed");
                return new ResponseEntity<RestResponseDTO>(result, HttpStatus.OK);
            }

            if (!((currentUser.getUserauthority().contains(Authorities.SUPERADMIN)) && currentUser.getUserauthority().contains(Authorities.AUTHENTICATED))) {

                request.getSession().invalidate();
                result.setStatus(ResponseStatus.FAILURE.getValue());
                result.setMessage("user authentication failed");
                return new ResponseEntity<RestResponseDTO>(result, HttpStatus.OK);
            }

            if (token == null){
                result.setStatus(ResponseStatus.FAILURE.getValue());
                result.setMessage("invalid token");
                return new ResponseEntity<RestResponseDTO>(result, HttpStatus.OK);
            }

            if (token.isEmpty()){
                result.setStatus(ResponseStatus.FAILURE.getValue());
                result.setMessage("invalid token");
                return new ResponseEntity<RestResponseDTO>(result, HttpStatus.OK);
            }

            userApi.updateFCMToken(token , currentUser.getUserId());

            result.setStatus(ResponseStatus.SUCCESS.getValue());
            result.setMessage("store successfully saved");



        } catch (Exception e) {
            logger.error("Stack trace: " + e.getStackTrace());
            result.setStatus(ResponseStatus.FAILURE.getValue());
            result.setMessage("internal server error");
        }

        return new ResponseEntity<RestResponseDTO>(result, HttpStatus.OK);
    }
}
