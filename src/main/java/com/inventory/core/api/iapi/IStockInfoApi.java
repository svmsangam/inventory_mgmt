package com.inventory.core.api.iapi;

/**
 * Created by dhiraj on 8/24/17.
 */
public interface IStockInfoApi {

    void updateOnItemSave(long productId , int quanity);
}
