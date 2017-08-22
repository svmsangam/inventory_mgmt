package com.inventory.core.api.impl;

import com.inventory.core.api.iapi.IItemInfoApi;
import com.inventory.core.model.converter.ItemInfoConverter;
import com.inventory.core.model.dto.ItemInfoDTO;
import com.inventory.core.model.entity.ItemInfo;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.model.repository.ItemInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by dhiraj on 8/22/17.
 */
@Transactional
@Service
public class ItemInfoApi implements IItemInfoApi{

    @Autowired
    private ItemInfoRepository itemInfoRepository;

    @Autowired
    private ItemInfoConverter itemInfoConverter;

    @Override
    public ItemInfoDTO save(ItemInfoDTO itemInfoDTO) {

        ItemInfo itemInfo = itemInfoConverter.convertToEntity(itemInfoDTO);

        itemInfo.setStatus(Status.ACTIVE);

        return itemInfoConverter.convertToDto(itemInfoRepository.save(itemInfo));
    }

    @Override
    public ItemInfoDTO update(ItemInfoDTO itemInfoDTO) {

        ItemInfo itemInfo = itemInfoRepository.findById(itemInfoDTO.getItemId());

        itemInfo = itemInfoConverter.copyConvertToEntity(itemInfoDTO , itemInfo);

        return itemInfoConverter.convertToDto(itemInfoRepository.save(itemInfo));
    }

    @Override
    public void delete(long itemInfoId) {

        ItemInfo itemInfo = itemInfoRepository.findById(itemInfoId);

        itemInfo.setStatus(Status.DELETED);

        itemInfoRepository.save(itemInfo);
    }

    @Override
    public ItemInfoDTO getByIdAndStoreAndStatus(long itemInfoId, long storeId, Status status) {
        return itemInfoConverter.convertToDto(itemInfoRepository.findByIdAndStatusAndStoreInfo(itemInfoId , status , storeId));
    }

    @Override
    public List<ItemInfoDTO> list(Status status, long storeId) {
        return itemInfoConverter.convertToDtoList(itemInfoRepository.findAllByStatusAndStoreInfo(status , storeId));
    }

    @Override
    public List<ItemInfoDTO> getAllByProductAndStatusAndStore(long productInfoId, Status status, long storeId) {
        return itemInfoConverter.convertToDtoList(itemInfoRepository.findAllByStatusAndStoreInfoAndProductInfo(status , storeId , productInfoId));
    }
}
