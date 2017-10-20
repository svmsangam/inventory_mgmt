package com.inventory.core.api.impl;

import com.inventory.core.api.iapi.ILedgerInfoApi;
import com.inventory.core.model.converter.LedgerInfoConverter;
import com.inventory.core.model.dto.LedgerInfoDTO;
import com.inventory.core.model.entity.LedgerInfo;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.model.repository.LedgerInfoRepository;
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

    private Pageable createPageRequest(int page , int size , String properties , Sort.Direction direction) {

        return new PageRequest(page, size, new Sort(direction, properties));
    }

    @Override
    public List<LedgerInfoDTO> list(Status status, long storeId, int page, int size) {

        Pageable pageable = createPageRequest(page,size ,"id" , Sort.Direction.DESC);

        return ledgerInfoConverter.convertToDtoList(ledgerInfoRepository.findAllByStatusAndStoreInfo(status , storeId , pageable));
    }

    @Override
    public long countAllByStatusAndStore(Status status, long storeId) {
        return ledgerInfoRepository.countAllByStatusAndStoreInfo(status , storeId);
    }

    @Override
    public List<LedgerInfoDTO> filter(Status status, long storeId, long accountId, Date from, Date to, int page, int size) {

        Pageable pageable = createPageRequest(page,size ,"id" , Sort.Direction.DESC);

        return ledgerInfoConverter.convertToDtoList(ledgerInfoRepository.filter(status , storeId , accountId , from , to , pageable));
    }

    @Override
    public Long filterCount(Status status, long storeId, long accountId, Date from, Date to) {

        Long count = ledgerInfoRepository.filterCount(status , storeId , accountId , from , to);

        if (count == null) {
            return null;
        }

        return count;
    }
}
