package com.inventory.core.validation;

import com.inventory.core.model.dto.CategoryInfoDTO;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.model.repository.CategoryInfoRepository;
import com.inventory.web.error.CategoryInfoError;
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
public class CategoryInfoValidation extends GlobalValidation{

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CategoryInfoRepository categoryInfoRepository;

    CategoryInfoError error = new CategoryInfoError();

    boolean valid;

    public CategoryInfoError onSave(CategoryInfoDTO categoryInfoDTO ,  BindingResult result){

        valid = true;

        if (result.hasErrors()) {

            valid = false;

            List<FieldError> errors = result.getFieldErrors();
            for (FieldError errorResult : errors) {

                if (errorResult.getField().equals("name")) {
                    error.setName("invalid store name");
                } else if (errorResult.getField().equals("code")) {
                    error.setCode("invalid code");
                }else if (errorResult.getField().equals("description")){
                    error.setCode("invalid description");
                }
            }

            error.setValid(valid);

            return error;
        }

        error.setName(checkString(categoryInfoDTO.getName() , 2 , 10, "categoryName" , true));

        if (!("".equals(error.getName()))){
            valid = false;
        }else if (categoryInfoRepository.findByNameAndStatusAndStoreInfo(categoryInfoDTO.getName().trim() , Status.ACTIVE , categoryInfoDTO.getStoreInfoId()) != null){

            valid = false;

            error.setName("this name already in use");
        }

        error.setCode(checkString(categoryInfoDTO.getCode() , 2 , 10, "categoryCode" , true));

        if (!("".equals(error.getCode()))){
            valid = false;
        }else if (categoryInfoRepository.findByCodeAndStatusAndStoreInfo(categoryInfoDTO.getCode().trim() , Status.ACTIVE , categoryInfoDTO.getStoreInfoId()) != null){

            valid = false;

            error.setName("this code already in use");
        }

        error.setDescription(checkString(categoryInfoDTO.getDescription() , 5 , 100, "description" , false));

        if (!("".equals(error.getDescription()))){
            valid = false;
        }

        error.setValid(valid);

        return error;

    }
}
