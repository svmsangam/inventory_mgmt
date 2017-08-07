package com.inventory.core.validation;

import com.inventory.core.model.dto.CountryInfoDTO;
import com.inventory.core.model.repository.CountryInfoRepository;
import com.inventory.web.error.CountryError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * Created by dhiraj on 5/2/17.
 */
@Service
public class CountryValidation extends GlobalValidation {

    @Autowired
    private CountryInfoRepository countryRepository;

    CountryError error = new CountryError();

    private boolean valid = true;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public CountryError countryValidateOnSave(CountryInfoDTO countryDto){

        valid = true;

        error.setName(checkString(countryDto.getCountryName() , 3, 20, "cityname" , true));
        try {

            if (!("".equals(error.getName()))){
                valid = false;
            }

            else if (countryRepository.findByName(countryDto.getCountryName().trim()) != null) {
                valid = false;
                error.setName("Country name already exists");
            }
        }catch (Exception e){
            logger.error("# Stack Trace : 'class' -" + e.getClass() + " exception : " + Arrays.toString(e.getStackTrace()));
            error.setName("invalid CountryName");
            error.setValid(false);

            return error;
        }
        error.setValid(valid);

        return error;

    }

}
