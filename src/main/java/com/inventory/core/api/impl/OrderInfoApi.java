package com.inventory.core.api.impl;

import com.inventory.core.api.iapi.IOrderInfoApi;
import com.inventory.core.model.converter.OrderInfoConverter;
import com.inventory.core.model.dto.OrderInfoDTO;
import com.inventory.core.model.entity.OrderInfo;
import com.inventory.core.model.enumconstant.PurchaseOrderStatus;
import com.inventory.core.model.enumconstant.SalesOrderStatus;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.model.repository.OrderInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by dhiraj on 8/27/17.
 */
@Transactional
@Service
public class OrderInfoApi implements IOrderInfoApi {

    @Autowired
    private OrderInfoRepository orderInfoRepository;

    @Autowired
    private OrderInfoConverter orderInfoConverter;

    @Override
    public OrderInfoDTO save(OrderInfoDTO orderInfoDTO) {

        OrderInfo orderInfo = orderInfoConverter.convertToEntity(orderInfoDTO);

        orderInfo.setStatus(Status.ACTIVE);
        orderInfo.setPurchaseTrack(PurchaseOrderStatus.PENDDING);
        orderInfo.setSaleTrack(SalesOrderStatus.PENDDING);

        orderInfo = orderInfoRepository.save(orderInfo);

        return orderInfoConverter.convertToDto(orderInfo);
    }

    @Override
    public OrderInfoDTO show(Status status, long orderId, long storeId) {
        return orderInfoConverter.convertToDto(orderInfoRepository.findByIdAndStatusAndStoreInfo(orderId , status , storeId));
    }

    private Pageable createPageRequest(int page , int size , String properties , Sort.Direction direction) {

        return new PageRequest(page, size, new Sort(direction, properties));
    }

    @Override
    public List<OrderInfoDTO> list(Status status, long storeId, int page, int size) {

        Pageable pageable = createPageRequest(page,size ,"id" , Sort.Direction.DESC);

        return orderInfoConverter.convertToDtoList(orderInfoRepository.findAllByStatusAndStoreInfo(status , storeId , pageable));
    }

    @Override
    public long countList(Status status, long storeId) {

        Long count = orderInfoRepository.countAllByStatusAndStoreInfo(status , storeId);

        if (count == null) {
            return 0;
        }

        return count;
    }
}
