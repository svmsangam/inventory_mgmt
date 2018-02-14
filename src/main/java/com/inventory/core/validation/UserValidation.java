package com.inventory.core.validation;

import com.inventory.core.model.dto.InvUserDTO;
import com.inventory.core.model.entity.User;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.model.enumconstant.UserType;
import com.inventory.core.model.repository.StoreInfoRepository;
import com.inventory.core.model.repository.UserRepository;
import com.inventory.web.error.PasswordError;
import com.inventory.web.error.UserError;
import com.inventory.web.error.UserManageError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by dhiraj on 5/2/17.
 */
@Service
public class UserValidation extends GlobalValidation {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StoreInfoRepository storeInfoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    UserError error = new UserError();

    boolean valid = true;

    public PasswordError change(String oldPassword , String newPassword , String confirmPassword , long userId){

        PasswordError error = new PasswordError();

        valid = true;

        if (oldPassword == null | newPassword == null | confirmPassword == null){

            valid = false;

            error.setError("invalid request");

        } else if (oldPassword.trim().equals("") | newPassword.trim().equals("")| confirmPassword.trim().equals("")) {

            valid = false;

            error.setError("invalid request");

        } else if (newPassword.length() < 6 | newPassword.length() > 15){

            valid = false;

            error.setError("password must be between 6 and 15 length");

        }else if (!newPassword.equals(confirmPassword)){

            valid = false;

            error.setError("password did not matched with confirm password");

        }else {

            User user = userRepository.findByIdAndStatus(userId , Status.ACTIVE);

            if (user == null){
                valid = false;

                error.setError("user not found");
            } else if (!passwordEncoder.matches(oldPassword , user.getPassword())){

                valid = false;

                error.setError("you current password is incorrect");
            }
        }

        error.setValid(valid);

        return error;
    }

    public UserError saveValidation(InvUserDTO userDto, BindingResult result) {

        valid = true;

        if (result.hasErrors()) {

            valid = false;

            List<FieldError> errors = result.getFieldErrors();
            for (FieldError errorResult : errors) {

                if (errorResult.getField().equals("inventoryuser")) {
                    error.setUsername("invalid username data");
                } else if (errorResult.getField().equals("userpassword")) {
                    error.setPassword("invalid password data");
                } else if (errorResult.getField().equals("userType")) {
                    error.setUserType("invalid userType data");
                } else if (errorResult.getField().equals("storeId")) {
                    error.setUserType("invalid store data");
                }
            }

            error.setValid(valid);

            return error;
        }

        error.setUsername(checkString(userDto.getInventoryuser(), 5, 50, "username", true));

        if (!("".equals(error.getUsername()))) {
            valid = false;
        } else {

            error.setUsername(checkUserName(userDto.getInventoryuser()));
        }

        error.setPassword(checkString(userDto.getUserpassword(), 5, 10, "password", true));

        if (!("".equals(error.getPassword()))) {
            valid = false;
        }

        error.setRepassword(checkString(userDto.getUserrepassword(), 5, 10, "repassword", true));

        if (!("".equals(error.getRepassword()))) {
            valid = false;
        } else {

            error.setRepassword(checkRepassword(userDto.getUserrepassword(), userDto.getUserpassword()));
        }

        if (userDto.getUserType() == null) {
            valid = false;
            error.setUserType("user type required");
        }

        if (UserType.ADMIN.equals(userDto.getUserType())) {
            List<User> userList = userRepository.findAllByUserTypeAndStoreInfo_Id(UserType.ADMIN, userDto.getStoreId());

            if (userList != null ) {
                if(!userList.isEmpty()) {
                    valid = false;
                    error.setUserType("user admin already exist for this store");
                }
            }
        }

        error.setStoreId(checkLong(userDto.getStoreId(), 1, "store", false));

        if (!("".equals(error.getStoreId()))) {
            valid = false;
        } else if (userDto.getStoreId() != null) {
            try {

                if (storeInfoRepository.findByIdAndStatus(userDto.getStoreId(), Status.ACTIVE) == null) {
                    valid = false;
                    error.setStoreId("invalid store data");
                }

            } catch (Exception e) {
                logger.error("user validation store " + Arrays.toString(e.getStackTrace()));
            }
        }

        error.setValid(valid);

        return error;
    }

    public UserManageError onManage(long userId) {

        UserManageError error = new UserManageError();

        boolean valid = true;

        if (userId < 0) {
            error.setError("invalid user");
            valid = false;
        } else {

            User user = userRepository.findById(userId);

            if (user == null) {
                error.setError("user not found");
                valid = false;
            } else if (!user.getUserType().equals(UserType.USER)) {
                error.setError("this service is not avialable for this user");
                valid = false;
            } else if (!user.getEnabled()) {
                error.setError("this user is not activated");
                valid = false;
            }
        }

        error.setValid(valid);

        return error;
    }

    public UserManageError onUpadteEnable(long userId) {

        UserManageError error = new UserManageError();

        boolean valid = true;

        if (userId < 0) {
            error.setError("invalid user");
            valid = false;
        } else {

            User user = userRepository.findById(userId);

            if (user == null) {
                error.setError("user not found");
                valid = false;
            } else if (!(user.getUserType().equals(UserType.USER) | user.getUserType().equals(UserType.ADMIN))) {
                error.setError("this service is not avialable for this user");
                valid = false;
            }
        }

        error.setValid(valid);

        return error;
    }


    private String checkUserName(String username) {

        Pattern pattern = Pattern.compile("[A-Za-z0-9_]+");

        boolean valid = pattern.matcher(username).matches();

        if (!valid){

            return "invalid username format";
        }

        if (userRepository.findByUsername(username.trim()) != null) {

            logger.debug("username already created");

            valid = false;

            return "username already exist";
        }
        return "";
    }

    private String checkRepassword(String repassword, String password) {

        if (repassword != null && password != null)
            if (!repassword.equals(password)) {

                logger.debug("password not matched");

                valid = false;

                return "password did not matched";
            }

        return "";
    }
}
