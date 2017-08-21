package com.inventory.core.validation;

import com.inventory.core.model.dto.CityInfoDTO;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.model.repository.CityInfoRepository;
import com.inventory.core.model.repository.StateInfoRepository;
import com.inventory.web.error.CityError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class CityInfoValidation extends GlobalValidation {

    @Autowired
    private CityInfoRepository cityInfoRepository;

    @Autowired
    private StateInfoRepository stateInfoRepository;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

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
            logger.error("# Stack Trace : 'class' -" + e.getClass() + " exception : " + Arrays.toString(e.getStackTrace()));
            error.setName("invalid CityName");
            error.setValid(false);

            return error;
        } finally {
            try {

                if (stateInfoRepository.findByIdAndStatus(cityDto.getStateId(), Status.ACTIVE) == null) {
                    logger.error("@ Validation Trace : 'invalid sate Id on save city' ");
                    error.setState("invalid State");

                    error.setValid(false);

                    return error;
                }

            } catch (Exception e) {
                logger.error("# Stack Trace : 'class' -" + e.getClass() + " exception : " + Arrays.toString(e.getStackTrace()));
                error.setState("invalid State");
                error.setValid(false);

                return error;
            }
        }


        error.setValid(valid);
        return error;
    }
}
