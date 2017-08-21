package com.inventory.core.model.converter;

import com.inventory.core.model.dto.StoreUserInfoDTO;
import com.inventory.core.model.entity.StoreUserInfo;
import com.inventory.core.model.repository.StoreInfoRepository;
import com.inventory.core.model.repository.UserRepository;
import com.inventory.core.util.IConvertable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by dhiraj on 8/10/17.
 */
@Service
public class StoreUserInfoConverter implements IConvertable<StoreUserInfo, StoreUserInfoDTO> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StoreInfoRepository storeInfoRepository;

    @Autowired
    private StoreInfoConverter storeInfoConverter;

    @Autowired
    private UserConverter userConverter;

    @Override
    public StoreUserInfo convertToEntity(StoreUserInfoDTO dto) {
        return copyConvertToEntity(dto, new StoreUserInfo());
    }

    @Override
    public StoreUserInfoDTO convertToDto(StoreUserInfo entity) {

        if (entity == null) {
            return null;
        }

        StoreUserInfoDTO dto = new StoreUserInfoDTO();

        dto.setStoreUserInfoId(entity.getId());
        dto.setStatus(entity.getStatus());
        dto.setStoreInfoDTO(storeInfoConverter.convertToDto(entity.getStoreInfo()));
        dto.setStoreInfoId(entity.getStoreInfo().getId());
        dto.setUserDTO(userConverter.convertToDto(entity.getUser()));
        dto.setUserId(entity.getUser().getId());
        dto.setVersion(entity.getVersion());

        return dto;
    }

    @Override
    public StoreUserInfo copyConvertToEntity(StoreUserInfoDTO dto, StoreUserInfo entity) {

        if (entity == null | dto == null) {
            return null;
        }

        entity.setStoreInfo(storeInfoRepository.findOne(dto.getStoreInfoId()));
        entity.setUser(userRepository.findOne(dto.getUserId()));

        return entity;
    }
}
