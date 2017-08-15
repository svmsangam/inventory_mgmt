package com.inventory.core.api.iapi;

import com.inventory.core.model.dto.ProductInfoDTO;
import com.inventory.core.model.enumconstant.Status;

import java.util.List;

/**
 * Created by dhiraj on 8/15/17.
 */
public interface IProductInfoApi {

    ProductInfoDTO save(ProductInfoDTO productInfoDTO);

    ProductInfoDTO update(ProductInfoDTO productInfoDTO);

    void delete(long productInfoId);

    ProductInfoDTO getByNameAndStoreAndStatus(String productName , long storeId , Status status);

    ProductInfoDTO getByIdAndStoreAndStatus(long productInfoId , long storeId , Status status);

    List<ProductInfoDTO> list(Status status , long storeId);
}
