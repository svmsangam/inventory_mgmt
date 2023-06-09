package com.inventory.core.validation;

import com.inventory.core.model.dto.StateInfoDTO;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.model.repository.CountryInfoRepository;
import com.inventory.core.model.repository.StateInfoRepository;
import com.inventory.web.error.StateError;
import com.inventory.web.util.LoggerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StateValidation extends GlobalValidation {

    @Autowired
    private StateInfoRepository stateInfoRepository;

    @Autowired
    private CountryInfoRepository countryInfoRepository;

    private Logger logger = LoggerFactory.getLogger(StateValidation.class);

    public StateError stateValidationOnSave(StateInfoDTO stateDto) {

        StateError error = new StateError();
        boolean valid = true;

        error.setName(checkString(stateDto.getStateName(), 3, 20, "cityname", true));
        try {

            if (!("".equals(error.getName()))) {
                valid = false;
            } else if (stateInfoRepository.findByName(stateDto.getStateName().trim()) != null) {
                valid = false;
                error.setName("State name already exists");
            }
        } catch (Exception e) {
            LoggerUtil.logException(this.getClass() , e);
            error.setName("invalid StateName");
            error.setValid(false);

            return error;
        } finally {
            try {

                if (countryInfoRepository.findByIdAndStatus(stateDto.getCountryId(), Status.ACTIVE) == null) {
                    error.setCountry("invalid Country");

                    error.setValid(false);

                    return error;
                }

            } catch (Exception e) {
                LoggerUtil.logException(this.getClass() , e);
                error.setCountry("invalid Country");
                error.setValid(false);

                return error;
            }
        }


        error.setValid(valid);
        return error;
    }

}
