package com.inventory.core.api.impl;

import com.inventory.core.api.iapi.IStockInfoApi;
import com.inventory.core.model.entity.StockInfo;
import com.inventory.core.model.repository.StockInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by dhiraj on 8/24/17.
 */
@Transactional
@Service
public class StockInfoApi implements IStockInfoApi{

    @Autowired
    private StockInfoRepository stockInfoRepository;

    @Override
    public void updateOnItemSave(long productId, int quanity) {

        StockInfo stockInfo = stockInfoRepository.findByProductInfo(productId);

        stockInfo.setInStock(stockInfo.getInStock() + quanity);
        stockInfo.setQuantity(stockInfo.getQuantity() + quanity);

        stockInfoRepository.save(stockInfo);
    }
}
