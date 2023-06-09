package com.inventory.core.validation;

import com.inventory.core.model.dto.UnitInfoDTO;
import com.inventory.core.model.entity.UnitInfo;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.model.repository.UnitInfoRepository;
import com.inventory.web.error.UnitInfoError;
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
public class UnitInfoValidation extends GlobalValidation {



    @Autowired
    private UnitInfoRepository unitInfoRepository;

    UnitInfoError error = new UnitInfoError();

    boolean valid;

    public UnitInfoError onSave(UnitInfoDTO unitInfoDTO, BindingResult result) {

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

        error.setName(checkString(unitInfoDTO.getName(), 2, 50, "tagName", true));

        if (!("".equals(error.getName()))) {
            valid = false;
        } else if (unitInfoRepository.findByNameAndStatusAndStoreInfo(unitInfoDTO.getName().trim(), Status.ACTIVE, unitInfoDTO.getStoreInfoId()) != null) {

            valid = false;

            error.setName("this name already in use");
        }

        error.setCode(checkString(unitInfoDTO.getCode(), 2, 30, "tagCode", true));

        if (!("".equals(error.getCode()))) {
            valid = false;
        } else if (unitInfoRepository.findByCodeAndStatusAndStoreInfo(unitInfoDTO.getCode().trim(), Status.ACTIVE, unitInfoDTO.getStoreInfoId()) != null) {

            valid = false;

            error.setCode("this code already in use");
        }

        error.setValid(valid);

        return error;

    }

    public UnitInfoError onUpdate(UnitInfoDTO unitInfoDTO, BindingResult result) {

        valid = true;

        if (result.hasErrors()) {

            valid = false;

            List<FieldError> errors = result.getFieldErrors();
            for (FieldError errorResult : errors) {

                if (errorResult.getField().equals("name")) {
                    error.setName("invalid store name");
                } else if (errorResult.getField().equals("code")) {
                    error.setCode("invalid code");
                }else if (errorResult.getField().equals("unitId")) {
                    error.setName("invalid unit");
                }
            }

            error.setValid(valid);

            return error;
        }

        error.setName(checkString(unitInfoDTO.getName(), 2, 50, "tagName", true));

        if (!("".equals(error.getName()))) {
            valid = false;
        } else {

            UnitInfo unitInfo = unitInfoRepository.findByNameAndStatusAndStoreInfo(unitInfoDTO.getName().trim(), Status.ACTIVE, unitInfoDTO.getStoreInfoId());

            if (unitInfo != null) {
                if (unitInfo.getId() != unitInfoDTO.getUnitId()) {
                    valid = false;

                    error.setName("this name already in use");
                }
            } else {
                error.setName(checkLong(unitInfoDTO.getUnitId(), 0, "tagName", true));

                if (!("".equals(error.getName()))) {
                    valid = false;
                } else if (unitInfoRepository.findByIdAndStatusAndStoreInfo(unitInfoDTO.getUnitId(), Status.ACTIVE, unitInfoDTO.getStoreInfoId()) == null) {
                    valid = false;

                    error.setName("unit not found to update");

                }

            }
        }

        error.setCode(checkString(unitInfoDTO.getCode(), 2, 30, "tagCode", true));

        if (!("".equals(error.getCode()))) {
            valid = false;
        } else{

            UnitInfo unitInfo1 = unitInfoRepository.findByCodeAndStatusAndStoreInfo(unitInfoDTO.getCode().trim(), Status.ACTIVE, unitInfoDTO.getStoreInfoId());

            if (unitInfo1 != null){
                if (unitInfo1.getId() != unitInfoDTO.getUnitId()){
                    valid = false;

                    error.setCode("this code already in use");
                }
            }
        }

        error.setValid(valid);

        return error;

    }
}
