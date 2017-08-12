package com.inventory.web.controller.JS;

import com.inventory.core.api.iapi.IUserApi;
import com.inventory.core.model.dto.InvUserDTO;
import com.inventory.core.model.dto.RestResponseDTO;
import com.inventory.core.model.enumconstant.ResponseStatus;
import com.inventory.core.util.Authorities;
import com.inventory.core.validation.UserValidation;
import com.inventory.web.error.UserError;
import com.inventory.web.util.AuthenticationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by dhiraj on 8/12/17.
 */
@Controller
@RequestMapping("user")
@ResponseBody
public class UserJsController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IUserApi userApi;

    @Autowired
    private UserValidation userValidation;

    @PostMapping(value = "save" , produces = {MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<RestResponseDTO> save(@RequestAttribute("user")InvUserDTO userDTO , BindingResult bindingResult){
        RestResponseDTO result = new RestResponseDTO();

        try {

            if (AuthenticationUtil.getCurrentUser() != null) {

                String authority = AuthenticationUtil.getCurrentUser().getAuthority();

                if (authority.contains(Authorities.SYSTEM) && authority.contains(Authorities.AUTHENTICATED)) {

                    UserError error = new UserError();

                    error = userValidation.saveValidation(userDTO , bindingResult);

                    if (error.isValid()){
                        userDTO = userApi.save(userDTO);
                        result.setStatus(ResponseStatus.SUCCESS.getValue());
                        result.setMessage("user successfully saved");
                        result.setDetail(userDTO);
                    }else {
                        result.setStatus(ResponseStatus.VALIDATION_FAILED.getValue());
                        result.setMessage("user validation failed");
                        result.setDetail(error);
                    }

                }else {
                    result.setStatus(ResponseStatus.FAILURE.getValue());
                    result.setMessage("unauthorized user");
                }
            }else {
                result.setStatus(ResponseStatus.FAILURE.getValue());
                result.setMessage("user authentication failed");
            }

        }catch (Exception e){
            logger.error("Stack trace: " + e.getStackTrace());
            result.setStatus(ResponseStatus.FAILURE.getValue());
            result.setMessage("internal server error");
        }

        return new ResponseEntity<RestResponseDTO>(result, HttpStatus.OK);
    }
}
