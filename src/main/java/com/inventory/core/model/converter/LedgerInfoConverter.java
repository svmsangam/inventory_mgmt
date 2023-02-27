package com.inventory.core.model.converter;

import com.inventory.core.model.dto.LedgerFilter;
import com.inventory.core.model.dto.LedgerFilterDTO;
import com.inventory.core.model.dto.LedgerInfoDTO;
import com.inventory.core.model.entity.InvoiceInfo;
import com.inventory.core.model.entity.LedgerInfo;
import com.inventory.core.model.entity.OrderReturnInfo;
import com.inventory.core.model.entity.PaymentInfo;
import com.inventory.core.model.enumconstant.*;
import com.inventory.core.model.repository.*;
import com.inventory.core.util.IConvertable;
import com.inventory.core.util.IListConvertable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by dhiraj on 9/16/17.
 */
@Service
public class LedgerInfoConverter implements IListConvertable<LedgerInfo, LedgerInfoDTO>, IConvertable<LedgerInfo, LedgerInfoDTO> {

    @Autowired
    private AccountInfoConverter accountInfoConverter;

    @Autowired
    private AccountInfoRepository accountInfoRepository;

    @Autowired
    private StoreInfoRepository storeInfoRepository;

    @Autowired
    private InvoiceInfoRepository invoiceInfoRepository;

    @Autowired
    private PaymentInfoRepository paymentInfoRepository;

    @Autowired
    private FiscalYearInfoRepository fiscalYearInfoRepository;

    @Autowired
    private OrderReturnInfoRepository orderReturnInfoRepository;

    @Override
    public LedgerInfo convertToEntity(LedgerInfoDTO dto) {
        return copyConvertToEntity(dto, new LedgerInfo());
    }

    @Override
    public LedgerInfoDTO convertToDto(LedgerInfo entity) {

        if (entity == null) {
            return null;
        }

        LedgerInfoDTO dto = new LedgerInfoDTO();

        dto.setLedgerId(entity.getId());
        dto.setAccountEntryType(entity.getAccountEntryType());
        dto.setAccountId(entity.getAccountInfo().getId());
        dto.setAccountInfo(accountInfoConverter.convertToDto(entity.getAccountInfo()));
        dto.setAmount(entity.getAmount());
        dto.setLedgerEntryType(entity.getLedgerEntryType());
        dto.setRemarks(entity.getRemarks());
        dto.setStatus(entity.getStatus());
        dto.setStoreInfoId(entity.getStoreInfo().getId());
        dto.setDate(entity.getCreated());
        dto.setAccountNo(entity.getAccountInfo().getAcountNumber());
        dto.setFiscalYear(entity.getFiscalYearInfo().getTitle());
        dto.setFiscalYearId(entity.getFiscalYearInfo().getId());


        return dto;
    }

    @Override
    public LedgerInfo copyConvertToEntity(LedgerInfoDTO dto, LedgerInfo entity) {

        if (entity == null | dto == null) {
            return null;
        }

        entity.setAccountEntryType(dto.getAccountEntryType());
        entity.setAccountInfo(accountInfoRepository.findById(dto.getAccountId()).orElse(null));
        entity.setAmount(dto.getAmount());
        entity.setLedgerEntryType(dto.getLedgerEntryType());
        entity.setRemarks(dto.getRemarks());
        entity.setStoreInfo(storeInfoRepository.findById(dto.getStoreInfoId()));
        entity.setFiscalYearInfo(fiscalYearInfoRepository.findByStatusAndStoreInfoAndSelected(Status.ACTIVE, dto.getStoreInfoId(), true));

        return entity;
    }

    @Override
    public List<LedgerInfoDTO> convertToDtoList(List<LedgerInfo> entities) {
        return entities.parallelStream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<LedgerInfo> convertToEntityList(List<LedgerInfoDTO> dtoList) {
        return dtoList.parallelStream().filter(Objects::nonNull).map(this::convertToEntity).collect(Collectors.toList());
    }

    public List<LedgerInfoDTO> convertPageToDtoList(Page<LedgerInfo> entities) {

        List<LedgerInfoDTO> dtoList = new ArrayList<>();

        for (LedgerInfo ledgerInfo : entities) {
            dtoList.add(convertToDto(ledgerInfo));
        }

        return dtoList;
    }

    public LedgerInfo convertInvoiceToDRLedger(long invoiceId) {

        InvoiceInfo invoiceInfo = invoiceInfoRepository.findById(invoiceId);

        LedgerInfo entity = new LedgerInfo();

        entity.setAccountEntryType(AccountEntryType.DEBIT);
        entity.setAccountInfo(accountInfoRepository.findByAssociateIdAndAssociateType(invoiceInfo.getOrderInfo().getClientInfo().getId(), AccountAssociateType.CUSTOMER));
        entity.setAmount(invoiceInfo.getTotalAmount());
        entity.setLedgerEntryType(LedgerEntryType.GOODS);

        if (invoiceInfo.getOrderInfo().getClientInfo().getCompanyName() != null) {
            entity.setRemarks("goods sold to " + invoiceInfo.getOrderInfo().getClientInfo().getCompanyName());
        } else {
            entity.setRemarks("goods sold to " + invoiceInfo.getOrderInfo().getClientInfo().getName());
        }

        entity.setStoreInfo(invoiceInfo.getStoreInfo());
        entity.setStatus(Status.ACTIVE);
        entity.setFiscalYearInfo(fiscalYearInfoRepository.findByStatusAndStoreInfoAndSelected(Status.ACTIVE, invoiceInfo.getStoreInfo().getId(), true));

        return entity;
    }

    public LedgerInfo convertInvoiceToCRLedger(long invoiceId) {

        InvoiceInfo invoiceInfo = invoiceInfoRepository.findById(invoiceId);

        LedgerInfo entity = new LedgerInfo();

        entity.setAccountEntryType(AccountEntryType.CREDIT);
        entity.setAccountInfo(accountInfoRepository.findByAssociateIdAndAssociateType(invoiceInfo.getStoreInfo().getId(), AccountAssociateType.STORE));
        entity.setAmount(invoiceInfo.getTotalAmount());
        entity.setLedgerEntryType(LedgerEntryType.GOODS);

        if (invoiceInfo.getOrderInfo().getClientInfo().getCompanyName() != null) {
            entity.setRemarks("goods sold to " + invoiceInfo.getOrderInfo().getClientInfo().getCompanyName());
        } else {
            entity.setRemarks("goods sold to " + invoiceInfo.getOrderInfo().getClientInfo().getName());
        }

        entity.setStoreInfo(invoiceInfo.getStoreInfo());
        entity.setStatus(Status.ACTIVE);
        entity.setFiscalYearInfo(fiscalYearInfoRepository.findByStatusAndStoreInfoAndSelected(Status.ACTIVE, invoiceInfo.getStoreInfo().getId(), true));

        return entity;
    }


    public LedgerInfo convertPaymentInfoToCRLedger(long paymentInfoId) {

        PaymentInfo paymentInfo = paymentInfoRepository.findById(paymentInfoId);

        LedgerInfo entity = new LedgerInfo();

        entity.setAccountEntryType(AccountEntryType.CREDIT);
        entity.setAccountInfo(accountInfoRepository.findByAssociateIdAndAssociateType(paymentInfo.getInvoiceInfo().getOrderInfo().getClientInfo().getId(), AccountAssociateType.CUSTOMER));
        entity.setAmount(paymentInfo.getReceivedPayment().getAmount());

        LedgerEntryType ledgerEntryType = LedgerEntryType.CASH;

        if (paymentInfo.getReceivedPayment().getPaymentMethod().equals(PaymentMethod.CHEQUE)) {
            ledgerEntryType = LedgerEntryType.CHEQUE;
        }

        entity.setLedgerEntryType(ledgerEntryType);

        if (paymentInfo.getInvoiceInfo().getOrderInfo().getClientInfo().getCompanyName() != null) {
            entity.setRemarks("payment Made By " + paymentInfo.getInvoiceInfo().getOrderInfo().getClientInfo().getCompanyName());
        } else {
            entity.setRemarks("payment Made By " + paymentInfo.getInvoiceInfo().getOrderInfo().getClientInfo().getName());
        }

        entity.setStoreInfo(paymentInfo.getStoreInfo());
        entity.setStatus(Status.ACTIVE);
        entity.setFiscalYearInfo(fiscalYearInfoRepository.findByStatusAndStoreInfoAndSelected(Status.ACTIVE, paymentInfo.getStoreInfo().getId(), true));

        return entity;
    }

    public LedgerInfo convertPaymentInfoToDRLedger(long paymentInfoId) {

        PaymentInfo paymentInfo = paymentInfoRepository.findById(paymentInfoId);

        LedgerInfo entity = new LedgerInfo();

        entity.setAccountEntryType(AccountEntryType.DEBIT);
        entity.setAccountInfo(accountInfoRepository.findByAssociateIdAndAssociateType(paymentInfo.getInvoiceInfo().getOrderInfo().getStoreInfo().getId(), AccountAssociateType.STORE));
        entity.setAmount(paymentInfo.getReceivedPayment().getAmount());

        entity.setLedgerEntryType(LedgerEntryType.CASH);

        if (paymentInfo.getInvoiceInfo().getOrderInfo().getClientInfo().getCompanyName() != null) {
            entity.setRemarks("payment Made By " + paymentInfo.getInvoiceInfo().getOrderInfo().getClientInfo().getCompanyName());
        } else {
            entity.setRemarks("payment Made By " + paymentInfo.getInvoiceInfo().getOrderInfo().getClientInfo().getName());
        }

        entity.setStoreInfo(paymentInfo.getStoreInfo());
        entity.setStatus(Status.ACTIVE);
        entity.setFiscalYearInfo(fiscalYearInfoRepository.findByStatusAndStoreInfoAndSelected(Status.ACTIVE, paymentInfo.getStoreInfo().getId(), true));

        return entity;
    }

    public LedgerInfo convertInvoiceCancelToDRLedger(long invoiceId) {

        InvoiceInfo invoiceInfo = invoiceInfoRepository.findById(invoiceId);

        LedgerInfo entity = new LedgerInfo();

        entity.setAccountEntryType(AccountEntryType.DEBIT);
        entity.setAccountInfo(accountInfoRepository.findByAssociateIdAndAssociateType(invoiceInfo.getOrderInfo().getClientInfo().getId(), AccountAssociateType.CUSTOMER));
        entity.setAmount(invoiceInfo.getTotalAmount() - invoiceInfo.getReceivableAmount());

        LedgerEntryType ledgerEntryType = LedgerEntryType.CASH;

        entity.setLedgerEntryType(ledgerEntryType);

        if (invoiceInfo.getOrderInfo().getClientInfo().getCompanyName() != null) {
            entity.setRemarks("Cash return to " + invoiceInfo.getOrderInfo().getClientInfo().getCompanyName());
        } else {
            entity.setRemarks("Cash return to " + invoiceInfo.getOrderInfo().getClientInfo().getName());
        }

        entity.setStoreInfo(invoiceInfo.getStoreInfo());
        entity.setStatus(Status.ACTIVE);
        entity.setFiscalYearInfo(fiscalYearInfoRepository.findByStatusAndStoreInfoAndSelected(Status.ACTIVE, invoiceInfo.getStoreInfo().getId(), true));

        return entity;
    }

    public LedgerInfo convertInvoiceCancelToCRLedger(long invoiceId) {

        InvoiceInfo invoiceInfo = invoiceInfoRepository.findById(invoiceId);

        LedgerInfo entity = new LedgerInfo();

        entity.setAccountEntryType(AccountEntryType.CREDIT);
        entity.setAccountInfo(accountInfoRepository.findByAssociateIdAndAssociateType(invoiceInfo.getOrderInfo().getStoreInfo().getId(), AccountAssociateType.STORE));
        entity.setAmount(invoiceInfo.getTotalAmount() - invoiceInfo.getReceivableAmount());

        entity.setLedgerEntryType(LedgerEntryType.CASH);

        if (invoiceInfo.getOrderInfo().getClientInfo().getCompanyName() != null) {
            entity.setRemarks("Cash Return to " + invoiceInfo.getOrderInfo().getClientInfo().getCompanyName());
        } else {
            entity.setRemarks("Cash Return to " + invoiceInfo.getOrderInfo().getClientInfo().getName());
        }

        entity.setStoreInfo(invoiceInfo.getStoreInfo());
        entity.setStatus(Status.ACTIVE);
        entity.setFiscalYearInfo(fiscalYearInfoRepository.findByStatusAndStoreInfoAndSelected(Status.ACTIVE, invoiceInfo.getStoreInfo().getId(), true));

        return entity;
    }


    public LedgerInfo convertOrderReturnToDRLedger(long orderReturnId) {

        OrderReturnInfo orderReturnInfo = orderReturnInfoRepository.findById(orderReturnId);

        LedgerInfo entity = new LedgerInfo();

        entity.setAccountEntryType(AccountEntryType.DEBIT);
        entity.setAccountInfo(accountInfoRepository.findByAssociateIdAndAssociateType(orderReturnInfo.getStoreInfo().getId(), AccountAssociateType.STORE));
        entity.setAmount(orderReturnInfo.getTotalAmount());
        entity.setLedgerEntryType(LedgerEntryType.GOODS_RETURN);

        if (orderReturnInfo.getOrderInfo().getClientInfo().getCompanyName() != null) {
            entity.setRemarks("goods return By " + orderReturnInfo.getOrderInfo().getClientInfo().getCompanyName());
        } else {
            entity.setRemarks("goods return By " + orderReturnInfo.getOrderInfo().getClientInfo().getName());
        }

        entity.setStoreInfo(orderReturnInfo.getStoreInfo());
        entity.setStatus(Status.ACTIVE);
        entity.setFiscalYearInfo(fiscalYearInfoRepository.findByStatusAndStoreInfoAndSelected(Status.ACTIVE, orderReturnInfo.getStoreInfo().getId(), true));

        return entity;
    }

    public LedgerInfo convertOrderReturnToCRLedger(long orderReturnId) {

        OrderReturnInfo orderReturnInfo = orderReturnInfoRepository.findById(orderReturnId);

        LedgerInfo entity = new LedgerInfo();

        entity.setAccountEntryType(AccountEntryType.CREDIT);
        entity.setAccountInfo(accountInfoRepository.findByAssociateIdAndAssociateType(orderReturnInfo.getOrderInfo().getClientInfo().getId(), AccountAssociateType.CUSTOMER));
        entity.setAmount(orderReturnInfo.getTotalAmount());
        entity.setLedgerEntryType(LedgerEntryType.GOODS_RETURN);

        if (orderReturnInfo.getOrderInfo().getClientInfo().getCompanyName() != null) {
            entity.setRemarks("goods return By " + orderReturnInfo.getOrderInfo().getClientInfo().getCompanyName());
        } else {
            entity.setRemarks("goods return By " + orderReturnInfo.getOrderInfo().getClientInfo().getName());
        }

        entity.setStoreInfo(orderReturnInfo.getStoreInfo());
        entity.setStatus(Status.ACTIVE);
        entity.setFiscalYearInfo(fiscalYearInfoRepository.findByStatusAndStoreInfoAndSelected(Status.ACTIVE, orderReturnInfo.getStoreInfo().getId(), true));

        return entity;
    }



    public LedgerFilter convertToFilterSpec(LedgerFilterDTO filterDTO) {

        if (filterDTO == null) {
            return null;
        }

        LedgerFilter filter = new LedgerFilter();

        /*filter.setStatus(filterDTO.getStatus());
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
*/
        if (filterDTO.getFrom() != null) {
            /*try {
                filter.setFrom(dateFormat.parse(filterDTO.getFrom()));
            } catch (ParseException e) {
                e.printStackTrace();
            }*/

            filter.setFrom(filterDTO.getFrom());
        }

        if (filterDTO.getTo() != null) {
            /*try {
                filter.setTo(dateFormat.parse(filterDTO.getTo()));
            } catch (ParseException e) {
                e.printStackTrace();
            }*/
            filter.setTo(filterDTO.getTo());
        }

        if (filterDTO.getFiscalYearId() != null) {
            if (filterDTO.getFiscalYearId() > 0) {
                filter.setFiscalYearInfo(fiscalYearInfoRepository.findById(filterDTO.getFiscalYearId()).orElse(null));
            }
        }

        filter.setStoreInfo(storeInfoRepository.findById(filterDTO.getStoreId()));

        if (filterDTO.getAccountId() != null) {

            if (filterDTO.getAccountId() > 0) {
                filter.setAccountInfo(accountInfoRepository.findById(filterDTO.getAccountId()).orElse(null));
            }
        }

        return filter;
    }
}
