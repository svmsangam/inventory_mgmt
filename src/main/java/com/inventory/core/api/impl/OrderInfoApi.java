package com.inventory.core.api.impl;

import com.inventory.core.api.iapi.IInvoiceInfoApi;
import com.inventory.core.api.iapi.IItemInfoApi;
import com.inventory.core.api.iapi.IOrderInfoApi;
import com.inventory.core.api.iapi.IOrderItemInfoApi;
import com.inventory.core.model.converter.OrderInfoConverter;
import com.inventory.core.model.dto.OrderInfoDTO;
import com.inventory.core.model.entity.CodeGenerator;
import com.inventory.core.model.entity.OrderInfo;
import com.inventory.core.model.entity.StoreInfo;
import com.inventory.core.model.enumconstant.*;
import com.inventory.core.model.repository.CodeGeneratorRepository;
import com.inventory.core.model.repository.InvoiceInfoRepository;
import com.inventory.core.model.repository.OrderInfoRepository;
import com.inventory.core.model.repository.StoreInfoRepository;
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
    
    @Autowired
    private CodeGeneratorRepository codeGeneratorRepository;

    @Autowired
    private StoreInfoRepository storeInfoRepository;

    @Autowired
    private IOrderItemInfoApi orderItemInfoApi;

    @Autowired
    private IItemInfoApi itemInfoApi;

    @Autowired
    private IInvoiceInfoApi invoiceInfoApi;

    @Autowired
    private InvoiceInfoRepository invoiceInfoRepository;

    @Override
    public OrderInfoDTO save(OrderInfoDTO orderInfoDTO) {

        OrderInfo orderInfo = orderInfoConverter.convertToEntity(orderInfoDTO);

        orderInfo.setStatus(Status.ACTIVE);
        orderInfo.setPurchaseTrack(PurchaseOrderStatus.PENDING);
        orderInfo.setSaleTrack(SalesOrderStatus.PENDDING);
        orderInfo.setOrderType(OrderType.Sale);

        orderInfo = orderInfoRepository.save(orderInfo);

        orderInfoDTO.setOrderId(orderInfo.getId());

        orderInfo.setTotalAmount(orderItemInfoApi.save(orderInfoDTO));

        orderInfo.setGrandTotal(orderInfo.getTotalAmount() + orderInfo.getTotalAmount() * orderInfo.getTax() / 100);

        itemInfoApi.updateInStockOnSaleTrack(SalesOrderStatus.PENDDING , orderInfo.getId());

        return orderInfoConverter.convertToDto(orderInfoRepository.save(orderInfo));
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

    @Override
    public String generatOrderNumber(long storeId) {

        Long count = codeGeneratorRepository.findByStoreAndNumberStatus(storeId , NumberStatus.Order);

        if (count == null | 0 == count){
            CodeGenerator codeGenerator = new CodeGenerator();

            StoreInfo store = storeInfoRepository.findOne(storeId);

            String prefix = "O" + store.getName().substring(0 , 2).toUpperCase();

            codeGenerator.setStoreInfo(store);
            codeGenerator.setNumber(100001);
            codeGenerator.setNumberStatus(NumberStatus.Order);
            codeGenerator.setPrefix(prefix);

            codeGenerator = codeGeneratorRepository.save(codeGenerator);

            return codeGenerator.getPrefix() + "-" + codeGenerator.getNumber();

        } else {

            StoreInfo store = storeInfoRepository.findOne(storeId);

            long number = codeGeneratorRepository.findFirstByStoreInfoAndNumberStatusOrderByIdDesc(store, NumberStatus.Order).getNumber();

            CodeGenerator codeGenerator = new CodeGenerator();


            String prefix = "O" + store.getName().substring(0 , 2).toUpperCase();

            codeGenerator.setStoreInfo(store);
            codeGenerator.setNumber(number + 1);
            codeGenerator.setNumberStatus(NumberStatus.Order);
            codeGenerator.setPrefix(prefix);

            codeGenerator = codeGeneratorRepository.save(codeGenerator);

            return codeGenerator.getPrefix() + "-" + codeGenerator.getNumber();

        }
    }

    @Override
    public OrderInfoDTO updateSaleTrack(long orderId, SalesOrderStatus track , long createdById) {

        OrderInfo orderInfo = orderInfoRepository.findOne(orderId);

        orderInfo.setSaleTrack(track);

        orderInfo = orderInfoRepository.save(orderInfo);

        if (track.equals(SalesOrderStatus.CANCEL)){

            itemInfoApi.updateInStockOnSaleTrack(SalesOrderStatus.CANCEL , orderId);
        }

        if (track.equals(SalesOrderStatus.SHIPPED) && invoiceInfoRepository.findByStatusAndStoreInfoAndAndOrderInfo(Status.ACTIVE , orderInfo.getStoreInfo().getId() , orderId) == null){
            invoiceInfoApi.save(orderId , createdById);
        }

        return orderInfoConverter.convertToDto(orderInfo);
    }
}
