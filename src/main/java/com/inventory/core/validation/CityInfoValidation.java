package com.inventory.core.validation;

import com.inventory.core.model.dto.CityInfoDTO;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.model.repository.CityInfoRepository;
import com.inventory.core.model.repository.StateInfoRepository;
import com.inventory.web.error.CityError;
import com.inventory.web.util.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityInfoValidation extends GlobalValidation {

    @Autowired
    private CityInfoRepository cityInfoRepository;

    @Autowired
    private StateInfoRepository stateInfoRepository;



    public CityError cityValidationOnSave(CityInfoDTO cityDto) {

        CityError error = new CityError();
        boolean valid = true;

        error.setName(checkString(cityDto.getCityName(), 3, 20, "cityname", true));
        try {

            if (!("".equals(error.getName()))) {
                valid = false;
            } else if (cityInfoRepository.findByName(cityDto.getCityName().trim()) != null) {
                valid = false;
                error.setName("City name already exists");
            }
        } catch (Exception e) {
            LoggerUtil.logException(this.getClass() , e);
            error.setName("invalid CityName");
            error.setValid(false);

            return error;
        } finally {
            try {

                if (stateInfoRepository.findByIdAndStatus(cityDto.getStateId(), Status.ACTIVE) == null) {
                    error.setState("invalid State");

                    error.setValid(false);

                    return error;
                }

            } catch (Exception e) {
                LoggerUtil.logException(this.getClass() , e);
                error.setState("invalid State");
                error.setValid(false);

                return error;
            }
        }


        error.setValid(valid);
        return error;
    }
}
