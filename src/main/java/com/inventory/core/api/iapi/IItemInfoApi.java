package com.inventory.core.api.iapi;

import com.inventory.core.model.dto.ItemInfoDTO;
import com.inventory.core.model.enumconstant.Status;

import java.util.List;

/**
 * Created by dhiraj on 8/15/17.
 */
public interface IItemInfoApi {

    ItemInfoDTO save(ItemInfoDTO itemInfoDTO);

    ItemInfoDTO update(ItemInfoDTO itemInfoDTO);

    void delete(long itemInfoId);

    ItemInfoDTO getByIdAndStoreAndStatus(long itemInfoId, long storeId, Status status);

    List<ItemInfoDTO> list(Status status, long storeId);

    List<ItemInfoDTO> getAllByProductAndStatusAndStore(long productInfoId, Status status, long storeId);
}
