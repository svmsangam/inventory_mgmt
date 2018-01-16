package com.inventory.core.validation;

import com.inventory.core.model.dto.ClientInfoDTO;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.model.repository.CityInfoRepository;
import com.inventory.core.model.repository.ClientInfoRepository;
import com.inventory.web.error.ClientInfoError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by dhiraj on 8/26/17.
 */
@Service
public class ClientInfoValidation extends GlobalValidation{

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CityInfoRepository cityInfoRepository;

    @Autowired
    private ClientInfoRepository clientInfoRepository;

    ClientInfoError error;

    public ClientInfoError onSave(ClientInfoDTO clientInfoDTO){

        error = new ClientInfoError();

        boolean valid = true;

        error.setName(checkString(clientInfoDTO.getName().trim() , 3 , 50 , "name" , true));

        if (!"".equals(error.getName())){
            valid = false;
        }

        error.setCompanyName(checkString(clientInfoDTO.getCompanyName() , 4 , 30 , "company name" , false));

        if (!"".equals(error.getCompanyName())){
            valid = false;
        }

        error.setCityId(checkLong(clientInfoDTO.getCityId() , 1 , "city" , false));

        if (clientInfoDTO.getCityId() != null) {
            if (!"".equals(error.getCityId())) {
                valid = false;
            } else if (cityInfoRepository.findByIdAndStatus(clientInfoDTO.getCityId(), Status.ACTIVE) == null) {
                error.setCityId("invalid city");
                valid = false;
            }
        }

        error.setContact(checkString(clientInfoDTO.getContact() , 7 , 10, "contact" , false));

        if (clientInfoDTO.getContact() != null && !clientInfoDTO.getContact().isEmpty()) {
            if (!"".equals(error.getContact())) {
                valid = false;
            } else if (clientInfoDTO.getContact() != null) {
                if (clientInfoRepository.findByContact(clientInfoDTO.getContact()) != null) {
                    error.setContact("this contact already registered");

                    valid = false;
                }
            }
        }

        error.setMobileNumber(checkString(clientInfoDTO.getMobileNumber() , 10 , 10  , "mobile number" , false));

        if (clientInfoDTO.getMobileNumber() != null && !clientInfoDTO.getMobileNumber().isEmpty()) {
            if (!"".equals(error.getMobileNumber())) {
                valid = false;
            } else if (clientInfoRepository.findByMobileNumber(clientInfoDTO.getMobileNumber()) != null) {
                error.setMobileNumber("this mobile already registered");

                valid = false;
            }
        }

        error.setEmail(checkString(clientInfoDTO.getEmail() , 5 , 50 , "email" , false));

        if (!"".equals(error.getEmail())){
            valid = false;
        }

        error.setStreet(checkString(clientInfoDTO.getStreet() , 3, 50 , "Stret" , false));

        if (!"".equals(error.getStreet())){
            valid = false;
        }

        error.setValid(valid);

        return error;
    }


}
