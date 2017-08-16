package com.inventory.core.api.impl;

import com.inventory.core.api.iapi.IUserPermissionApi;
import com.inventory.core.model.converter.UserPermissionConverter;
import com.inventory.core.model.dto.UserPermissionDTO;
import com.inventory.core.model.entity.UserPermission;
import com.inventory.core.model.repository.UserPermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by dhiraj on 8/16/17.
 */
@Service
@Transactional
public class UserPermissionApi implements IUserPermissionApi{

    @Autowired
    private UserPermissionConverter userPermissionConverter;

    @Autowired
    private UserPermissionRepository userPermissionRepository;

    @Override
    public UserPermissionDTO save(UserPermissionDTO userPermissionDTO) {

        return userPermissionConverter.convertToDto(userPermissionRepository.save(userPermissionConverter.convertToEntity(userPermissionDTO)));
    }

    @Override
    public UserPermissionDTO update(UserPermissionDTO userPermissionDTO) {

        UserPermission userPermission = userPermissionRepository.findById(userPermissionDTO.getUserPermissionId());

        userPermission = userPermissionConverter.copyConvertToEntity(userPermissionDTO , userPermission);

        return userPermissionConverter.convertToDto(userPermissionRepository.save(userPermission));
    }

    @Override
    public UserPermissionDTO getById(long userPermissionId) {

        return userPermissionConverter.convertToDto(userPermissionRepository.findById(userPermissionId));
    }

    @Override
    public UserPermissionDTO getByUserId(long userId) {
        return userPermissionConverter.convertToDto(userPermissionRepository.findByUser(userId));
    }
}
