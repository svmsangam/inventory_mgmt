package com.inventory.core.api.impl;

import com.inventory.core.api.iapi.IItemInfoApi;
import com.inventory.core.api.iapi.IStockInfoApi;
import com.inventory.core.model.converter.ItemInfoConverter;
import com.inventory.core.model.dto.ItemInfoDTO;
import com.inventory.core.model.entity.ItemInfo;
import com.inventory.core.model.entity.OrderItemInfo;
import com.inventory.core.model.enumconstant.SalesOrderStatus;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.model.repository.ItemInfoRepository;
import com.inventory.core.model.repository.OrderItemInfoRepository;
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

    @Autowired
    private IStockInfoApi stockInfoApi;

    @Autowired
    private OrderItemInfoRepository orderItemInfoRepository;

    @Override
    public ItemInfoDTO save(ItemInfoDTO itemInfoDTO) {

        ItemInfo itemInfo = itemInfoConverter.convertToEntity(itemInfoDTO);

        itemInfo.setStatus(Status.ACTIVE);

        itemInfo = itemInfoRepository.save(itemInfo);

        stockInfoApi.updateOnItemSave(itemInfo.getProductInfo().getId() , itemInfo.getInStock());

        return itemInfoConverter.convertToDto(itemInfo);
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
    public List<ItemInfoDTO> getAllByStatusAndStoreWithStock(Status status, long storeId) {
        return itemInfoConverter.convertToDtoList(itemInfoRepository.findAllByStatusAndStoreInfoHavingInStock(status , storeId));
    }

    @Override
    public List<ItemInfoDTO> getAllByProductAndStatusAndStore(long productInfoId, Status status, long storeId) {
        return itemInfoConverter.convertToDtoList(itemInfoRepository.findAllByStatusAndStoreInfoAndProductInfo(status , storeId , productInfoId));
    }

    @Override
    public void updateInStockOnSaleTrack(SalesOrderStatus track, long orderId) {

        List<OrderItemInfo> orderItemInfoList = orderItemInfoRepository.findAllByStatusAndOrderInfo(Status.ACTIVE, orderId);

        for (OrderItemInfo orderItemInfo : orderItemInfoList) {

            ItemInfo itemInfo = itemInfoRepository.findById(orderItemInfo.getItemInfo().getId());

            if (track.equals(SalesOrderStatus.PENDDING)) {

                itemInfo.setInStock(itemInfo.getInStock() - orderItemInfo.getQuantity());

            } else if (track.equals(SalesOrderStatus.CANCEL)) {

                itemInfo.setInStock(itemInfo.getInStock() + orderItemInfo.getQuantity());
            }

            itemInfoRepository.save(itemInfo);

        }
    }
}
