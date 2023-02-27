package com.inventory.core.api.impl;

import com.inventory.core.api.iapi.IItemInfoApi;
import com.inventory.core.api.iapi.IStockInfoApi;
import com.inventory.core.model.converter.ItemInfoConverter;
import com.inventory.core.model.dto.ItemInfoDTO;
import com.inventory.core.model.entity.ItemInfo;
import com.inventory.core.model.entity.OrderItemInfo;
import com.inventory.core.model.entity.ReturnItemInfo;
import com.inventory.core.model.enumconstant.SalesOrderStatus;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.model.liteentity.ItemDomain;
import com.inventory.core.model.liteentity.ReturnItemInfoDomain;
import com.inventory.core.model.repository.ItemInfoRepository;
import com.inventory.core.model.repository.OrderItemInfoRepository;
import com.inventory.core.model.repository.ReturnItemInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @Autowired
    private ReturnItemInfoRepository returnItemInfoRepository;

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

        ItemInfo itemInfo = itemInfoRepository.findById(itemInfoDTO.getItemId()).orElseThrow();

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
    @Transactional
    public void addUpQuantity(List<Long> itemIdArr, int quantity) {
        int totalQuantity = 0;
        long productId = 0;
        for (long itemId : itemIdArr){
            ItemInfo itemInfo = itemInfoRepository.findById(itemId);

            itemInfo.setQuantity(quantity + itemInfo.getQuantity());
            itemInfo.setInStock(quantity + itemInfo.getInStock());

            itemInfoRepository.save(itemInfo);

            totalQuantity = totalQuantity + quantity;

            if (productId ==0){
                productId = itemInfo.getProductInfo().getId();
            }

        }

        stockInfoApi.updateOnItemSave(productId , totalQuantity);

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
        return itemInfoConverter.convertToDtoListWithSaleAmount(itemInfoRepository.findAllByStatusAndStoreInfoAndProductInfo(status , storeId , productInfoId));
    }

    @Override
    public void updateInStockOnSaleTrack(SalesOrderStatus track, long orderId) {

        List<OrderItemInfo> orderItemInfoList = orderItemInfoRepository.findAllByStatusAndOrderInfo(Status.ACTIVE, orderId);

        for (OrderItemInfo orderItemInfo : orderItemInfoList) {

            ItemInfo itemInfo = itemInfoRepository.findById(orderItemInfo.getItemInfo().getId()).orElse(null);

            if (track.equals(SalesOrderStatus.PENDDING)) {

                itemInfo.setInStock(itemInfo.getInStock() - orderItemInfo.getQuantity());

            } else if (track.equals(SalesOrderStatus.CANCEL)) {

                itemInfo.setInStock(itemInfo.getInStock() + orderItemInfo.getQuantity());
            }

            stockInfoApi.updateOnItemUpdateInStockOnSaleTrack(track , itemInfo.getProductInfo().getId() , orderItemInfo.getQuantity());

            itemInfoRepository.save(itemInfo);

        }
    }

    @Override
    public void updateInStockOnSaleReturn(long orderReturnIdId) {

        List<ReturnItemInfoDomain> returnItemInfoList = returnItemInfoRepository.findForLiteByStatusAndStoreInfo_Id(Status.ACTIVE, orderReturnIdId);

        for (ReturnItemInfoDomain returnItemInfo : returnItemInfoList) {

            ItemInfo itemInfo = itemInfoRepository.findById(returnItemInfo.getItemInfoId());

            itemInfo.setInStock(itemInfo.getInStock() + returnItemInfo.getQuantity());

            stockInfoApi.updateOnItemUpdateInStockOnSaleReturn(itemInfo.getProductInfo().getId() , returnItemInfo.getQuantity());

            itemInfoRepository.save(itemInfo);

        }
    }

    private Pageable createPageRequest(int page , int size , String properties , Sort.Direction direction) {

        return PageRequest.of(page, size, Sort.by(direction, properties));
    }

    @Override
    public List<ItemDomain> search(Status status, String term, int page, int size, long storeId) {

        Pageable pageable = createPageRequest(page,size ,"id" , Sort.Direction.DESC);

        return itemInfoRepository.search(term , status , storeId , pageable);
    }
}
