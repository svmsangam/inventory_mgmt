package com.inventory.core.api.iapi;

import com.inventory.core.model.dto.InvUserDTO;
import com.inventory.core.model.enumconstant.Permission;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.model.enumconstant.UserType;
import org.json.JSONException;

import java.io.IOException;
import java.util.List;


public interface IUserApi {

    long save(String username, String password);

    long getTotalUserByStoreInfoAndStatus(long storeInfoId , Status status);

    InvUserDTO save(InvUserDTO userDTO) throws IOException, JSONException;

    InvUserDTO getUserWithId(long userId);

    InvUserDTO getUserPermission(InvUserDTO userDTO);

    List<Permission> getUserPermission(long userId);

    InvUserDTO updateEnable(long userId);

    void changePassword(long userId, String newPassword) throws IOException, JSONException;

    InvUserDTO getUserByUserName(String userName);

    boolean nameExists(String userName);

    void updateFCMToken(String token, long userId);

    InvUserDTO changeStore(long userId, long storeId);

    List<InvUserDTO> getAllByStatusAndUserTypeIn(Status status, List<UserType> userTypeList);

    List<InvUserDTO> getAllByStatusAndUserTypeInAndSuperAdmin(Status status, List<UserType> userTypeList , long userId);

    List<InvUserDTO> getAllByStatusAndUserTypeInAndStoreInfo(Status status, List<UserType> userTypeList, long storeInfoId);

}
