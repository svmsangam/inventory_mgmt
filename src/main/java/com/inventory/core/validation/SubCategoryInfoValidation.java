package com.inventory.core.validation;

import com.inventory.core.model.dto.SubCategoryInfoDTO;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.model.repository.CategoryInfoRepository;
import com.inventory.core.model.repository.SubCategoryInfoRepository;
import com.inventory.web.error.SubCategoryInfoError;
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
public class SubCategoryInfoValidation extends GlobalValidation {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CategoryInfoRepository categoryInfoRepository;

    @Autowired
    private SubCategoryInfoRepository subCategoryInfoRepository;

    SubCategoryInfoError error = new SubCategoryInfoError();

    boolean valid;

    public SubCategoryInfoError onSave(SubCategoryInfoDTO subCategoryInfoDTO, BindingResult result) {

        valid = true;

        if (result.hasErrors()) {

            valid = false;

            List<FieldError> errors = result.getFieldErrors();
            for (FieldError errorResult : errors) {

                if (errorResult.getField().equals("name")) {
                    error.setName("invalid store name");
                } else if (errorResult.getField().equals("code")) {
                    error.setCode("invalid code");
                } else if (errorResult.getField().equals("description")) {
                    error.setCode("invalid description");
                } else if (errorResult.getField().equals("categoryId")) {
                    error.setCategoryId("invalid category");
                }
            }

            error.setValid(valid);

            return error;
        }

        error.setName(checkString(subCategoryInfoDTO.getName(), 2, 10, "subcategoryName", true));

        if (!("".equals(error.getName()))) {
            valid = false;
        } else if (subCategoryInfoRepository.findByNameAndStatusAndStoreInfo(subCategoryInfoDTO.getName().trim(), Status.ACTIVE, subCategoryInfoDTO.getStoreInfoId()) != null) {

            valid = false;

            error.setName("this name already in use");
        }

        error.setCode(checkString(subCategoryInfoDTO.getCode(), 2, 10, "subcategoryCode", true));

        if (!("".equals(error.getCode()))) {
            valid = false;
        } else if (subCategoryInfoRepository.findByCodeAndStatusAndStoreInfo(subCategoryInfoDTO.getCode().trim(), Status.ACTIVE, subCategoryInfoDTO.getStoreInfoId()) != null) {

            valid = false;

            error.setName("this code already in use");
        }

        error.setDescription(checkString(subCategoryInfoDTO.getDescription(), 5, 100, "description", false));

        if (!("".equals(error.getDescription()))) {
            valid = false;
        }

        error.setCategoryId(checkLong(subCategoryInfoDTO.getCategoryId(), 1, "categoryId", true));

        if (!("".equals(error.getCode()))) {
            valid = false;
        } else if (categoryInfoRepository.findByIdAndStatusAndStoreInfo(subCategoryInfoDTO.getCategoryId(), Status.ACTIVE, subCategoryInfoDTO.getStoreInfoId()) == null) {
            valid = false;
            error.setCategoryId("invalid category");
        }

        error.setValid(valid);

        return error;

    }
}
