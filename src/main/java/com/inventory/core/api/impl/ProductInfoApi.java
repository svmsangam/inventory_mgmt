package com.inventory.core.api.impl;

import com.inventory.core.api.iapi.IProductInfoApi;
import com.inventory.core.api.iapi.IStockInfoApi;
import com.inventory.core.api.iapi.ISubcategoryInfoApi;
import com.inventory.core.model.converter.ProductInfoConverter;
import com.inventory.core.model.dto.ProductInfoDTO;
import com.inventory.core.model.entity.ProductInfo;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.model.liteentity.CategoryDomain;
import com.inventory.core.model.repository.ItemInfoRepository;
import com.inventory.core.model.repository.ProductInfoRepository;
import com.inventory.core.model.repository.StoreInfoRepository;
import com.inventory.core.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by dhiraj on 8/22/17.
 */
@Transactional
@Service
public class ProductInfoApi implements IProductInfoApi{

    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Autowired
    private ProductInfoConverter productInfoConverter;

    @Autowired
    private IStockInfoApi stockInfoApi;

    @Autowired
    private ItemInfoRepository itemInfoRepository;

    @Autowired
    private ISubcategoryInfoApi subcategoryInfoApi;

    @Autowired
    private StoreInfoRepository storeInfoRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public ProductInfoDTO save(ProductInfoDTO productInfoDTO) {

        ProductInfo productInfo = productInfoConverter.convertToEntity(productInfoDTO);

        productInfo.setStatus(Status.ACTIVE);
        productInfo.setCreatedBy(userRepository.findOne(productInfoDTO.getCreatedById()));
        productInfo.setStoreInfo(storeInfoRepository.findOne(productInfoDTO.getStoreInfoId()));

        productInfo = productInfoRepository.save(productInfo);

        stockInfoApi.saveOnProductSave(productInfo.getId());

        return productInfoConverter.convertToDto(productInfo);
    }

    @Override
    public ProductInfoDTO update(ProductInfoDTO productInfoDTO) {

        ProductInfo productInfo = productInfoRepository.findById(productInfoDTO.getProductId());

        productInfo = productInfoConverter.copyConvertToEntity(productInfoDTO , productInfo);

        return productInfoConverter.convertToDto(productInfoRepository.save(productInfo));
    }

    @Override
    public void delete(long productInfoId) {

        ProductInfo productInfo = productInfoRepository.findById(productInfoId);

        productInfo.setStatus(Status.DELETED);

        productInfoRepository.save(productInfo);
    }

    @Override
    public ProductInfoDTO getByIdAndStoreAndStatus(long productInfoId, long storeId, Status status) {
        return productInfoConverter.convertToDtoDetail(productInfoRepository.findByIdAndStatusAndStoreInfo(productInfoId , status , storeId));
    }

    @Override
    public List<CategoryDomain> getAllCategory(long productInfoCategoryId, long storeId, Status status ,  List<CategoryDomain> categoryDomainList) {

        CategoryDomain categoryDomain = subcategoryInfoApi.getLiteCategory(productInfoCategoryId , storeId , status);

        categoryDomainList.add(categoryDomain);

        if (categoryDomain.getDepth() == 0) {

            Collections.reverse(categoryDomainList);

            return categoryDomainList;
        }

        return getAllCategory(categoryDomain.getParentId() , storeId , status , categoryDomainList);
    }

    @Override
    public List<ProductInfoDTO> list(Status status, long storeId) {
        return productInfoConverter.convertToDtoList(productInfoRepository.findAllByStatusAndStoreInfo(status , storeId));
    }

    private Pageable createPageRequest(int page , int size , String properties , Sort.Direction direction) {

        return new PageRequest(page, size, new Sort(direction, properties));
    }

    @Override
    public List<ProductInfoDTO> list(Status status, long storeId, int page, int size) {

        Pageable pageable = createPageRequest(page,size ,"id" , Sort.Direction.DESC);

        return productInfoConverter.convertToDtoList(productInfoRepository.findAllByStatusAndStoreInfo(status , storeId , pageable));
    }

    @Override
    public long countList(Status status, long storeId) {
        return productInfoRepository.countAllByStatusAndStoreInfo_Id(status , storeId);
    }

    @Override
    public double getTotalCosting(long productId) {

        Object[] amountList = itemInfoRepository.findAllTotalCostByProduct(productId);

        if (amountList == null){
            return 0.0;
        }

        double amount = 0.0;

        for (Object o :amountList){

            if (o != null){
                amount = amount + (Double) o;
            }
        }

        return amount;
    }
}
