package com.inventory.core.api.iapi;

import com.inventory.core.model.entity.StoreInvoice;

public interface IStoreInvoiceApi {
    StoreInvoice save(long storeId);

    int getCountOfThisMonth(long storeId);

    boolean verifyForCurrentMonth(long storeId);
}
