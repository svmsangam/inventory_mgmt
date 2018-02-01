package com.inventory.core.api.impl;

import com.inventory.core.api.iapi.*;
import com.inventory.core.model.converter.OrderReturnInfoConverter;
import com.inventory.core.model.dto.OrderInfoDTO;
import com.inventory.core.model.dto.OrderReturnInfoDTO;
import com.inventory.core.model.entity.FiscalYearInfo;
import com.inventory.core.model.entity.InvoiceInfo;
import com.inventory.core.model.entity.OrderInfo;
import com.inventory.core.model.entity.OrderReturnInfo;
import com.inventory.core.model.enumconstant.*;
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

    @Autowired
    private ILoggerApi loggerApi;

    @Override
    public OrderReturnInfoDTO save(OrderReturnInfoDTO orderReturnInfoDTO) {

        OrderReturnInfo orderReturnInfo = orderReturnInfoConverter.convertToEntity(orderReturnInfoDTO);

        orderReturnInfo.setStatus(Status.ACTIVE);

        FiscalYearInfo currentFiscalYear = fiscalYearInfoRepository.findByStatusAndStoreInfoAndSelected(Status.ACTIVE , orderReturnInfo.getStoreInfo().getId() , true);

        orderReturnInfo.setFiscalYearInfo(currentFiscalYear);

        orderReturnInfo = orderReturnInfoRepository.save(orderReturnInfo);

        orderReturnInfoDTO.setOrderReturnInfoId(orderReturnInfo.getId());

        orderReturnInfo.setTotalAmount(returnItemInfoApi.save(orderReturnInfoDTO));

        orderReturnInfo.setTotalAmount(orderReturnInfo.getTotalAmount() + ( orderReturnInfo.getTotalAmount() * orderReturnInfo.getOrderInfo().getTax() /100));

        orderReturnInfoRepository.save(orderReturnInfo);

        itemInfoApi.updateInStockOnSaleReturn(orderReturnInfo.getId());

        ledgerInfoApi.saveOnOrderReturn(orderReturnInfo.getId());

        InvoiceInfo invoiceInfo = invoiceInfoRepository.findByStatusAndStoreInfoAndOrderInfo(Status.ACTIVE , orderReturnInfo.getStoreInfo().getId() , orderReturnInfo.getOrderInfo().getId());

        if (invoiceInfo.getReceivableAmount() - orderReturnInfo.getTotalAmount() < 0){

            ledgerInfoApi.savePaymentOnSaleReturn(invoiceInfo.getId() , orderReturnInfo.getTotalAmount() - invoiceInfo.getReceivableAmount());

            invoiceInfo.setReceivableAmount(orderReturnInfo.getTotalAmount() - invoiceInfo.getReceivableAmount());

            invoiceInfoRepository.save(invoiceInfo);

            loggerApi.save(invoiceInfo.getId() , LogType.Invoice_Print , invoiceInfo.getStoreInfo().getId() , orderReturnInfo.getCreatedBy().getId() , "invoice updated due to of sales return");

        }

        return orderReturnInfoConverter.convertToDto(orderReturnInfo);
    }

    @Override
    public void cancelInvoice(long invoiceId , long createdById  ){

        OrderReturnInfo orderReturnInfo = orderReturnInfoConverter.convertToEntity(invoiceId , createdById);

        orderReturnInfo.setStatus(Status.ACTIVE);

        FiscalYearInfo currentFiscalYear = fiscalYearInfoRepository.findByStatusAndStoreInfoAndSelected(Status.ACTIVE , orderReturnInfo.getStoreInfo().getId() , true);

        orderReturnInfo.setFiscalYearInfo(currentFiscalYear);

        orderReturnInfo = orderReturnInfoRepository.save(orderReturnInfo);

        orderReturnInfo.setTotalAmount(returnItemInfoApi.save(orderReturnInfo.getOrderInfo().getId() , orderReturnInfo.getId()));

        orderReturnInfo.setTotalAmount(orderReturnInfo.getTotalAmount() + ( orderReturnInfo.getTotalAmount() * orderReturnInfo.getOrderInfo().getTax() /100));

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
