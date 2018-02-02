package com.inventory.core.validation;

import com.inventory.core.model.dto.SubscriberDTO;
import com.inventory.core.model.repository.CityInfoRepository;
import com.inventory.core.model.repository.UserRepository;
import com.inventory.web.error.SubscriberError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    private SubscriberError error;

    public SubscriberError onRegister(SubscriberDTO subscriberDTO){

        error = new SubscriberError();

        boolean valid = true;

        valid = valid && checkUserName(subscriberDTO.getUsername());

        valid = valid && checkPassword(subscriberDTO.getPassword() , subscriberDTO.getRepassword());

        error.setValid(valid);

        return error;
    }

    private boolean checkUserName(String username){

        error.setUsername(checkString(username , 5 , 20 , "username" , true));

        if (!"".equals(username)){
            return false;
        }

        Pattern pattern = Pattern.compile("[A-Za-z0-9_]+");

        boolean valid = pattern.matcher(username).matches();

        if (!valid){
            error.setUsername("invalid username format");

            return false;
        }

        if (userRepository.findByUsername(username) == null){
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

        error.setContact(checkString(contact , 10 , 10 , "contact" , false));

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

        return true;
    }
}
