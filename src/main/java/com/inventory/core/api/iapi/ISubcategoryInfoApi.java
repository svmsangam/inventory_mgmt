package com.inventory.core.api.iapi;

import com.inventory.core.model.dto.SubCategoryInfoDTO;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.model.liteentity.CategoryDomain;

import java.util.List;

public interface ISubcategoryInfoApi {

    SubCategoryInfoDTO save(SubCategoryInfoDTO subCategoryInfoDTO);

    SubCategoryInfoDTO update(SubCategoryInfoDTO subCategoryInfoDTO);

    void delete(long subCategoryId);

    SubCategoryInfoDTO show(long subCategoryId, long storeId, Status status);

    CategoryDomain getLiteCategory(long subCategoryId, long storeId, Status status);

    List<CategoryDomain> list(Status status, long storeId);

    List<SubCategoryInfoDTO> getAllByStatusAndStoreInfoAndCagegoryInfo(Status status, long storeId, long categoryId);

    SubCategoryInfoDTO getSubCategoryByNameAndStoreAndStatus(String subCategoryName, long storeId, Status status);

    List<CategoryDomain> getTree(Status status, long storeId);
}
