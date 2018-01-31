package com.inventory.core.api.impl;

import com.inventory.core.api.iapi.IReturnItemInfoApi;
import com.inventory.core.model.converter.ReturnItemInfoConverter;
import com.inventory.core.model.dto.OrderInfoDTO;
import com.inventory.core.model.dto.OrderItemInfoDTO;
import com.inventory.core.model.dto.OrderReturnInfoDTO;
import com.inventory.core.model.dto.ReturnItemInfoDTO;
import com.inventory.core.model.entity.OrderItemInfo;
import com.inventory.core.model.entity.OrderReturnInfo;
import com.inventory.core.model.entity.ReturnItemInfo;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.model.repository.OrderItemInfoRepository;
import com.inventory.core.model.repository.OrderReturnInfoRepository;
import com.inventory.core.model.repository.ReturnItemInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dhiraj on 1/29/18.
 */
@Service
@Transactional
public class ReturnItemInfoApi implements IReturnItemInfoApi {

    @Autowired
    private ReturnItemInfoRepository returnItemInfoRepository;

    @Autowired
    private OrderItemInfoRepository orderItemInfoRepository;

    @Autowired
    private ReturnItemInfoConverter returnItemInfoConverter;

    @Autowired
    private OrderReturnInfoRepository orderReturnInfoRepository;

    private ReturnItemInfoDTO save(ReturnItemInfoDTO returnItemInfoDTO) {

        ReturnItemInfo returnItemInfo = returnItemInfoConverter.convertToEntity(returnItemInfoDTO);

        returnItemInfo.setStatus(Status.ACTIVE);

        returnItemInfo = returnItemInfoRepository.save(returnItemInfo);

        return returnItemInfoConverter.convertToDto(returnItemInfo);
    }

    @Override
    public double save(OrderReturnInfoDTO orderReturnInfoDTO) {

        double amount = 0.0;

        List<ReturnItemInfo> returnItemInfoList = new ArrayList<>();

        OrderReturnInfo orderReturnInfo = orderReturnInfoRepository.findById(orderReturnInfoDTO.getOrderReturnInfoId());

        for (int i = 0 ; i < orderReturnInfoDTO.getOrderItemInfoIdList().size() ; i ++){

            ReturnItemInfo entity = new ReturnItemInfo();

            OrderItemInfo orderItemInfo = orderItemInfoRepository.findById(orderReturnInfoDTO.getOrderItemInfoIdList().get(i));

            entity.setOrderItemInfo(orderItemInfo);
            entity.setOrderReturnInfo(orderReturnInfo);
            entity.setQuantity(orderReturnInfoDTO.getReturnQuantityList().get(i));
            entity.setRate(entity.getOrderItemInfo().getRate());
            entity.setTotalAmount(entity.getQuantity() * entity.getRate());
            entity.setTotalAmount(entity.getTotalAmount() - (entity.getTotalAmount() * entity.getOrderItemInfo().getDiscount() / 100));
            entity.setStatus(Status.ACTIVE);

            amount = amount + entity.getTotalAmount();

            returnItemInfoList.add(entity);

            orderItemInfo.setReturnQuantity(orderItemInfo.getReturnQuantity() + entity.getQuantity());

            orderItemInfoRepository.save(orderItemInfo);

        }

        returnItemInfoRepository.save(returnItemInfoList);

        return amount;
    }

    @Override
    public double save(long orderId, long orderReturnId) {

        double amount = 0.0;

        List<OrderItemInfo> orderItemInfoList = orderItemInfoRepository.findAllByStatusAndOrderInfo(Status.ACTIVE , orderId);

        OrderReturnInfo orderReturnInfo = orderReturnInfoRepository.findById(orderReturnId);

        List<ReturnItemInfo> returnItemInfoList = new ArrayList<>();

        for (OrderItemInfo orderItemInfo : orderItemInfoList){

            ReturnItemInfo entity = new ReturnItemInfo();

            entity.setOrderItemInfo(orderItemInfo);
            entity.setOrderReturnInfo(orderReturnInfo);
            entity.setQuantity(orderItemInfo.getQuantity() - orderItemInfo.getReturnQuantity());
            entity.setRate(orderItemInfo.getRate());
            entity.setTotalAmount(entity.getQuantity() * entity.getRate());
            entity.setTotalAmount(entity.getTotalAmount() - (entity.getTotalAmount() * entity.getOrderItemInfo().getDiscount() / 100));
            entity.setStatus(Status.ACTIVE);

            amount = amount + entity.getTotalAmount();

            returnItemInfoList.add(entity);

            orderItemInfo.setReturnQuantity(orderItemInfo.getReturnQuantity() + entity.getQuantity());

            orderItemInfoRepository.save(orderItemInfo);

        }

        returnItemInfoRepository.save(returnItemInfoList);

        return amount;
    }

    @Override
    public List<ReturnItemInfoDTO> list(Status status, long returnOrderInfoId) {
        return returnItemInfoConverter.convertToDtoList(returnItemInfoRepository.findAllByStatusAndOrderReturnInfo_Id(status , returnOrderInfoId));
    }
}
