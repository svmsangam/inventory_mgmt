package com.inventory.web.testMain;

import com.inventory.core.api.iapi.ICountryInfoApi;
import com.inventory.core.api.impl.StateInfoApi;
import com.inventory.core.model.dto.StateInfoDTO;
import com.inventory.core.model.enumconstant.Status;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by dhiraj on 8/12/17.
 */
public class Test {

    @Autowired
    private ICountryInfoApi countryInfoApi;

    /*@Autowired
    private IStateInfoApi stateInfoApi;*/

    public void transactional() {

        try {

            StateInfoApi stateInfoApi = new StateInfoApi();
           /* CountryInfoDTO countryInfoDTO = new CountryInfoDTO();

            countryInfoDTO.setCountryName("Dhiraj");
            countryInfoDTO.setCountryISO("DH");
            countryInfoDTO.setCountryStatus(Status.ACTIVE);

            countryInfoDTO = countryInfoApi.save(countryInfoDTO);*/

            StateInfoDTO stateInfoDTO = new StateInfoDTO();

            stateInfoDTO.setStateName("Shova");
            stateInfoDTO.setStateStatus(Status.ACTIVE);
            stateInfoDTO.setCountryId((long) 1);

            stateInfoApi.save(stateInfoDTO);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
