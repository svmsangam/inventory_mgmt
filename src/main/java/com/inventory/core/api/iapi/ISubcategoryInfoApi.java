package com.inventory.core.api.iapi;

import com.inventory.core.model.dto.SubCategoryInfoDTO;
import com.inventory.core.model.enumconstant.Status;

import java.util.List;

public interface ISubcategoryInfoApi {

    SubCategoryInfoDTO save(SubCategoryInfoDTO subCategoryInfoDTO);

    SubCategoryInfoDTO update(SubCategoryInfoDTO subCategoryInfoDTO);

    void delete(long subCategoryId);

    SubCategoryInfoDTO show(long subCategoryId , long storeId , Status status);

    List<SubCategoryInfoDTO> list(Status status , long storeId);

    SubCategoryInfoDTO getSubCategoryByNameAndStoreAndStatus(String subCategoryName , long storeId , Status status);

}
