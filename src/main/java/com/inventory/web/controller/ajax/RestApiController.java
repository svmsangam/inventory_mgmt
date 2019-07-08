package com.inventory.web.controller.ajax;

import com.inventory.core.api.iapi.IStockInfoApi;
import com.inventory.core.api.iapi.IUnitInfoApi;
import com.inventory.core.api.iapi.IUserApi;
import com.inventory.core.model.dto.RestResponseDTO;
import com.inventory.core.model.enumconstant.ResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by dhiraj on 12/11/17.
 */
@Controller
@RequestMapping("api/customer")
@ResponseBody
public class RestApiController {

    @Autowired
    private IUnitInfoApi unitInfoApi;

    @Autowired
    private IStockInfoApi stockInfoApi;

    @Autowired
    private IUserApi userApi;


    @PostMapping(value = "/save" , produces = {MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<RestResponseDTO> saveCustomer(HttpServletRequest request){

        RestResponseDTO result = new RestResponseDTO();

        try {

            String name = request.getParameter("name");
            String contact = request.getParameter("contact");
            String email = request.getParameter("email");



            result.setStatus(ResponseStatus.SUCCESS.getValue());
            result.setDetail(name);
            result.setMessage("CUSTOMER created ");

        }catch (Exception e){
            e.printStackTrace();
            result.setStatus(ResponseStatus.FAILURE.getValue());
            result.setMessage("internal server error");
        }

        return new ResponseEntity<RestResponseDTO>(result, HttpStatus.OK);
    }
}
