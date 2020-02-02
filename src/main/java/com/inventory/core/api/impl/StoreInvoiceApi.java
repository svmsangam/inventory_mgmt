package com.inventory.core.api.impl;

import com.inventory.core.api.iapi.IStoreInvoiceApi;
import com.inventory.core.model.converter.StoreInvoiceConverter;
import com.inventory.core.model.dto.ServiceDTO;
import com.inventory.core.model.entity.StoreInvoice;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.model.repository.StoreInfoRepository;
import com.inventory.core.model.repository.StoreInvoiceRepository;
import com.inventory.core.util.DateParseUtil;
import com.inventory.web.util.AuthenticationUtil;
import com.inventory.web.util.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class StoreInvoiceApi implements IStoreInvoiceApi {

    @Autowired
    private StoreInfoRepository storeInfoRepository;

    @Autowired
    private StoreInvoiceRepository storeInvoiceRepository;

    @Autowired
    private StoreInvoiceConverter storeInvoiceConverter;

    @Override
    public StoreInvoice save(long storeId) {

        StoreInvoice storeInvoice = null;
        try {

            Date currentMonth = DateParseUtil.getMonthDate(new Date());

            storeInvoice = storeInvoiceRepository.findByStoreInfo_IdAndMonth(storeId, currentMonth);

            if (storeInvoice == null) {
                storeInvoice = new StoreInvoice();
                storeInvoice.setMonth(currentMonth);
                storeInvoice.setStatus(Status.ACTIVE);
                storeInvoice.setCount(1);
                storeInvoice.setStoreInfo(storeInfoRepository.findById(storeId));
            } else {
                storeInvoice.setCount(storeInvoice.getCount() + 1);
            }

            storeInvoice = storeInvoiceRepository.save(storeInvoice);
        } catch (Exception e) {
            LoggerUtil.logException(this.getClass(), e);
            throw e;
        }

        return storeInvoice;
    }

    @Override
    public int getCountOfThisMonth(long storeId) {
        try {

            Date currentMonth = DateParseUtil.getMonthDate(new Date());

            StoreInvoice storeInvoice = storeInvoiceRepository.findByStoreInfo_IdAndMonth(storeId, currentMonth);
            if (storeInvoice == null) {
                return 0;
            }
            return storeInvoice.getCount();
        } catch (Exception e) {
            LoggerUtil.logException(this.getClass(), e);
            throw e;
        }
    }

    @Override
    public boolean verifyForCurrentMonth(long storeId) {
        try {

            Date currentMonth = DateParseUtil.getMonthDate(new Date());

            StoreInvoice storeInvoice = storeInvoiceRepository.findByStoreInfo_IdAndMonth(storeId, currentMonth);

            if (storeInvoice == null) {
                storeInvoice = save(storeId);
            }

            return storeInvoice.getCount() < AuthenticationUtil.getOrderLimit();

        } catch (Exception e) {
            LoggerUtil.logException(this.getClass(), e);
            throw e;
        }
    }
}
