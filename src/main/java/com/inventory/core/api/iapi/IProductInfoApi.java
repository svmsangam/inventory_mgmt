package com.inventory.core.api.iapi;

import java.util.List;

import com.inventory.core.model.dto.ProductFilterDTO;
import com.inventory.core.model.dto.ProductInfoDTO;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.model.liteentity.CategoryDomain;

/**
 * Created by dhiraj on 8/15/17.
 */
public interface IProductInfoApi {

	ProductInfoDTO save(ProductInfoDTO productInfoDTO);

	ProductInfoDTO update(ProductInfoDTO productInfoDTO);

	void delete(long productInfoId);

	ProductInfoDTO getByIdAndStoreAndStatus(long productInfoId, long storeId, Status status);

	List<CategoryDomain> getAllCategory(long productInfoCategoryId, long storeId, Status status,
			List<CategoryDomain> categoryDomainList);

	List<ProductInfoDTO> list(Status status, long storeId);

	List<ProductInfoDTO> list(Status status, long storeId, int page, int size);

	long countList(Status status, long storeId);

	double getTotalCosting(long productId);

	List<ProductInfoDTO> filter(ProductFilterDTO filterDTO);

	long filterCount(ProductFilterDTO filterDTO);
}
