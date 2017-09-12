package com.inventory.core.api.iapi;

import com.inventory.core.model.enumconstant.SalesOrderStatus;

/**
 * Created by dhiraj on 8/24/17.
 */
public interface IStockInfoApi {

    void updateOnItemSave(long productId , int quanity);

    void updateOnItemUpdateInStockOnSaleTrack(SalesOrderStatus track , long productId , int quanity);

    void saveOnProductSave(long productId);
}
