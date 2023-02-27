package com.inventory.core.api.impl;

import com.inventory.core.api.iapi.IFiscalYearInfoApi;
import com.inventory.core.api.iapi.ILedgerInfoApi;
import com.inventory.core.model.converter.LedgerInfoConverter;
import com.inventory.core.model.dto.LedgerFilter;
import com.inventory.core.model.dto.LedgerFilterDTO;
import com.inventory.core.model.dto.LedgerInfoDTO;
import com.inventory.core.model.entity.LedgerInfo;
import com.inventory.core.model.enumconstant.AccountEntryType;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.model.repository.LedgerInfoRepository;
import com.inventory.core.model.specification.LedgerSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by dhiraj on 9/16/17.
 */
@Service
@Transactional
public class LedgerInfoApi implements ILedgerInfoApi{

    @Autowired
    private LedgerInfoConverter ledgerInfoConverter;

    @Autowired
    private LedgerInfoRepository ledgerInfoRepository;

    @Autowired
    private IFiscalYearInfoApi fiscalYearInfoApi;

    @Override
    public void save(long invoiceId) {

        LedgerInfo drLedgerInfo = ledgerInfoConverter.convertInvoiceToDRLedger(invoiceId);

        LedgerInfo crLedgerInfo = ledgerInfoConverter.convertInvoiceToCRLedger(invoiceId);

        ledgerInfoRepository.save(drLedgerInfo);

        ledgerInfoRepository.save(crLedgerInfo);
    }

    @Override
    public void saveOnPayment(long paymentInfoId) {

        LedgerInfo drLedgerInfo = ledgerInfoConverter.convertPaymentInfoToDRLedger(paymentInfoId);

        LedgerInfo crLedgerInfo = ledgerInfoConverter.convertPaymentInfoToCRLedger(paymentInfoId);

        ledgerInfoRepository.save(drLedgerInfo);

        ledgerInfoRepository.save(crLedgerInfo);

    }

    @Override
    public void saveOnOrderReturn(long orderReturnId) {

        LedgerInfo drLedgerInfo = ledgerInfoConverter.convertOrderReturnToDRLedger(orderReturnId);

        LedgerInfo crLedgerInfo = ledgerInfoConverter.convertOrderReturnToCRLedger(orderReturnId);

        ledgerInfoRepository.save(drLedgerInfo);

        ledgerInfoRepository.save(crLedgerInfo);

    }

    @Override
    public void saveOnInvoiceCancel(long invoiceId){

        LedgerInfo drLedgerInfo = ledgerInfoConverter.convertInvoiceCancelToDRLedger(invoiceId);

        LedgerInfo crLedgerInfo = ledgerInfoConverter.convertInvoiceCancelToCRLedger(invoiceId);

        ledgerInfoRepository.save(drLedgerInfo);

        ledgerInfoRepository.save(crLedgerInfo);
    }

    private Pageable createPageRequest(int page , int size , String properties , Sort.Direction direction) {

        return PageRequest.of(page, size, Sort.by(direction, properties));
    }

    @Override
    public List<LedgerInfoDTO> list(Status status, long storeId, int page, int size) {

        Pageable pageable = createPageRequest(page,size ,"id" , Sort.Direction.DESC);

        return ledgerInfoConverter.convertToDtoList(ledgerInfoRepository.findAllByStatusAndStoreInfoAndFiscalYearInfo(status , storeId , fiscalYearInfoApi.getCurrentFiscalYearIdByStoreInfo(storeId) , pageable));
    }

    @Override
    public long countAllByStatusAndStore(Status status, long storeId) {
        return ledgerInfoRepository.countAllByStatusAndStoreInfoAndFiscalYearInfo(status , storeId , fiscalYearInfoApi.getCurrentFiscalYearIdByStoreInfo(storeId));
    }

    @Override
    public List<LedgerInfoDTO> filter(LedgerFilterDTO filterDTO) {

       /* LedgerFilter filter = ledgerInfoConverter.convertToFilterSpec(filterDTO);*/

        LedgerSpecification specification = new LedgerSpecification(filterDTO);

        Pageable pageable = createPageRequest(filterDTO.getPage(),filterDTO.getSize() ,"id" , Sort.Direction.DESC);

        return ledgerInfoConverter.convertPageToDtoList(ledgerInfoRepository.findAll(specification , pageable));
    }

    @Override
    public long countFilter(LedgerFilterDTO filterDTO) {

       // LedgerFilter filter = ledgerInfoConverter.convertToFilterSpec(filterDTO);

        LedgerSpecification specification = new LedgerSpecification(filterDTO);

        return ledgerInfoRepository.count(specification);
    }

    @Override
    public List<LedgerInfoDTO> filterReport(Status status, long storeId, long accountId, Date from, Date to) {
        return ledgerInfoConverter.convertToDtoList(ledgerInfoRepository.filter(status , storeId , accountId , from , to ));
    }

    @Override
    public Long filterCount(Status status, long storeId, long accountId, Date from, Date to) {

        Long count = ledgerInfoRepository.filterCount(status , storeId , accountId , from , to);

        if (count == null) {
            return null;
        }

        return count;
    }

    @Override
    public double filterTotalAmount(Status status, long storeId, long accountId, Date from, Date to, AccountEntryType accountEntryType) {

        Double amount = ledgerInfoRepository.filterTotalAmount(status , storeId , accountId , from , to , accountEntryType);

        if (amount == null) {
            return 0;
        }

        return amount;
    }

    @Override
    public double getTotalAmountByStatusAndStoreInfoIdAndAccountInfoAndAccountEntryType(Status status, long storeId, long accountId, AccountEntryType accountEntryType) {

        Double amount = ledgerInfoRepository.findTotalAmountByStatusAndStoreInfoIdAndAccountInfoAndAccountEntryType(status , storeId , accountId , accountEntryType);

        if (amount == null) {
            return 0;
        }

        return amount;
    }

    @Override
    public void savePaymentOnSaleReturn(long invoiceId, double amount) {

        LedgerInfo drLedgerInfo = ledgerInfoConverter.convertInvoiceCancelToDRLedger(invoiceId);

        drLedgerInfo.setAmount(amount);

        LedgerInfo crLedgerInfo = ledgerInfoConverter.convertInvoiceCancelToCRLedger(invoiceId);

        crLedgerInfo.setAmount(amount);

        ledgerInfoRepository.save(drLedgerInfo);

        ledgerInfoRepository.save(crLedgerInfo);
    }
}
