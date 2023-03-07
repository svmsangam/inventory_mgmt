package com.inventory.core.api.impl;

import com.inventory.core.api.iapi.*;
import com.inventory.core.model.converter.InvoiceInfoConverter;
import com.inventory.core.model.dto.*;
import com.inventory.core.model.entity.*;
import com.inventory.core.model.enumconstant.*;
import com.inventory.core.model.repository.*;
import com.inventory.core.model.specification.InvoiceSpecification;
import com.inventory.web.util.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by dhiraj on 9/13/17.
 */
@Service
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

    @Autowired
    private IOrderReturnInfoApi orderReturnInfoApi;

    @Autowired
    private ILoggerApi loggerApi;

    @Autowired
    private IAccountInfoApi accountInfoApi;

    @Autowired
    private IStoreInvoiceApi storeInvoiceApi;

    @Override
    public double getTotalAmountByStoreInfoAndStatus(long storeInfoId, Status status) {

        Double amount = invoiceInfoRepository.findTotalAmountByStoreAndStatus(storeInfoId, status);

        if (amount == null) {
            return 0;
        }

        return amount;
    }

    @Override
    public double getToDayTotalAmountByStoreInfoAndStatus(long storeInfoId, Status status) {

        Double amount = invoiceInfoRepository.findToDayTotalAmountByStoreAndStatus(storeInfoId, status);

        if (amount == null) {
            return 0;
        }

        return amount;
    }

    @Override
    public String generatInvoiceNumber(long storeId) {

        FiscalYearInfo fiscalYearInfo = fiscalYearInfoRepository.findByStatusAndStoreInfoAndSelected(Status.ACTIVE, storeId, true);

        long count = codeGeneratorRepository.findByStoreAndNumberStatusAndFiscalYearInfo(storeId, NumberStatus.Invoice, fiscalYearInfo.getId());

        if (0 == count) {
            CodeGenerator codeGenerator = new CodeGenerator();

            StoreInfo store = storeInfoRepository.findById(storeId);

            String prefix = "I" + store.getName().substring(0, 2).toUpperCase();

            codeGenerator.setStoreInfo(store);
            codeGenerator.setNumber(100001);
            codeGenerator.setNumberStatus(NumberStatus.Invoice);
            codeGenerator.setPrefix(prefix);
            codeGenerator.setFiscalYearInfo(fiscalYearInfo);

            codeGenerator = codeGeneratorRepository.save(codeGenerator);

            return codeGenerator.getPrefix() + "-" + codeGenerator.getNumber();

        } else {

            StoreInfo store = storeInfoRepository.findById(storeId);

            long number = codeGeneratorRepository.findFirstByStoreInfo_IdAndNumberStatusAndFiscalYearInfo_IdOrderByIdDesc(storeId, NumberStatus.Invoice, fiscalYearInfo.getId()).getNumber();

            CodeGenerator codeGenerator = new CodeGenerator();


            String prefix = "I" + store.getName().substring(0, 2).toUpperCase();

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
    @Transactional
    public InvoiceInfoDTO save(long orderInfoId, long createdById) {

        InvoiceInfo invoiceInfo = invoiceInfoConverter.convertToEntity(orderInfoId, createdById);

        invoiceInfo.setInvoiceNo(generatInvoiceNumber(invoiceInfo.getStoreInfo().getId()));

        invoiceInfo = invoiceInfoRepository.save(invoiceInfo);

        accountInfoApi.addCreditAmount(invoiceInfo.getOrderInfo().getClientInfo().getId(), AccountAssociateType.CUSTOMER, BigDecimal.valueOf(invoiceInfo.getTotalAmount()));

        ledgerInfoApi.save(invoiceInfo.getId());

        storeInvoiceApi.save(invoiceInfo.getStoreInfo().getId());

        return invoiceInfoConverter.convertToDto(invoiceInfo);
    }

    @Override
    @Transactional
    public PaymentInfoDTO savePayment(PaymentInfoDTO paymentInfoDTO){
        paymentInfoDTO = paymentInfoApi.save(paymentInfoDTO);
        if (PaymentMethod.CASH.equals(paymentInfoDTO.getReceivedPayment().getPaymentMethod())) {
            ledgerInfoApi.saveOnPayment(paymentInfoDTO.getPaymentInfoId());
            updateOnPayment(paymentInfoDTO.getPaymentInfoId());
        } else if (PaymentMethod.CHEQUE.equals(paymentInfoDTO.getReceivedPayment().getPaymentMethod())) {
            updateVersion(paymentInfoDTO.getInvoiceInfoId());
        }

        return paymentInfoDTO;
    }

    @Override
    @Lock(LockModeType.OPTIMISTIC)
    public long collectCheque(long paymentInfoId) {
        long invoiceId = paymentInfoApi.collectCheque(paymentInfoId);
        updateOnPayment(paymentInfoId);
        return invoiceId;
    }

    @Override
    @Transactional
    public InvoiceInfoDTO saveQuickSale(PaymentInfoDTO paymentInfoDTO) {

        OrderInfo orderInfo = orderInfoRepository.findById(paymentInfoDTO.getOrderInfoId()).orElseThrow();

        orderInfo.setStatus(Status.ACTIVE);

        orderInfoRepository.save(orderInfo);

        InvoiceInfoDTO invoiceInfo = save(paymentInfoDTO.getOrderInfoId(), paymentInfoDTO.getCreatedById());

        paymentInfoDTO.setInvoiceInfoId(invoiceInfo.getInvoiceId());

        paymentInfoApi.save(paymentInfoDTO);

        //accountInfoApi.addCreditAmount(orderInfo.getClientInfo().getId(), AccountAssociateType.CUSTOMER, BigDecimal.valueOf(invoiceInfo.getTotalAmount()));
        //accountInfoApi.addDebitAmount(orderInfo.getClientInfo().getId(), AccountAssociateType.CUSTOMER, BigDecimal.valueOf(paymentInfoDTO.getReceivedPayment().getAmount()));

        return invoiceInfo;
    }

    @Override
    @Transactional
    public void updateOnPayment(long paymentInfoId) {

        try {

            PaymentInfo paymentInfo = paymentInfoRepository.findById(paymentInfoId);

            InvoiceInfo invoiceInfo = paymentInfo.getInvoiceInfo();

            invoiceInfo.setReceivableAmount(invoiceInfo.getReceivableAmount() - paymentInfo.getReceivedPayment().getAmount());

            invoiceInfoRepository.save(invoiceInfo);

            accountInfoApi.addDebitAmount(invoiceInfo.getOrderInfo().getClientInfo().getId(), AccountAssociateType.CUSTOMER, BigDecimal.valueOf(paymentInfo.getReceivedPayment().getAmount()));

        }catch (Exception e){
            LoggerUtil.logException(this.getClass() , e);
            throw e;
        }

    }

    @Override
    @Transactional
    public void updateVersion(long invoiceId) {

        InvoiceInfo invoiceInfo = invoiceInfoRepository.findById(invoiceId);

        invoiceInfo.setVersion(invoiceInfo.getVersion() + 1);

        invoiceInfoRepository.save(invoiceInfo);
    }

    @Override
    public List<InvoiceInfoDTO> filter(InvoiceFilterDTO filterDTO) {

        InvoiceSpecification specification = new InvoiceSpecification(filterDTO);

        Pageable pageable = createPageRequest(filterDTO.getPageNo(), filterDTO.getSize(), "id", Sort.Direction.DESC);

        return invoiceInfoConverter.convertPageToDtoList(invoiceInfoRepository.findAll(specification, pageable));
    }

    @Override
    public long filterCount(InvoiceFilterDTO filterDTO) {

        InvoiceSpecification specification = new InvoiceSpecification(filterDTO);

        Long count = invoiceInfoRepository.count(specification);

        if (count == null) {
            return 0;
        }

        return count;
    }

    @Override
    public InvoiceInfoDTO show(long invoiceId, long storeId, Status status) {
        return invoiceInfoConverter.convertToDto(invoiceInfoRepository.findByIdAndStatusAndStoreInfo(invoiceId, status, storeId));
    }

    @Override
    public InvoiceInfoDTO getByOrderIdAndStatusAndStoreId(long orderId, Status status, long storeId) {
        return invoiceInfoConverter.convertToDto(invoiceInfoRepository.findByStatusAndStoreInfoAndOrderInfo(status, storeId, orderId));
    }

    private Pageable createPageRequest(int page, int size, String properties, Sort.Direction direction) {

        return PageRequest.of(page, size, Sort.by(direction, properties));
    }

    @Override
    public List<InvoiceInfoDTO> list(Status status, long storeId, int page, int size) {

        Pageable pageable = createPageRequest(page, size, "id", Sort.Direction.DESC);

        return invoiceInfoConverter.convertToDtoList(invoiceInfoRepository.findAllByStatusAndStoreInfo(status, storeId, pageable));
    }

    @Override
    public List<InvoiceListDTO> listToJson(Status status, long storeId, int page, int size) {

        Pageable pageable = createPageRequest(page, size, "id", Sort.Direction.DESC);

        return invoiceInfoConverter.convertToJsonList(invoiceInfoRepository.findAllByStatusAndStoreInfo(status, storeId, pageable));
    }

    @Override
    public List<InvoiceInfoDTO> listTopReceivable(Status status, long storeId, int page, int size) {

        Pageable pageable = createPageRequest(page, size, "receivableAmount", Sort.Direction.DESC);

        return invoiceInfoConverter.convertToDtoList(invoiceInfoRepository.findAllTopReceivableByStatusAndStoreInfo(status, storeId, pageable));
    }

    @Override
    public long countlist(Status status, long storeId) {
        return invoiceInfoRepository.countAllByStatusAndStoreInfo(status, storeId);
    }

    @Override
    public double getTotalReceivableByStoreInfoAndStatus(long storeInfoId, Status status) {

        Double amount = invoiceInfoRepository.findTotalReceivableByStoreInfoAndStatus(storeInfoId, status);

        if (amount == null) {
            return 0;
        }
        return amount;
    }

    public List<Double> getTotalSellOfYearByStore(long storeId, int year) {

        List<Double> totalSales = new ArrayList<Double>();

        for (int i = 0; i < 12; i++) {
            totalSales.add(0.0);
        }

        List<Object[]> objectList = invoiceInfoRepository.findTotalSellOfYearByStore(storeId, year);

        for (Object[] object : objectList) {

            System.out.println("month " + (String) object[1]);

            if ((Double) object[0] == null) {
                totalSales.add(Integer.parseInt((String) object[1]) - 1, 0.0);
            } else {
                totalSales.add(Integer.parseInt((String) object[1]) - 1, limitPrecision((Double) object[0], 2));
            }
        }

        return totalSales;
    }

    @Override
    public List<InvoiceInfoDTO> getAllByStatusAndBuyerAndStoreInfo(Status status, long clientId, long storeId, int page, int size) {

        Pageable pageable = createPageRequest(page, size, "id", Sort.Direction.DESC);

        return invoiceInfoConverter.convertToDtoList(invoiceInfoRepository.findAllByStatusAndBuyerAndStoreInfo(status, clientId, storeId, pageable));
    }

    @Override
    public List<InvoiceInfoDTO> getAllReceivableByStatusAndBuyerAndStoreInfo(Status status, long clientId, long storeId, int page, int size) {
        Pageable pageable = createPageRequest(page, size, "id", Sort.Direction.DESC);
        return invoiceInfoConverter.convertToDtoList(invoiceInfoRepository.findAllReceivableByStatusAndStoreInfoAndCustomer(status , storeId , clientId , pageable));
    }

    @Override
    public long countAllByStatusAndBuyerAndStoreInfo(Status status, long clientId, long storeId) {
        return invoiceInfoRepository.countAllByStatusAndBuyerAndStoreInfo(status, clientId, storeId);
    }

    @Override
    public List<InvoiceInfoDTO> getAllByStatusAndStoreInfoAndInvoiceDateBetween(Status status, long storeId, Date from, Date to, int page, int size) {

        Pageable pageable = createPageRequest(page, size, "id", Sort.Direction.DESC);

        return invoiceInfoConverter.convertToDtoList(invoiceInfoRepository.findAllByStatusAndStoreInfoAndInvoiceDateBetween(status, storeId, from, to, pageable));
    }

    @Override
    public long countAllByStatusAndStoreInfoAndInvoiceDateBetween(Status status, long storeId, Date from, Date to) {

        Long count = invoiceInfoRepository.countAllByStatusAndStoreInfoAndInvoiceDateBetween(status, storeId, from, to);

        if (count == null) {
            return 0;
        }

        return count;
    }

    @Override
    @Transactional
    public void cancel(long invoiceId, String note, long createdById) {

        InvoiceInfo invoiceInfo = invoiceInfoRepository.findById(invoiceId);

        invoiceInfo.setCanceled(true);
        invoiceInfo.setCancelNote(note);

        invoiceInfo = invoiceInfoRepository.save(invoiceInfo);

        loggerApi.save(invoiceId, LogType.Invoice_Print, invoiceInfo.getStoreInfo().getId(), createdById, "invoice canceled");

        orderReturnInfoApi.cancelInvoice(invoiceId, createdById);

        invoiceInfo.setReceivableAmount(0.0);

        invoiceInfo.setTotalAmount(0.0);

        invoiceInfoRepository.save(invoiceInfo);

        paymentInfoApi.refundOnInvoiceCancel(invoiceId, createdById);
    }

    private double limitPrecision(Double dblAsString, int maxDigitsAfterDecimal) {

        int multiplier = (int) Math.pow(10, maxDigitsAfterDecimal);
        double truncated = (double) ((long) ((dblAsString) * multiplier)) / multiplier;
        System.out.println(dblAsString + " ==> " + truncated);
        return truncated;
    }

}
