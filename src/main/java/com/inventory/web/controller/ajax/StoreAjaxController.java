package com.inventory.web.controller.ajax;

import com.inventory.core.api.iapi.IStoreInfoApi;
import com.inventory.core.model.dto.RestResponseDTO;
import com.inventory.core.model.dto.StoreInfoDTO;
import com.inventory.core.model.enumconstant.ResponseStatus;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @PostMapping(value = "save" , produces = {MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<RestResponseDTO> save(@RequestAttribute("store")StoreInfoDTO storeInfoDTO, BindingResult bindingResult){
        RestResponseDTO result = new RestResponseDTO();

        try {

            if (AuthenticationUtil.getCurrentUser() != null) {

                String authority = AuthenticationUtil.getCurrentUser().getAuthority();

                if (authority.contains(Authorities.SUPERADMIN) && authority.contains(Authorities.AUTHENTICATED)) {

                    StoreInfoError error = new StoreInfoError();

                    error = storeInfoValidation.onSave(storeInfoDTO , bindingResult);

                    if (error.isValid()){
                        storeInfoDTO = storeInfoApi.save(storeInfoDTO);
                        result.setStatus(ResponseStatus.SUCCESS.getValue());
                        result.setMessage("user successfully saved");
                        result.setDetail(storeInfoDTO);
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
