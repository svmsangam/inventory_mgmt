package com.inventory.core.model.converter;

import com.inventory.core.model.dto.UserPermissionDTO;
import com.inventory.core.model.entity.UserPermission;
import com.inventory.core.model.repository.UserRepository;
import com.inventory.core.util.IConvertable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by dhiraj on 8/10/17.
 */

@Service
public class UserPermissionConverter implements IConvertable<UserPermission, UserPermissionDTO> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserConverter userConverter;

    @Override
    public UserPermission convertToEntity(UserPermissionDTO dto) {

        UserPermission entity = new UserPermission();

        entity.setUser(userRepository.findById(dto.getUserId()).orElse(null));
        entity.setPermissionList(dto.getPermissionList());

        return entity;
    }

    @Override
    public UserPermissionDTO convertToDto(UserPermission entity) {

        if (entity == null) {
            return null;
        }

        UserPermissionDTO dto = new UserPermissionDTO();

        dto.setUserPermissionId(entity.getId());
        dto.setVersion(entity.getVersion());
        dto.setUserId(entity.getUser().getId());
        dto.setPermissionList(entity.getPermissionList());
        dto.setUserDTO(userConverter.convertToDto(entity.getUser()));

        return dto;
    }

    @Override
    public UserPermission copyConvertToEntity(UserPermissionDTO dto, UserPermission entity) {

        if (entity == null | dto == null) {
            return null;
        }

        entity.setPermissionList(dto.getPermissionList());

        return entity;
    }
}
