package com.inventory.core.api.iapi;

import com.inventory.core.model.dto.UserPermissionDTO;

/**
 * Created by dhiraj on 8/16/17.
 */
public interface IUserPermissionApi {

    UserPermissionDTO save(UserPermissionDTO userPermissionDTO);

    UserPermissionDTO update(UserPermissionDTO userPermissionDTO);

    UserPermissionDTO getById(long userPermissionId);

    UserPermissionDTO getByUserId(long userId);


}
