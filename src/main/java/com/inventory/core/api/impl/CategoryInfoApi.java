package com.inventory.core.api.impl;

import com.inventory.core.api.iapi.ICategoryInfoApi;
import com.inventory.core.model.converter.CategoryInfoConverter;
import com.inventory.core.model.dto.CategoryInfoDTO;
import com.inventory.core.model.entity.CategoryInfo;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.model.repository.CategoryInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryInfoApi implements ICategoryInfoApi {

    @Autowired
    private CategoryInfoConverter categoryInfoConverter;

    @Autowired
    private CategoryInfoRepository categoryInfoRepository;

    @Override
    public CategoryInfoDTO save(CategoryInfoDTO categoryInfoDTO){

        CategoryInfo categoryInfo = categoryInfoConverter.convertToEntity(categoryInfoDTO);
        categoryInfo = categoryInfoConverter.copyConvertToEntity(categoryInfoDTO, categoryInfo);

        return categoryInfoConverter.convertToDto(categoryInfoRepository.save(categoryInfo));
    }

    @Override
    public CategoryInfoDTO update(CategoryInfoDTO categoryInfoDTO) {

        CategoryInfo categoryInfo = categoryInfoRepository.findById(categoryInfoDTO.getCategoryId());
        categoryInfo = categoryInfoConverter.copyConvertToEntity(categoryInfoDTO,categoryInfo);

        return categoryInfoConverter.convertToDto(categoryInfoRepository.save(categoryInfo));
    }

    @Override
    public void delete(long categoryId){

        CategoryInfo categoryInfo = categoryInfoRepository.findById(categoryId);
        categoryInfo.setStatus(Status.DELETED);

        categoryInfoRepository.save(categoryInfo);
    }

    @Override
    public CategoryInfoDTO show(long categoryId, long storeId, Status status) {
        return categoryInfoConverter.convertToDto(categoryInfoRepository.findByIdAndStatusAndStoreInfo(categoryId,status,storeId));
    }

    @Override
    public List<CategoryInfoDTO> list(Status status, long storeId) {
        return categoryInfoConverter.convertToDtoList(categoryInfoRepository.findAllByStatusAndStoreInfo(status,storeId));
    }

    @Override
    public CategoryInfoDTO getCategoryByNameAndStoreAndStatus(String categoryName, long storeId, Status status) {
        return categoryInfoConverter.convertToDto(categoryInfoRepository.findByNameAndStatusAndStoreInfo(categoryName,status,storeId));
    }

    @Override
    public long categoryCount(Status status, long storeId) {

        Long count = categoryInfoRepository.countAllByStatusAndStoreInfo(status,storeId);
        if (count == null)
            return 0;

        return count;
    }
}
