package com.inventory.core.api.iapi;

import com.inventory.core.model.dto.CategoryInfoDTO;
import com.inventory.core.model.enumconstant.Status;

import java.util.List;

public interface ICategoryInfoApi {

    CategoryInfoDTO save(CategoryInfoDTO categoryInfoDTO);

    CategoryInfoDTO update(CategoryInfoDTO categoryInfoDTO);

    void delete(long categoryId);

    CategoryInfoDTO show(long categoryId , long storeId , Status status);

    List<CategoryInfoDTO> list(Status status , long storeId);

    CategoryInfoDTO getCategoryByNameAndStoreAndStatus(String categoryName , long storeId , Status status);

    long categoryCount(Status status  , long storeId);
}
