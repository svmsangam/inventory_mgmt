package com.inventory.core.validation;

import com.inventory.core.model.dto.InvUserDTO;
import com.inventory.core.model.repository.UserRepository;
import com.inventory.web.error.UserError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by dhiraj on 5/2/17.
 */
@Service
public class UserValidation extends GlobalValidation{

    @Autowired
    private UserRepository userRepository;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    UserError error = new UserError();

    boolean valid = true;

    public UserError saveValidation(InvUserDTO userDto) {

        error.setUsername(checkString(userDto.getInventoryuser(), 5, 50, "username", true));

        if (!("".equals(error.getUsername()))){
            valid = false;
        }else {

            error.setUsername(checkUserName(userDto.getInventoryuser()));
        }

        error.setPassword(checkString(userDto.getUserpassword(), 5, 10, "password", true));

        if (!("".equals(error.getPassword()))){
            valid = false;
        }

        error.setRepassword(checkString(userDto.getUserrepassword(), 5, 10, "repassword", true));

        if (!("".equals(error.getRepassword()))){
            valid = false;
        }else {

            error.setRepassword(checkRepassword(userDto.getUserrepassword() , userDto.getUserpassword()));
        }

        error.setValid(valid);

        return error;
    }


    private String checkUserName(String username) {

        if (userRepository.findByUsername(username) != null) {

            logger.debug("username already created");

            valid = false;

            return "username already exist";
        }
        return "";
    }

    private String checkRepassword(String repassword , String password){

        if (repassword != null && password != null)
        if (!repassword.equals(password)){

            logger.debug("password not matched");

            valid = false;

            return "password did not matched";
        }

        return "";
    }
}
