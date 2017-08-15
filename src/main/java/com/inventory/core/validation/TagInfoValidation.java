package com.inventory.core.validation;

import com.inventory.core.model.dto.TagInfoDTO;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.model.repository.TagInfoRepository;
import com.inventory.web.error.TagInfoError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

/**
 * Created by dhiraj on 8/15/17.
 */
@Service
public class TagInfoValidation extends GlobalValidation{

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TagInfoRepository tagInfoRepository;

    TagInfoError error = new TagInfoError();

    boolean valid;

    public TagInfoError onSave(TagInfoDTO tagInfoDTO , BindingResult result){

        valid = true;

        if (result.hasErrors()) {

            valid = false;

            List<FieldError> errors = result.getFieldErrors();
            for (FieldError errorResult : errors) {

                if (errorResult.getField().equals("name")) {
                    error.setName("invalid store name");
                } else if (errorResult.getField().equals("code")) {
                    error.setCode("invalid code");
                }
            }

            error.setValid(valid);

            return error;
        }

        error.setName(checkString(tagInfoDTO.getName() , 2 , 10, "tagName" , true));

        if (!("".equals(error.getName()))){
            valid = false;
        }else if (tagInfoRepository.findByNameAndStatusAndStoreInfo(tagInfoDTO.getName().trim() , Status.ACTIVE , tagInfoDTO.getStoreInfoId()) != null){

            valid = false;

            error.setName("this name already in use");
        }

        error.setCode(checkString(tagInfoDTO.getCode() , 2 , 10, "tagCode" , true));

        if (!("".equals(error.getCode()))){
            valid = false;
        }else if (tagInfoRepository.findByCodeAndStatusAndStoreInfo(tagInfoDTO.getCode().trim() , Status.ACTIVE , tagInfoDTO.getStoreInfoId()) != null){

            valid = false;

            error.setName("this code already in use");
        }

        error.setValid(valid);

        return error;

    }
}
