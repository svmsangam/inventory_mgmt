package com.inventory.core.api.impl;

import com.inventory.core.api.iapi.IItemInfoApi;
import com.inventory.core.model.dto.ItemInfoDTO;
import com.inventory.core.model.enumconstant.Status;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by dhiraj on 8/22/17.
 */
@Transactional
@Service
public class ItemInfoApi implements IItemInfoApi{
    @Override
    public ItemInfoDTO save(ItemInfoDTO itemInfoDTO) {
        return null;
    }

    @Override
    public ItemInfoDTO update(ItemInfoDTO itemInfoDTO) {
        return null;
    }

    @Override
    public void delete(long itemInfoId) {

    }

    @Override
    public ItemInfoDTO getByIdAndStoreAndStatus(long itemInfoId, long storeId, Status status) {
        return null;
    }

    @Override
    public List<ItemInfoDTO> list(Status status, long storeId) {
        return null;
    }

    @Override
    public List<ItemInfoDTO> getAllByProductAndStatusAndStore(long productInfoId, Status status, long storeId) {
        return null;
    }
}
