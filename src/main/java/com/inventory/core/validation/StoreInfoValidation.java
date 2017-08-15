package com.inventory.core.validation;

import com.inventory.core.model.dto.StoreInfoDTO;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.model.repository.CityInfoRepository;
import com.inventory.core.model.repository.StoreInfoRepository;
import com.inventory.web.error.StoreInfoError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Arrays;
import java.util.List;

/**
 * Created by dhiraj on 8/14/17.
 */
@Service
public class StoreInfoValidation extends GlobalValidation {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private StoreInfoRepository storeInfoRepository;

    @Autowired
    private CityInfoRepository cityInfoRepository;

    StoreInfoError error = new StoreInfoError();

    boolean valid;

    public StoreInfoError onSave(StoreInfoDTO storeInfoDTO , BindingResult result){

        valid = true;


        if (result.hasErrors()) {

            valid = false;

            List<FieldError> errors = result.getFieldErrors();
            for (FieldError errorResult : errors ) {

                if (errorResult.getField().equals("name")){
                    error.setName("invalid store name");
                }else if (errorResult.getField().equals("cityId")){
                    error.setCityName("invalid city");
                } else {
                    error.setName("invalid data provided");
                }
            }

            error.setValid(valid);

            return error;
        }


        error.setName(checkString(storeInfoDTO.getName() , 5 , 50 , "storeName" , true));

        if (!("".equals(error.getName()))){
            valid = false;
        }else {
            checkStoreName(storeInfoDTO.getName().trim());
        }

        try {

            if (cityInfoRepository.findByIdAndStatus(storeInfoDTO.getCityId(), Status.ACTIVE) == null) {
                valid = false;
                error.setCityName("invalid city");
            }
        }catch (Exception e){
            logger.error(e.getClass() + " error on store validation for city id : "+ Arrays.toString(e.getStackTrace()));

            valid = false;
            error.setCityName("invalid city");
        }

        error.setContact(checkString(storeInfoDTO.getContact() , 7 , 10  , "contact" , true));

        if (!("".equals(error.getContact()))){
            valid = false;
        }

        error.setEmail(checkString(storeInfoDTO.getEmail() , 5 , 20 , "email" , true));

        if (!("".equals(error.getEmail()))){
            valid = false;
        }

        error.setMobileNumber(checkString(storeInfoDTO.getMobileNumber() , 10 , 10 , "mobile Number" , false));

        if (!("".equals(error.getMobileNumber()))){
            valid = false;
        }

        error.setPanNumber(checkString(storeInfoDTO.getPanNumber() , 10 , 10 , "pan number" , true));

        if (!("".equals(error.getPanNumber()))){
            valid = false;
        }

        error.setRegNumber(checkString(storeInfoDTO.getRegNumber() , 10 , 10 , "registration number" , true));

        if (!("".equals(error.getRegNumber()))){
            valid = false;
        }

        error.setValid(valid);

        return error;
    }

    private void checkStoreName(String name){

        if (storeInfoRepository.findByNameAndStatus(name , Status.ACTIVE) != null){
            valid = false;

            error.setName("store name not avialabale");
        }
    }
}
