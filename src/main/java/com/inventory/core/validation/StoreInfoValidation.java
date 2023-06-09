package com.inventory.core.validation;

import com.inventory.core.api.iapi.ISubscriberServiceApi;
import com.inventory.core.model.dto.StoreInfoDTO;
import com.inventory.core.model.dto.SubscriberServiceDTO;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.model.repository.CityInfoRepository;
import com.inventory.core.model.repository.StoreInfoRepository;
import com.inventory.core.model.repository.StoreUserInfoRepository;
import com.inventory.web.error.StoreInfoError;
import com.inventory.web.util.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

/**
 * Created by dhiraj on 8/14/17.
 */
@Service
public class StoreInfoValidation extends GlobalValidation {

    @Autowired
    private StoreInfoRepository storeInfoRepository;

    @Autowired
    private CityInfoRepository cityInfoRepository;

    @Autowired
    private ISubscriberServiceApi subscriberServiceApi;

    @Autowired
    private StoreUserInfoRepository storeUserInfoRepository;

    StoreInfoError error = new StoreInfoError();

    boolean valid;

    public StoreInfoError onSave(StoreInfoDTO storeInfoDTO, BindingResult result , long currentUser) {

        valid = true;


        if (result.hasErrors()) {

            valid = false;

            List<FieldError> errors = result.getFieldErrors();
            for (FieldError errorResult : errors) {

                if (errorResult.getField().equals("name")) {
                    error.setName("invalid store name");
                } else if (errorResult.getField().equals("cityId")) {
                    error.setCityName("invalid city");
                } else if (errorResult.getField().equals("contact")) {
                    error.setContact("invalid data provided");
                } else if (errorResult.getField().equals("email")) {
                    error.setEmail("invalid city");
                } else if (errorResult.getField().equals("mobileNumber")) {
                    error.setMobileNumber("invalid data provided");
                } else if (errorResult.getField().equals("regNumber")) {
                    error.setRegNumber("invalid city");
                } else if (errorResult.getField().equals("panNumber")) {
                    error.setPanNumber("invalid data provided");
                } else if (errorResult.getField().equals("street")) {
                    error.setStreet("invalid data provided");
                } else {
                    //System.out.println(errorResult.getField() + " " + errorResult.getRejectedValue());
                    error.setName("invalid data provided");
                }
            }

            error.setValid(valid);

            return error;
        }


        error.setName(checkString(storeInfoDTO.getName(), 5, 50, "storeName", true));

        if (!("".equals(error.getName()))) {
            valid = false;
        } else {
            checkStoreName(storeInfoDTO.getName().trim() , currentUser);
        }

        error.setCityName(checkLong(storeInfoDTO.getCityId(), 1, "city", true));

        try {

            if (!("".equals(error.getCityName()))) {
                valid = false;
            } else if (cityInfoRepository.findByIdAndStatus(storeInfoDTO.getCityId(), Status.ACTIVE) == null) {
                valid = false;
                error.setCityName("invalid city");
            }
        } catch (Exception e) {
            LoggerUtil.logException(this.getClass() , e);

            valid = false;
            error.setCityName("invalid city");
        }

        error.setContact(checkString(storeInfoDTO.getContact(), 7, 10, "contact", true));

        if (!("".equals(error.getContact()))) {
            valid = false;
        }

        error.setEmail(checkString(storeInfoDTO.getEmail(), 5, 50, "email", true));

        if (!("".equals(error.getEmail()))) {
            valid = false;
        }

        error.setMobileNumber(checkString(storeInfoDTO.getMobileNumber(), 10, 10, "mobile Number", false));

        if (!("".equals(error.getMobileNumber()))) {
            valid = false;
        }

        error.setPanNumber(checkString(storeInfoDTO.getPanNumber(), 5, 20, "pan number", true));

        if (!("".equals(error.getPanNumber()))) {
            valid = false;
        }

        error.setRegNumber(checkString(storeInfoDTO.getRegNumber(), 5, 20, "registration number", true));

        if (!("".equals(error.getRegNumber()))) {
            valid = false;
        }

        error.setValid(valid);

        return error;
    }

    public StoreInfoError onUpdate(StoreInfoDTO storeInfoDTO, BindingResult result) {

        valid = true;


        if (result.hasErrors()) {

            valid = false;

            List<FieldError> errors = result.getFieldErrors();
            for (FieldError errorResult : errors) {

                if (errorResult.getField().equals("name")) {
                    error.setName("invalid store name");
                } else if (errorResult.getField().equals("storeId")) {
                    error.setCityName("invalid store");
                } else if (errorResult.getField().equals("cityId")) {
                    error.setCityName("invalid city");
                } else if (errorResult.getField().equals("contact")) {
                    error.setContact("invalid data provided");
                } else if (errorResult.getField().equals("email")) {
                    error.setEmail("invalid city");
                } else if (errorResult.getField().equals("mobileNumber")) {
                    error.setMobileNumber("invalid data provided");
                } else if (errorResult.getField().equals("regNumber")) {
                    error.setRegNumber("invalid city");
                } else if (errorResult.getField().equals("panNumber")) {
                    error.setPanNumber("invalid data provided");
                } else if (errorResult.getField().equals("street")) {
                    error.setStreet("invalid data provided");
                } else {
                    //System.out.println(errorResult.getField() + " " + errorResult.getRejectedValue());
                    error.setName("invalid data provided");
                }
            }

            error.setValid(valid);

            return error;
        }


        error.setName(checkLong(storeInfoDTO.getStoreId(), 1, "storeId", true));

        if (!("".equals(error.getName()))) {
            valid = false;
        } else {
            try {

                if (storeInfoRepository.findByIdAndStatus(storeInfoDTO.getStoreId(), Status.ACTIVE) == null) {
                    valid = false;

                    error.setName("store not found");
                }
            } catch (Exception e) {
                valid = false;

                error.setName("store not found");
            }
        }

       /* error.setCityName(checkLong(storeInfoDTO.getCityId() , 1 , "city" , true));

        try {

            if (!("".equals(error.getCityName()))){
                valid = false;
            } else if (cityInfoRepository.findByIdAndStatus(storeInfoDTO.getCityId(), Status.ACTIVE) == null) {
                valid = false;
                error.setCityName("invalid city");
            }
        }catch (Exception e){
            LoggerUtil.logException(this.getClass() , e);(e.getClass() + " error on store validation for city id : "+ Arrays.toString(e.getStackTrace()));

            valid = false;
            error.setCityName("invalid city");
        }*/

        error.setContact(checkString(storeInfoDTO.getContact(), 7, 10, "contact", true));

        if (!("".equals(error.getContact()))) {
            valid = false;
        }

        error.setEmail(checkString(storeInfoDTO.getEmail(), 5, 50, "email", true));

        if (!("".equals(error.getEmail()))) {
            valid = false;
        }

        error.setMobileNumber(checkString(storeInfoDTO.getMobileNumber(), 10, 10, "mobile Number", false));

        if (!("".equals(error.getMobileNumber()))) {
            valid = false;
        }

        error.setPanNumber(checkString(storeInfoDTO.getPanNumber(), 5, 20, "pan number", true));

        if (!("".equals(error.getPanNumber()))) {
            valid = false;
        }

        error.setRegNumber(checkString(storeInfoDTO.getRegNumber(), 5, 20, "registration number", true));

        if (!("".equals(error.getRegNumber()))) {
            valid = false;
        }

        error.setValid(valid);

        return error;
    }

    private void checkStoreName(String name , long superAdminId) {

       SubscriberServiceDTO subscriberServiceDTO = subscriberServiceApi.getSelectedByUserId(superAdminId);

       if (subscriberServiceDTO == null ){
           valid = false;

           error.setName("please subscribe any service");
       }

       long count = storeUserInfoRepository.countAllByUserAndStatus(superAdminId , Status.ACTIVE);

       if (subscriberServiceDTO.getServiceInfo().getTotalStore() <= count){
           valid = false;

           error.setName("you reached maximum store capacity");
       }
        if (storeInfoRepository.findByNameAndStatus(name, Status.ACTIVE) != null) {
            valid = false;

            error.setName("store name not avialabale");
        }
    }
}
