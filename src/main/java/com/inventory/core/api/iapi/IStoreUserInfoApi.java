package com.inventory.core.api.iapi;

import com.inventory.core.model.dto.StoreInfoDTO;
import com.inventory.core.model.dto.StoreUserInfoDTO;

import java.util.List;

/**
 * Created by dhiraj on 8/15/17.
 */
public interface IStoreUserInfoApi {

    StoreUserInfoDTO save(long userId , long storeId);

    StoreUserInfoDTO getByUserAndStore(long userId , long storeId);

    List<StoreInfoDTO> getAllStoreByUser(long userId);
}
