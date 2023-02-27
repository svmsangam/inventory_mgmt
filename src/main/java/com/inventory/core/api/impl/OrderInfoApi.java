package com.inventory.core.api.impl;

import com.inventory.core.api.iapi.IInvoiceInfoApi;
import com.inventory.core.api.iapi.IItemInfoApi;
import com.inventory.core.api.iapi.IOrderInfoApi;
import com.inventory.core.api.iapi.IOrderItemInfoApi;
import com.inventory.core.model.converter.OrderInfoConverter;
import com.inventory.core.model.dto.OrderFilterDTO;
import com.inventory.core.model.dto.OrderInfoDTO;
import com.inventory.core.model.entity.CodeGenerator;
import com.inventory.core.model.entity.FiscalYearInfo;
import com.inventory.core.model.entity.OrderInfo;
import com.inventory.core.model.entity.StoreInfo;
import com.inventory.core.model.enumconstant.*;
import com.inventory.core.model.repository.*;
import com.inventory.core.model.specification.OrderSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dhiraj on 8/27/17.
 */

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

    @Autowired
    private FiscalYearInfoRepository fiscalYearInfoRepository;

    @Override
    @Transactional
    public OrderInfoDTO save(OrderInfoDTO orderInfoDTO) {

        orderInfoDTO.setStatus(Status.ACTIVE);
        orderInfoDTO.setPurchaseTrack(PurchaseOrderStatus.PENDING);
        orderInfoDTO.setSaleTrack(SalesOrderStatus.PENDDING);
        orderInfoDTO.setOrderType(OrderType.Sale);

        OrderInfo orderInfo = saveOrder(orderInfoDTO);

        orderInfoDTO.setOrderId(orderInfo.getId());

        orderInfo.setTotalAmount(orderItemInfoApi.save(orderInfoDTO));

        orderInfo.setGrandTotal(orderInfo.getTotalAmount() + orderInfo.getTotalAmount() * orderInfo.getTax() / 100);

        itemInfoApi.updateInStockOnSaleTrack(SalesOrderStatus.PENDDING , orderInfo.getId());

        return orderInfoConverter.convertToDto(orderInfoRepository.save(orderInfo));
    }

    @Override
    @Transactional
    public OrderInfoDTO saveQuickSale(OrderInfoDTO orderInfoDTO){

        orderInfoDTO.setStatus(Status.INACTIVE);
        orderInfoDTO.setPurchaseTrack(PurchaseOrderStatus.RECEIVED);
        orderInfoDTO.setSaleTrack(SalesOrderStatus.DELIVERED);
        orderInfoDTO.setOrderType(OrderType.Sale);

        OrderInfo orderInfo = saveOrder(orderInfoDTO);

        orderInfoDTO.setOrderId(orderInfo.getId());
        orderInfo.setTotalAmount(orderItemInfoApi.save(orderInfoDTO));
        orderInfo.setGrandTotal(orderInfo.getTotalAmount() + orderInfo.getTotalAmount() * orderInfo.getTax() / 100);

        itemInfoApi.updateInStockOnSaleTrack(SalesOrderStatus.PENDDING , orderInfo.getId());

        return orderInfoConverter.convertToDto(orderInfoRepository.save(orderInfo));
    }

    private OrderInfo saveOrder(OrderInfoDTO orderInfoDTO) {

        OrderInfo orderInfo = orderInfoConverter.convertToEntity(orderInfoDTO);

        orderInfo.setStatus(orderInfoDTO.getStatus());
        orderInfo.setPurchaseTrack(orderInfoDTO.getPurchaseTrack());
        orderInfo.setSaleTrack(orderInfoDTO.getSaleTrack());
        orderInfo.setOrderType(orderInfoDTO.getOrderType());

        FiscalYearInfo currentFiscalYear = fiscalYearInfoRepository.findByStatusAndStoreInfoAndSelected(Status.ACTIVE , orderInfo.getStoreInfo().getId() , true);

        orderInfo.setFiscalYearInfo(currentFiscalYear);

        return orderInfoRepository.save(orderInfo);
    }

    @Override
    public OrderInfoDTO show(Status status, long orderId, long storeId) {
        return orderInfoConverter.convertToDto(orderInfoRepository.findByIdAndStatusAndStoreInfo(orderId , status , storeId));
    }

    private Pageable createPageRequest(int page , int size , String properties , Sort.Direction direction) {

        return PageRequest.of(page, size, Sort.by(direction, properties));
    }

    @Override
    public List<OrderInfoDTO> listSale(Status status, long storeId, int page, int size) {

        Pageable pageable = createPageRequest(page,size ,"id" , Sort.Direction.DESC);

        return orderInfoConverter.convertToDtoList(orderInfoRepository.findAllByStatusAndStoreInfoAndOrderType(status , storeId  , OrderType.Sale, pageable));
    }

    @Override
    public List<OrderInfoDTO> getAllOrderListOfCustomer(Status status, long storeId, long customerId, int page, int size) {
        Pageable pageable = createPageRequest(page,size ,"id" , Sort.Direction.DESC);
        return orderInfoConverter.convertToDtoList(orderInfoRepository.findAllByStatusAndStoreInfoAndOrderTypeAndAndClientInfo(status , storeId , OrderType.Sale,  customerId , pageable));
    }

    @Override
    public List<OrderInfoDTO> listTopSale(Status status, long storeId, int page, int size) {

        Pageable pageable = createPageRequest(page,size ,"grandTotal" , Sort.Direction.DESC);

        List<SalesOrderStatus> trackList = new ArrayList<>();

        trackList.add(SalesOrderStatus.PENDDING);
        trackList.add(SalesOrderStatus.ACCEPTED);
        trackList.add(SalesOrderStatus.PACKED);
        trackList.add(SalesOrderStatus.SHIPPED);

        return orderInfoConverter.convertToDtoList(orderInfoRepository.findAllByStatusAndStoreInfoAndOrderTypeAndSaleTrackIn(status , storeId  , OrderType.Sale, trackList, pageable));
    }

    @Override
    public long countListSale(Status status, long storeId) {

        Long count = orderInfoRepository.countAllByStatusAndStoreInfoAndOrderType(status , storeId , OrderType.Sale);

        if (count == null) {
            return 0;
        }

        return count;
    }

    @Override
    public long countSaleByStatusAndStoreInfoAndSaleTrack(Status status, long storeId, SalesOrderStatus track) {
        return orderInfoRepository.countAllSaleByStatusAndStoreInfoAndOrderTypeAndSaleTrack(status , storeId , OrderType.Sale , track);
    }

    @Override
    @Lock(LockModeType.OPTIMISTIC)
    public String generatOrderNumber(long storeId) {

        FiscalYearInfo fiscalYearInfo = fiscalYearInfoRepository.findByStatusAndStoreInfoAndSelected(Status.ACTIVE , storeId , true);

        Long count = codeGeneratorRepository.findByStoreAndNumberStatusAndFiscalYearInfo(storeId , NumberStatus.Order , fiscalYearInfo.getId());

        if (count == null | 0 == count){
            CodeGenerator codeGenerator = new CodeGenerator();

            StoreInfo store = storeInfoRepository.findById(storeId);

            String prefix = "O" + store.getName().substring(0 , 2).toUpperCase();

            codeGenerator.setStoreInfo(store);
            codeGenerator.setNumber(100001);
            codeGenerator.setNumberStatus(NumberStatus.Order);
            codeGenerator.setPrefix(prefix);
            codeGenerator.setFiscalYearInfo(fiscalYearInfo);

            codeGenerator = codeGeneratorRepository.save(codeGenerator);

            return codeGenerator.getPrefix() + "-" + codeGenerator.getNumber();

        } else {

            StoreInfo store = storeInfoRepository.findById(storeId);

            long number = codeGeneratorRepository.findFirstByStoreInfo_IdAndNumberStatusAndFiscalYearInfo_IdOrderByIdDesc(storeId, NumberStatus.Order , fiscalYearInfo.getId()).getNumber();

            CodeGenerator codeGenerator = new CodeGenerator();


            String prefix = "O" + store.getName().substring(0 , 2).toUpperCase();

            codeGenerator.setStoreInfo(store);
            codeGenerator.setNumber(number + 1);
            codeGenerator.setNumberStatus(NumberStatus.Order);
            codeGenerator.setPrefix(prefix);
            codeGenerator.setFiscalYearInfo(fiscalYearInfo);

            codeGenerator = codeGeneratorRepository.save(codeGenerator);

            String newNumber = codeGenerator.getPrefix() + "-" + codeGenerator.getNumber();

            return newNumber;

        }
    }

    @Override
    @Lock(LockModeType.OPTIMISTIC)
    public void updateAmount(long orderId){

        OrderInfo orderInfo = orderInfoRepository.findById(orderId).orElseThrow();

        orderInfo.setTotalAmount( orderItemInfoApi.getTotalAmountByStatusAndOrderInfo(Status.ACTIVE , orderId));

        orderInfo.setGrandTotal(orderInfo.getTotalAmount() + orderInfo.getTotalAmount() * orderInfo.getTax() / 100);

        orderInfoRepository.save(orderInfo);


    }

    @Override
    @Lock(LockModeType.OPTIMISTIC)
    @Transactional
    public OrderInfoDTO updateSaleTrack(long orderId, SalesOrderStatus track , long createdById) {

        OrderInfo orderInfo = orderInfoRepository.findById(orderId).orElseThrow();

        orderInfo.setSaleTrack(track);

        orderInfo = orderInfoRepository.save(orderInfo);

        if (track.equals(SalesOrderStatus.CANCEL)){

            itemInfoApi.updateInStockOnSaleTrack(SalesOrderStatus.CANCEL , orderId);
        }

        if (track.equals(SalesOrderStatus.SHIPPED) && invoiceInfoRepository.findByStatusAndStoreInfoAndOrderInfo(Status.ACTIVE , orderInfo.getStoreInfo().getId() , orderId) == null){
            invoiceInfoApi.save(orderId , createdById);
        }

        return orderInfoConverter.convertToDto(orderInfo);
    }

    @Override
    @Transactional
    public OrderInfoDTO cancelQuickSale(long orderId) {

        itemInfoApi.updateInStockOnSaleTrack(SalesOrderStatus.CANCEL , orderId);

        OrderInfo orderInfo = orderInfoRepository.findById(orderId).orElseThrow();

        orderInfo.setStatus(Status.DELETED);
        orderInfo.setSaleTrack(SalesOrderStatus.CANCEL);

        orderInfoRepository.save(orderInfo);

        return orderInfoConverter.convertToDto(orderInfo);
    }

    @Override
    public List<OrderInfoDTO> filter(OrderFilterDTO filterDTO) {

        OrderSpecification specification = new OrderSpecification(filterDTO);

        Pageable pageable = createPageRequest(filterDTO.getPageNo(),filterDTO.getSize() ,"id" , Sort.Direction.DESC);

        return orderInfoConverter.convertPageToDtoList(orderInfoRepository.findAll(specification , pageable));
    }

    @Override
    public long filterCount(OrderFilterDTO filterDTO) {

        OrderSpecification specification = new OrderSpecification(filterDTO);

        Long count = orderInfoRepository.count(specification );

        if (count == null){
            return 0;
        }

        return count;
    }

}
