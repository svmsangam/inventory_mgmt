package com.inventory.core.api.impl;

import com.inventory.core.api.iapi.IUserApi;
import com.inventory.core.model.converter.UserConverter;
import com.inventory.core.model.dto.InvUserDTO;
import com.inventory.core.model.entity.User;
import com.inventory.core.model.entity.UserPermission;
import com.inventory.core.model.enumconstant.Permission;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.model.enumconstant.UserType;
import com.inventory.core.model.repository.StoreInfoRepository;
import com.inventory.core.model.repository.StoreUserInfoRepository;
import com.inventory.core.model.repository.UserPermissionRepository;
import com.inventory.core.model.repository.UserRepository;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
public class UserApi implements IUserApi {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private StoreInfoRepository storeInfoRepository;

    @Autowired
    private UserPermissionRepository userPermissionRepository;

    @Autowired
    private StoreUserInfoRepository storeUserInfoRepository;

    @Override
    public long getTotalUserByStoreInfoAndStatus(long storeInfoId, Status status) {
        return userRepository.countAllByStoreInfoAndStatus(storeInfoId , status);
    }

    @Override
    public InvUserDTO save(InvUserDTO userDTO) throws IOException, JSONException {

        userDTO.setEnable(false);

        User user = userConverter.convertToEntity(userDTO);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user = userRepository.save(user);

        return userConverter.convertToDto(user);
    }

    public void changePassword(long userId, String newPassword) throws IOException, JSONException {
        User user = userRepository.findOne(userId);

        user.setPassword(passwordEncoder.encode(newPassword));

        userRepository.save(user);
    }

    @Override
    public InvUserDTO getUserWithId(long userId) {
        return userConverter.convertToDto(userRepository.findByIdAndStatus(userId, Status.ACTIVE));

    }

    @Override
    public InvUserDTO getUserPermission(InvUserDTO userDTO) {

        UserPermission userPermission = userPermissionRepository.findByPermissionUser(userDTO.getUserId());

        if (userPermission != null)
            userDTO.setPermissionList(userPermission.getPermissionList());

        return userDTO;
    }

    @Override
    public List<Permission> getUserPermission(long userId) {

        UserPermission userPermission = userPermissionRepository.findByPermissionUser(userId);

        if (userPermission != null)
            return userPermission.getPermissionList();

        return null;

    }

    @Override
    public InvUserDTO updateEnable(long userId) {

        User user = userRepository.findById(userId);

        if (user.getEnabled()) {
            user.setEnabled(false);

        } else {
            user.setEnabled(true);
        }

        return userConverter.convertToDto(userRepository.save(user));
    }

    @Override
    public InvUserDTO getUserByUserName(String userName) {

        User user = userRepository.findByUsername(userName);

        return userConverter.convertToDto(user);
    }

    @Override
    public boolean nameExists(String userName) {
        return userRepository.findByUsername(userName) != null;
    }

    @Override
    public InvUserDTO changeStore(long userId, long storeId) {

        User user = userRepository.findById(userId);

        user.setStoreInfo(storeInfoRepository.findById(storeId));

        return userConverter.convertToDto(userRepository.save(user));
    }

    @Override
    public List<InvUserDTO> getAllByStatusAndUserTypeIn(Status status, List<UserType> userTypeList) {
        return null;
    }

    @Override
    public List<InvUserDTO> getAllByStatusAndUserTypeInAndSuperAdmin(Status status, List<UserType> userTypeList , long userId) {

        List<Long> storeList = storeUserInfoRepository.findAllStoreIdByUserAndStatus(userId , Status.ACTIVE);

        if (storeList == null){
            storeList = new ArrayList<>();

            storeList.add((long)0);
        }

        return userConverter.convertToDtoList(userRepository.findAllByStatusAndUserTypeInAndSuperAdmin(status, userTypeList , storeList));
    }

    @Override
    public List<InvUserDTO> getAllByStatusAndUserTypeInAndStoreInfo(Status status, List<UserType> userTypeList, long storeInfoId) {
        return userConverter.convertToDtoList(userRepository.findAllByStatusAndUserTypeInAndStoreInfo(status, userTypeList, storeInfoId));
    }

}
