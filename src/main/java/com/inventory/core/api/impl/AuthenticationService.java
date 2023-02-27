package com.inventory.core.api.impl;

import com.inventory.core.api.iapi.IAuthenticationService;
import com.inventory.core.api.iapi.IFiscalYearInfoApi;
import com.inventory.core.model.dto.FiscalYearInfoDTO;
import com.inventory.core.model.dto.InvUserDTO;
import com.inventory.core.model.dto.RestResponseDTO;
import com.inventory.core.model.enumconstant.Permission;
import com.inventory.core.model.enumconstant.ResponseStatus;
import com.inventory.core.util.Authorities;
import com.inventory.web.util.AuthenticationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by dhiraj on 1/31/18.
 */
@Service
public class AuthenticationService implements IAuthenticationService {

    @Autowired
    private IFiscalYearInfoApi fiscalYearInfoApi;


    @Override
    public RestResponseDTO auth_Rest_Controller_Store_All_User(InvUserDTO currentUser, RestResponseDTO responseDTO){

        if (!responseDTO.getStatus().equals(ResponseStatus.SUCCESS.getValue())){

            return responseDTO;
        }

        if (currentUser.getStoreId() == null) {

            responseDTO.setStatus(ResponseStatus.FAILURE.getValue());
            responseDTO.setMessage("Store not assigned");

            return responseDTO;
        }

        FiscalYearInfoDTO currentFiscalYear = fiscalYearInfoApi.getCurrentFiscalYearByStoreInfo(currentUser.getStoreId());

        if (currentFiscalYear == null){

            responseDTO.setStatus(ResponseStatus.FAILURE.getValue());
            responseDTO.setMessage("please create current fiscal year");

            return responseDTO;

        }

        responseDTO.setStatus(ResponseStatus.SUCCESS.getValue());

        return responseDTO;
    }

    @Override
    public RestResponseDTO auth_Rest_Controller_Check_Permission(InvUserDTO currentUser, RestResponseDTO responseDTO, Permission permission){

        if (currentUser.getUserauthority().contains(Authorities.USER) & !AuthenticationUtil.checkPermission(currentUser, permission)) {

            responseDTO.setStatus(ResponseStatus.FAILURE.getValue());
            responseDTO.setMessage("access denied");

            return responseDTO;
        }

        responseDTO.setStatus(ResponseStatus.SUCCESS.getValue());

        return responseDTO;

    }
}
