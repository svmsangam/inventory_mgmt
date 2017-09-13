package com.inventory.web.controller.ajax;

import com.inventory.core.api.iapi.IOrderInfoApi;
import com.inventory.core.api.iapi.IUserApi;
import com.inventory.core.model.dto.InvUserDTO;
import com.inventory.core.model.dto.OrderInfoDTO;
import com.inventory.core.model.dto.RestResponseDTO;
import com.inventory.core.model.enumconstant.ResponseStatus;
import com.inventory.core.model.enumconstant.SalesOrderStatus;
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

import javax.servlet.http.HttpServletRequest;

/**
 * Created by dhiraj on 9/13/17.
 */
@Controller
@RequestMapping("order")
public class OrderInfoAjaxController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IOrderInfoApi orderInfoApi;

    @Autowired
    private IUserApi userApi;

    @GetMapping(value = "sale/track/update", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RestResponseDTO> searchCustomer(@RequestParam("orderId") Long orderId, @RequestParam("track")SalesOrderStatus track, HttpServletRequest request) {
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

            if (orderId == null){
                result.setStatus(ResponseStatus.FAILURE.getValue());
                result.setMessage("order validation failed");
                return new ResponseEntity<RestResponseDTO>(result, HttpStatus.OK);
            }

            if (track == null){
                result.setStatus(ResponseStatus.FAILURE.getValue());
                result.setMessage("order validation failed");
                return new ResponseEntity<RestResponseDTO>(result, HttpStatus.OK);
            }

            if (orderId < 1){
                result.setStatus(ResponseStatus.FAILURE.getValue());
                result.setMessage("order validation failed");
                return new ResponseEntity<RestResponseDTO>(result, HttpStatus.OK);
            }

            OrderInfoDTO orderInfoDTO = orderInfoApi.show(Status.ACTIVE , orderId , currentUser.getStoreId());

            if (orderInfoDTO == null){
                result.setStatus(ResponseStatus.FAILURE.getValue());
                result.setMessage("order validation failed");
                return new ResponseEntity<RestResponseDTO>(result, HttpStatus.OK);
            }

            if (orderInfoDTO.getSaleTrack().equals(SalesOrderStatus.CANCEL)){
                result.setStatus(ResponseStatus.FAILURE.getValue());
                result.setMessage("order validation failed");
                return new ResponseEntity<RestResponseDTO>(result, HttpStatus.OK);
            }


            result.setStatus(ResponseStatus.SUCCESS.getValue());
            result.setMessage("store successfully saved");
            result.setDetail(orderInfoApi.updateSaleTrack(orderId , track));


        } catch (Exception e) {
            logger.error("Stack trace: " + e.getStackTrace());
            result.setStatus(ResponseStatus.FAILURE.getValue());
            result.setMessage("internal server error");
        }

        return new ResponseEntity<RestResponseDTO>(result, HttpStatus.OK);
    }
}
