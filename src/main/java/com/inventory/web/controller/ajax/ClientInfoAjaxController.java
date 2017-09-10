package com.inventory.web.controller.ajax;

import com.inventory.core.api.iapi.IClientInfoApi;
import com.inventory.core.api.iapi.IUserApi;
import com.inventory.core.model.dto.InvUserDTO;
import com.inventory.core.model.dto.RestResponseDTO;
import com.inventory.core.model.enumconstant.ClientType;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by dhiraj on 9/10/17.
 */
@Controller
@RequestMapping("client")
@ResponseBody
public class ClientInfoAjaxController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IUserApi userApi;

    @Autowired
    private IClientInfoApi clientInfoApi;

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
            result.setDetail(clientInfoApi.search(Status.ACTIVE , ClientType.CUSTOMER , term , 0 , 50));


        } catch (Exception e) {
            e.getStackTrace();
            logger.error("Stack trace: " + e.getStackTrace());
            result.setStatus(ResponseStatus.FAILURE.getValue());
            result.setMessage("internal server error");
        }

        return new ResponseEntity<RestResponseDTO>(result, HttpStatus.OK);
    }

}
