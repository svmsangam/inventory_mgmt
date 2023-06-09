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

/**
 * Created by dhiraj on 9/13/17.
 */
@Controller
@RequestMapping("order")
public class OrderInfoAjaxController {

    @Autowired
    private IOrderInfoApi orderInfoApi;

    @Autowired
    private IUserApi userApi;

    @GetMapping(value = "sale/track/update", produces = {MediaType.APPLICATION_JSON_VALUE})
	@PreAuthorize("hasAnyRole('ROLE_SUPERADMINISTRATOR','ROLE_ADMINISTRATOR','ROLE_USER,ROLE_AUTHENTICATED')")
    public ResponseEntity<RestResponseDTO> searchCustomer(@RequestParam("orderId") Long orderId, @RequestParam("track")SalesOrderStatus track, HttpServletRequest request) {
        RestResponseDTO result = new RestResponseDTO();

        try {

            InvUserDTO currentUser = AuthenticationUtil.getCurrentUser(userApi);

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

            synchronized (this.getClass()) {
                OrderInfoDTO orderInfoDTO = orderInfoApi.show(Status.ACTIVE, orderId, currentUser.getStoreId());

                if (orderInfoDTO == null) {
                    result.setStatus(ResponseStatus.FAILURE.getValue());
                    result.setMessage("order validation failed");
                    return new ResponseEntity<RestResponseDTO>(result, HttpStatus.OK);
                }

                if (orderInfoDTO.getSaleTrack().equals(SalesOrderStatus.CANCEL)) {
                    result.setStatus(ResponseStatus.FAILURE.getValue());
                    result.setMessage("order validation failed");
                    return new ResponseEntity<RestResponseDTO>(result, HttpStatus.OK);
                }


                result.setStatus(ResponseStatus.SUCCESS.getValue());
                result.setMessage("store successfully saved");
                result.setDetail(orderInfoApi.updateSaleTrack(orderId, track, currentUser.getUserId()));

            }
        } catch (Exception e) {
            LoggerUtil.logException(this.getClass() , e);
            result.setStatus(ResponseStatus.FAILURE.getValue());
            result.setMessage("internal server error");
        }

        return new ResponseEntity<RestResponseDTO>(result, HttpStatus.OK);
    }
}
