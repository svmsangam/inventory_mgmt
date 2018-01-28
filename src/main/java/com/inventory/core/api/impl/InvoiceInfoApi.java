package com.inventory.core.api.impl;

import com.inventory.core.api.iapi.IInvoiceInfoApi;
import com.inventory.core.api.iapi.ILedgerInfoApi;
import com.inventory.core.api.iapi.IPaymentInfoApi;
import com.inventory.core.model.converter.InvoiceInfoConverter;
import com.inventory.core.model.dto.*;
import com.inventory.core.model.entity.*;
import com.inventory.core.model.enumconstant.NumberStatus;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.model.repository.*;
import com.inventory.core.model.specification.InvoiceSpecification;
import com.inventory.core.model.specification.LedgerSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by dhiraj on 9/13/17.
 */
@Service
@Transactional
public class InvoiceInfoApi implements IInvoiceInfoApi {

    @Autowired
    private InvoiceInfoRepository invoiceInfoRepository;

    @Autowired
    private CodeGeneratorRepository codeGeneratorRepository;

    @Autowired
    private StoreInfoRepository storeInfoRepository;

    @Autowired
    private InvoiceInfoConverter invoiceInfoConverter;

    @Autowired
    private FiscalYearInfoRepository fiscalYearInfoRepository;

    @Autowired
    private ILedgerInfoApi ledgerInfoApi;

    @Autowired
    private PaymentInfoRepository paymentInfoRepository;

    @Autowired
    private IPaymentInfoApi paymentInfoApi;

    @Autowired
    private OrderInfoRepository orderInfoRepository;

    @Override
    public double getTotalAmountByStoreInfoAndStatus(long storeInfoId, Status status) {

        Double amount = invoiceInfoRepository.findTotalAmountByStoreAndStatus(storeInfoId , status);

        if (amount == null){
            return 0;
        }

        return amount;
    }

    @Override
    public double getToDayTotalAmountByStoreInfoAndStatus(long storeInfoId, Status status) {

        Double amount = invoiceInfoRepository.findToDayTotalAmountByStoreAndStatus(storeInfoId , status);

        if (amount == null) {
            return 0;
        }

        return amount;
    }

    @Override
    public String generatInvoiceNumber(long storeId) {

        FiscalYearInfo fiscalYearInfo = fiscalYearInfoRepository.findByStatusAndStoreInfoAndSelected(Status.ACTIVE , storeId , true);

        long count = codeGeneratorRepository.findByStoreAndNumberStatusAndFiscalYearInfo(storeId , NumberStatus.Invoice , fiscalYearInfo.getId());

        if (0 == count){
            CodeGenerator codeGenerator = new CodeGenerator();

            StoreInfo store = storeInfoRepository.findOne(storeId);

            String prefix = "I" + store.getName().substring(0 , 2).toUpperCase();

            codeGenerator.setStoreInfo(store);
            codeGenerator.setNumber(100001);
            codeGenerator.setNumberStatus(NumberStatus.Invoice);
            codeGenerator.setPrefix(prefix);
            codeGenerator.setFiscalYearInfo(fiscalYearInfo);

            codeGenerator = codeGeneratorRepository.save(codeGenerator);

            return codeGenerator.getPrefix() + "-" + codeGenerator.getNumber();

        } else {

            StoreInfo store = storeInfoRepository.findOne(storeId);

            long number = codeGeneratorRepository.findFirstByStoreInfo_IdAndNumberStatusAndFiscalYearInfo_IdOrderByIdDesc(storeId, NumberStatus.Invoice , fiscalYearInfo.getId()).getNumber();

            CodeGenerator codeGenerator = new CodeGenerator();


            String prefix = "I" + store.getName().substring(0 , 2).toUpperCase();

            codeGenerator.setStoreInfo(store);
            codeGenerator.setNumber(number + 1);
            codeGenerator.setNumberStatus(NumberStatus.Invoice);
            codeGenerator.setPrefix(prefix);
            codeGenerator.setFiscalYearInfo(fiscalYearInfo);

            codeGenerator = codeGeneratorRepository.save(codeGenerator);

            return codeGenerator.getPrefix() + "-" + codeGenerator.getNumber();

        }
    }

    @Override
    public InvoiceInfoDTO save(long orderInfoId , long createdById) {

        InvoiceInfo invoiceInfo = invoiceInfoConverter.convertToEntity(orderInfoId , createdById);

        invoiceInfo.setInvoiceNo(generatInvoiceNumber(invoiceInfo.getStoreInfo().getId()));

        invoiceInfo = invoiceInfoRepository.save(invoiceInfo);

        ledgerInfoApi.save(invoiceInfo.getId());

        return invoiceInfoConverter.convertToDto(invoiceInfo);
    }

    @Override
    public InvoiceInfoDTO saveQuickSale(PaymentInfoDTO paymentInfoDTO) {

        OrderInfo orderInfo = orderInfoRepository.findOne(paymentInfoDTO.getOrderInfoId());

        orderInfo.setStatus(Status.ACTIVE);

        orderInfoRepository.save(orderInfo);

        InvoiceInfoDTO invoiceInfo = save(paymentInfoDTO.getOrderInfoId() , paymentInfoDTO.getCreatedById());

        paymentInfoDTO.setInvoiceInfoId(invoiceInfo.getInvoiceId());

        paymentInfoApi.save(paymentInfoDTO);

        return invoiceInfo;
    }

    @Override
    public void updateOnPayment(long paymentInfoId) {

        PaymentInfo paymentInfo = paymentInfoRepository.findById(paymentInfoId);

        if (paymentInfo != null) {

            InvoiceInfo invoiceInfo = paymentInfo.getInvoiceInfo();

            invoiceInfo.setReceivableAmount(invoiceInfo.getReceivableAmount() - paymentInfo.getReceivedPayment().getAmount());

            invoiceInfoRepository.save(invoiceInfo);
        }

    }

    @Override
    public void updateVersion(long invoiceId) {

        InvoiceInfo invoiceInfo = invoiceInfoRepository.findById(invoiceId);

        invoiceInfo.setVersion(invoiceInfo.getVersion() + 1);

        invoiceInfoRepository.save(invoiceInfo);
    }

    @Override
    public List<InvoiceInfoDTO> filter(InvoiceFilterDTO filterDTO) {

        InvoiceSpecification specification = new InvoiceSpecification(filterDTO);

        Pageable pageable = createPageRequest(filterDTO.getPageNo(),filterDTO.getSize() ,"id" , Sort.Direction.DESC);

        return invoiceInfoConverter.convertPageToDtoList(invoiceInfoRepository.findAll(specification , pageable));
    }

    @Override
    public long filterCount(InvoiceFilterDTO filterDTO) {

        InvoiceSpecification specification = new InvoiceSpecification(filterDTO);

        Long count = invoiceInfoRepository.count(specification );

        if (count == null){
            return 0;
        }

        return count;
    }

    @Override
    public InvoiceInfoDTO show(long invoiceId, long storeId, Status status) {
        return invoiceInfoConverter.convertToDto(invoiceInfoRepository.findByIdAndStatusAndStoreInfo(invoiceId , status , storeId));
    }

    @Override
    public InvoiceInfoDTO getByOrderIdAndStatusAndStoreId(long orderId, Status status , long storeId) {
        return invoiceInfoConverter.convertToDto(invoiceInfoRepository.findByStatusAndStoreInfoAndAndOrderInfo(status , storeId , orderId));
    }

    private Pageable createPageRequest(int page , int size , String properties , Sort.Direction direction) {

        return new PageRequest(page, size, new Sort(direction, properties));
    }

    @Override
    public List<InvoiceInfoDTO> list(Status status, long storeId, int page, int size) {

        Pageable pageable = createPageRequest(page,size ,"id" , Sort.Direction.DESC);

        return invoiceInfoConverter.convertToDtoList(invoiceInfoRepository.findAllByStatusAndStoreInfo(status , storeId , pageable));
    }

    @Override
    public List<InvoiceInfoDTO> listTopReceivable(Status status, long storeId, int page, int size) {

        Pageable pageable = createPageRequest(page,size ,"receivableAmount" , Sort.Direction.DESC);

        return invoiceInfoConverter.convertToDtoList(invoiceInfoRepository.findAllTopReceivableByStatusAndStoreInfo(status , storeId , pageable));
    }

    @Override
    public long countlist(Status status, long storeId) {
        return invoiceInfoRepository.countAllByStatusAndStoreInfo(status , storeId);
    }

    @Override
    public double getTotalReceivableByStoreInfoAndStatus(long storeInfoId, Status status) {

        Double amount = invoiceInfoRepository.findTotalAmountByStoreAndStatus(storeInfoId , status);

        if (amount == null){
            return 0;
        }
        return amount;
    }

    public List<Double> getTotalSellOfYearByStore(long storeId, String year) {

        List<Double> totalSales = new ArrayList<Double>();

        for(int i= 0 ; i<12 ; i++){
            totalSales.add(0.0);
        }

        List<Object[]> objectList = invoiceInfoRepository.findTotalSellOfYearByStore(storeId , year);

        for(Object[] object : objectList){

            System.out.println("month " + (String) object[1]);

            if ((Double) object[0] == null){
                totalSales.add(Integer.parseInt((String) object[1]) - 1, 0.0);
            }else {
                totalSales.add(Integer.parseInt((String) object[1]) - 1, limitPrecision((Double) object[0] , 2));
            }
        }

        return totalSales;
    }

    @Override
    public List<InvoiceInfoDTO> getAllByStatusAndBuyerAndStoreInfo(Status status, long clientId, long storeId, int page, int size) {

        Pageable pageable = createPageRequest(page,size ,"id" , Sort.Direction.DESC);

        return invoiceInfoConverter.convertToDtoList(invoiceInfoRepository.findAllByStatusAndBuyerAndStoreInfo(status , clientId , storeId , pageable));
    }

    @Override
    public long countAllByStatusAndBuyerAndStoreInfo(Status status, long clientId, long storeId) {
        return invoiceInfoRepository.countAllByStatusAndBuyerAndStoreInfo(status , clientId , storeId);
    }

    @Override
    public List<InvoiceInfoDTO> getAllByStatusAndStoreInfoAndInvoiceDateBetween(Status status, long storeId, Date from, Date to, int page, int size) {

        Pageable pageable = createPageRequest(page,size ,"id" , Sort.Direction.DESC);

        return invoiceInfoConverter.convertToDtoList(invoiceInfoRepository.findAllByStatusAndStoreInfoAndInvoiceDateBetween(status , storeId , from , to , pageable));
    }

    @Override
    public long countAllByStatusAndStoreInfoAndInvoiceDateBetween(Status status, long storeId, Date from, Date to) {

        Long count = invoiceInfoRepository.countAllByStatusAndStoreInfoAndInvoiceDateBetween(status , storeId , from , to);

        if (count == null){
            return 0;
        }

        return count;
    }

    private double limitPrecision(Double dblAsString, int maxDigitsAfterDecimal) {

        int multiplier = (int) Math.pow(10, maxDigitsAfterDecimal);
        double truncated = (double) ((long) ((dblAsString) * multiplier)) / multiplier;
        System.out.println(dblAsString + " ==> " + truncated);
        return truncated;
    }

}
