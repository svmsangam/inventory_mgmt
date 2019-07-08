package com.inventory.core.api.iapi;

import com.inventory.core.model.dto.InvUserDTO;
import com.inventory.core.model.dto.RestResponseDTO;
import com.inventory.core.model.enumconstant.Permission;

/**
 * Created by dhiraj on 1/31/18.
 */
public interface IAuthenticationService {

    RestResponseDTO auth_Rest_Controller(InvUserDTO currentUser, RestResponseDTO responseDTO);

    RestResponseDTO auth_Rest_Controller_Store_All_User(InvUserDTO currentUser, RestResponseDTO responseDTO);

    RestResponseDTO auth_Rest_Controller_Check_Permission(InvUserDTO currentUser, RestResponseDTO responseDTO, Permission permission);
}
