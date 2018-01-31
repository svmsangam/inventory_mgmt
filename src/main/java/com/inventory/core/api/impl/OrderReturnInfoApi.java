package com.inventory.core.api.impl;

import com.inventory.core.api.iapi.IItemInfoApi;
import com.inventory.core.api.iapi.ILedgerInfoApi;
import com.inventory.core.api.iapi.IOrderReturnInfoApi;
import com.inventory.core.api.iapi.IReturnItemInfoApi;
import com.inventory.core.model.converter.OrderReturnInfoConverter;
import com.inventory.core.model.dto.OrderInfoDTO;
import com.inventory.core.model.dto.OrderReturnInfoDTO;
import com.inventory.core.model.entity.FiscalYearInfo;
import com.inventory.core.model.entity.InvoiceInfo;
import com.inventory.core.model.entity.OrderInfo;
import com.inventory.core.model.entity.OrderReturnInfo;
import com.inventory.core.model.enumconstant.OrderType;
import com.inventory.core.model.enumconstant.PurchaseOrderStatus;
import com.inventory.core.model.enumconstant.SalesOrderStatus;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.model.repository.FiscalYearInfoRepository;
import com.inventory.core.model.repository.InvoiceInfoRepository;
import com.inventory.core.model.repository.OrderReturnInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by dhiraj on 1/17/18.
 */
@Service
@Transactional
public class OrderReturnInfoApi implements IOrderReturnInfoApi {

    @Autowired
    private OrderReturnInfoConverter orderReturnInfoConverter;

    @Autowired
    private OrderReturnInfoRepository orderReturnInfoRepository;

    @Autowired
    private FiscalYearInfoRepository fiscalYearInfoRepository;

    @Autowired
    private IReturnItemInfoApi returnItemInfoApi;

    @Autowired
    private IItemInfoApi itemInfoApi;

    @Autowired
    private InvoiceInfoRepository invoiceInfoRepository;

    @Autowired
    private ILedgerInfoApi ledgerInfoApi;

    @Override
    public OrderReturnInfoDTO save(OrderReturnInfoDTO orderReturnInfoDTO) {

        OrderReturnInfo orderReturnInfo = orderReturnInfoConverter.convertToEntity(orderReturnInfoDTO);

        orderReturnInfo.setStatus(Status.ACTIVE);

        FiscalYearInfo currentFiscalYear = fiscalYearInfoRepository.findByStatusAndStoreInfoAndSelected(Status.ACTIVE , orderReturnInfo.getStoreInfo().getId() , true);

        orderReturnInfo.setFiscalYearInfo(currentFiscalYear);

        orderReturnInfo = orderReturnInfoRepository.save(orderReturnInfo);

        orderReturnInfoDTO.setOrderReturnInfoId(orderReturnInfo.getId());

        orderReturnInfo.setTotalAmount(returnItemInfoApi.save(orderReturnInfoDTO));

        orderReturnInfoRepository.save(orderReturnInfo);

        itemInfoApi.updateInStockOnSaleReturn(orderReturnInfo.getId());

        ledgerInfoApi.saveOnOrderReturn(orderReturnInfo.getId());

        return orderReturnInfoConverter.convertToDto(orderReturnInfo);
    }

    @Override
    public void cancelInvoice(long invoiceId , long createdById  ){

        OrderReturnInfo orderReturnInfo = orderReturnInfoConverter.convertToEntity(invoiceId , createdById);

        orderReturnInfo.setStatus(Status.ACTIVE);

        FiscalYearInfo currentFiscalYear = fiscalYearInfoRepository.findByStatusAndStoreInfoAndSelected(Status.ACTIVE , orderReturnInfo.getStoreInfo().getId() , true);

        orderReturnInfo.setFiscalYearInfo(currentFiscalYear);

        orderReturnInfoRepository.save(orderReturnInfo);

        orderReturnInfo.setTotalAmount(returnItemInfoApi.save(orderReturnInfo.getOrderInfo().getId() , orderReturnInfo.getId()));

        orderReturnInfoRepository.save(orderReturnInfo);

        ledgerInfoApi.saveOnOrderReturn(orderReturnInfo.getId());

        itemInfoApi.updateInStockOnSaleReturn(orderReturnInfo.getId());

        InvoiceInfo invoiceInfo = invoiceInfoRepository.findById(invoiceId);

        if (invoiceInfo.getReceivableAmount() < invoiceInfo.getTotalAmount()){

            ledgerInfoApi.saveOnInvoiceCancel(invoiceId);
        }

    }

    @Override
    public List<OrderReturnInfoDTO> list(Status status, long storeId) {
        return orderReturnInfoConverter.convertToDtoList(orderReturnInfoRepository.findAllByStatusAndStoreInfo_Id(status , storeId));
    }

}
