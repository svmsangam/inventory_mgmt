package com.inventory.core.validation;

import com.inventory.core.model.dto.SubscriberDTO;
import com.inventory.core.model.entity.ServiceInfo;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.model.repository.CityInfoRepository;
import com.inventory.core.model.repository.ServiceRepository;
import com.inventory.core.model.repository.StoreUserInfoRepository;
import com.inventory.core.model.repository.UserRepository;
import com.inventory.web.error.RenewError;
import com.inventory.web.error.SubscriberError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by dhiraj on 2/2/18.
 */
@Service
public class SubscriberValidation extends GlobalValidation{

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CityInfoRepository cityInfoRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private StoreUserInfoRepository storeUserInfoRepository;

    private SubscriberError error;

    private RenewError renewError ;

    public SubscriberError onRegister(SubscriberDTO subscriberDTO){

        error = new SubscriberError();

        boolean valid = true;

        //valid = valid && checkUserName(subscriberDTO.getUsername());
        valid = valid && checkPassword(subscriberDTO.getPassword() , subscriberDTO.getRepassword());
        valid = valid && checkCity(subscriberDTO.getCityInfoId());
        valid = valid && checkContact(subscriberDTO.getContact());
        valid = valid && checkEmail(subscriberDTO.getEmail());
        valid = valid && checkMobile(subscriberDTO.getMobile());
        valid = valid && checkFullName(subscriberDTO.getFullName());

        error.setValid(valid);

        return error;
    }

    public RenewError onRenew(long subscriberUserId , long serviceId){

        renewError = new RenewError();

        boolean valid = true;


        valid = valid && checkService(serviceId , subscriberUserId);

        renewError.setValid(valid);

        return renewError;
    }

    private boolean checkService(long serviceId , long subscriberUserId){

        renewError.setError(checkLong(serviceId , 1 , "service" , true));

        if (!"".equals(renewError.getError())){
            return false;
        }

        ServiceInfo serviceInfo = serviceRepository.findByIdAndStatus(serviceId , Status.ACTIVE);

        if ( serviceInfo == null){
            renewError.setError("invalid service");
            return false;
        }

        long countStore = storeUserInfoRepository.countAllByUserAndStatus(subscriberUserId , Status.ACTIVE);

        if (serviceInfo.getTotalStore() < countStore){
            renewError.setError("to use " + serviceInfo.getTitle() + " service the subscriber must have store less than " + serviceInfo.getTotalStore());
            return false;
        }

        return true;
    }

    private boolean checkUserName(String username){

        error.setUsername(checkString(username , 5 , 20 , "username" , true));

        if (!"".equals(error.getUsername())){
            return false;
        }

        Pattern pattern = Pattern.compile("[A-Za-z0-9_]+");

        boolean valid = pattern.matcher(username).matches();

        if (!valid){
            error.setUsername("invalid username format");

            return false;
        }

        if (userRepository.findByUsername(username) != null){
            error.setUsername("username already in use");

            return false;
        }

        return true;

    }

    private boolean checkPassword(String password , String repassword){

        error.setPassword(checkString(password , 6 , 20 , "password" , true));

        if (!"".equals(error.getPassword())){
            return false;
        }

        error.setRepassword(checkString(password , 6 , 20 , "repassword" , true));

        if (!"".equals(error.getRepassword())){
            return false;
        }

        if (!password.equals(repassword)){
            error.setRepassword("repassword did not matched");
            return false;
        }

        return true;
    }

    private boolean checkFullName(String fullname){

        error.setFullName(checkString(fullname , 5 , 50 , "fullname" , true));

        if (!"".equals(error.getFullName())){
            return false;
        }

        return true;
    }

    private boolean checkContact(String contact){

        error.setContact(checkString(contact , 9 , 10 , "contact" , false));

        if (!"".equals(error.getContact())){
            return false;
        }

        return true;
    }

    private boolean checkMobile(String mobile){

        error.setMobile(checkString(mobile , 10 , 10 , "mobile" , true));

        if (!"".equals(error.getMobile())){
            return false;
        }

        return true;
    }

    private boolean checkEmail(String email){

        error.setEmail(checkString(email , 5 , 50 , "email" , true));

        if (!"".equals(error.getEmail())){
            return false;
        }

        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();

            if (userRepository.findByUsername(email) != null){
                error.setEmail("email already in use");

                return false;
            }

        } catch (AddressException ex) {

            error.setEmail("invalid email");

            return false;
        }

        return true;
    }

    private boolean checkCity(Long cityId){

        error.setCityName(checkLong(cityId , 1 , "city" , true));

        if (!"".equals(error.getCityName())){
            return false;
        }

        if (cityInfoRepository.findByIdAndStatus(cityId , Status.ACTIVE) == null){
            error.setCityName("invalid city");
            return false;
        }
        return true;
    }
}
