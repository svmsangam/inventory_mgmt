package com.inventory.core.api.impl;

import com.inventory.core.api.iapi.IStoreUserInfoApi;
import com.inventory.core.model.converter.StoreInfoConverter;
import com.inventory.core.model.converter.StoreUserInfoConverter;
import com.inventory.core.model.dto.StoreInfoDTO;
import com.inventory.core.model.dto.StoreUserInfoDTO;
import com.inventory.core.model.entity.StoreUserInfo;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.model.repository.StoreInfoRepository;
import com.inventory.core.model.repository.StoreUserInfoRepository;
import com.inventory.core.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by dhiraj on 8/15/17.
 */
@Service
@Transactional
public class StoreUserInfoApi implements IStoreUserInfoApi {

    @Autowired
    UserRepository userRepository;

    @Autowired
    StoreInfoRepository storeInfoRepository;

    @Autowired
    StoreInfoConverter storeInfoConverter;

    @Autowired
    StoreUserInfoConverter storeUserInfoConverter;

    @Autowired
    StoreUserInfoRepository storeUserInfoRepository;

    @Override
    public StoreUserInfoDTO save(long userId, long storeId) {

        StoreUserInfo storeUserInfo = new StoreUserInfo();

        storeUserInfo.setUser(userRepository.findById(userId));
        storeUserInfo.setStoreInfo(storeInfoRepository.findById(storeId));
        storeUserInfo.setStatus(Status.ACTIVE);

        return storeUserInfoConverter.convertToDto(storeUserInfoRepository.save(storeUserInfo));
    }

    @Override
    public StoreUserInfoDTO getByUserAndStore(long userId, long storeId) {
        return storeUserInfoConverter.convertToDto(storeUserInfoRepository.findByStoreInfoAndUserAndStatus(storeId, userId, Status.ACTIVE));
    }

    @Override
    public List<StoreInfoDTO> getAllStoreByUser(long userId) {
        return storeInfoConverter.convertToDtoList(storeUserInfoRepository.findAllByUserAndStatus(userId, Status.ACTIVE));
    }
}
