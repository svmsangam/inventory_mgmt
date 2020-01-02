package com.inventory.web.controller.ajax;

import com.inventory.core.api.iapi.INotificationApi;
import com.inventory.core.api.iapi.IUserApi;
import com.inventory.core.model.dto.InvUserDTO;
import com.inventory.core.model.dto.RestResponseDTO;
import com.inventory.core.model.enumconstant.ResponseStatus;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.util.Authorities;
import com.inventory.web.util.AuthenticationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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



    @Autowired
    private IUserApi userApi;

    @Autowired
    private INotificationApi notificationApi;

    @PostMapping(value = "/updateToken", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RestResponseDTO> updateToken(@RequestParam("token") String token, HttpServletRequest request) {
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
            LoggerUtil.logException(this.getClass() , e);("Stack trace: " + e.getStackTrace());
            result.setStatus(ResponseStatus.FAILURE.getValue());
            result.setMessage("internal server error");
        }

        return new ResponseEntity<RestResponseDTO>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/count", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RestResponseDTO> count(HttpServletRequest request) {
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

            result.setStatus(ResponseStatus.SUCCESS.getValue());
            result.setDetail(notificationApi.findAllByStatusAndTo_Id(Status.ACTIVE , currentUser.getUserId() , 0 , 10));
            result.setMessage("" + notificationApi.countAllByStatusAndTo_IdAndSeenAndSent(Status.ACTIVE , currentUser.getUserId() , false , true));

        } catch (Exception e) {
            LoggerUtil.logException(this.getClass() , e);("Stack trace: " + e.getStackTrace());
            result.setStatus(ResponseStatus.FAILURE.getValue());
            result.setMessage("internal server error");
        }

        return new ResponseEntity<RestResponseDTO>(result, HttpStatus.OK);
    }
}
