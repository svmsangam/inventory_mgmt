package com.inventory.core.api.impl;

import com.inventory.core.api.iapi.IStoreUserInfoApi;
import com.inventory.core.model.converter.StoreInfoConverter;
import com.inventory.core.model.converter.StoreUserInfoConverter;
import com.inventory.core.model.converter.UserConverter;
import com.inventory.core.model.dto.InvUserDTO;
import com.inventory.core.model.dto.StoreInfoDTO;
import com.inventory.core.model.dto.StoreUserInfoDTO;
import com.inventory.core.model.entity.StoreUserInfo;
import com.inventory.core.model.entity.User;
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
    private UserRepository userRepository;

    @Autowired
    private StoreInfoRepository storeInfoRepository;

    @Autowired
    private StoreInfoConverter storeInfoConverter;

    @Autowired
    private StoreUserInfoConverter storeUserInfoConverter;

    @Autowired
    private StoreUserInfoRepository storeUserInfoRepository;

    @Autowired
    private UserConverter userConverter;

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

    @Override
    public List<Long> getAllStoreIdStoreByUser(long userId) {
        return storeUserInfoRepository.findAllStoreIdByUserAndStatus(userId , Status.ACTIVE);
    }

    @Override
    public List<InvUserDTO> getAllUserBySuperAdmin(long superAdminId){

        List<Long> storeIdList = storeUserInfoRepository.findAllStoreIdByUserAndStatus(superAdminId , Status.ACTIVE);

        if (storeIdList == null){
            return null;
        }

        if (storeIdList.isEmpty()){
            return null;
        }

        return userConverter.convertToDtoList(userRepository.findAllByStoreInfo_IdIn(storeIdList));
    }
}
