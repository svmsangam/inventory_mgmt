package com.inventory.core.api.impl;

import com.inventory.core.api.iapi.IStockInfoApi;
import com.inventory.core.model.entity.StockInfo;
import com.inventory.core.model.enumconstant.SalesOrderStatus;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.model.repository.ProductInfoRepository;
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

    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Override
    public void updateOnItemSave(long productId, int quanity) {

        StockInfo stockInfo = stockInfoRepository.findByProductInfo(productId);

        stockInfo.setInStock(stockInfo.getInStock() + quanity);
        stockInfo.setQuantity(stockInfo.getQuantity() + quanity);

        stockInfoRepository.save(stockInfo);
    }

    @Override
    public void updateOnItemUpdateInStockOnSaleTrack(SalesOrderStatus track , long productId, int quanity) {

        StockInfo stockInfo = stockInfoRepository.findByProductInfo(productId);

        if (track.equals(SalesOrderStatus.PENDDING)) {

            stockInfo.setInStock(stockInfo.getInStock() - quanity);

        } else if (track.equals(SalesOrderStatus.CANCEL)) {

            stockInfo.setInStock(stockInfo.getInStock() + quanity);
        }

        stockInfoRepository.save(stockInfo);
    }

    @Override
    public void saveOnProductSave(long productId) {

        StockInfo stockInfo = new StockInfo();

        stockInfo.setProductInfo(productInfoRepository.findById(productId));
        stockInfo.setQuantity(0);
        stockInfo.setInStock(0);
        stockInfo.setStatus(Status.ACTIVE);

        stockInfoRepository.save(stockInfo);
    }
}
