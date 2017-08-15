package com.inventory.core.api.iapi;

import com.inventory.core.model.dto.StoreInfoDTO;
import com.inventory.core.model.enumconstant.Status;

import java.util.List;

/**
 * Created by dhiraj on 8/1/17.
 */
public interface IStoreInfoApi {

    StoreInfoDTO save(StoreInfoDTO storeInfoDTO , long currentUserId);

    StoreInfoDTO update(StoreInfoDTO storeInfoDTO);

    void delete(long storeId);

    StoreInfoDTO show(long storeId, Status status);

    List<StoreInfoDTO> list(Status status);

    StoreInfoDTO getStoreByNameAndStatus(String storeName, long storeId);

    long storeCount(Status status, long storeId);

}
