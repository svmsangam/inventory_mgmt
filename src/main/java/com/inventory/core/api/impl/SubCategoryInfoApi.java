package com.inventory.core.api.impl;

import com.inventory.core.api.iapi.ISubcategoryInfoApi;
import com.inventory.core.model.converter.SubCategoryInfoConverter;
import com.inventory.core.model.dto.SubCategoryInfoDTO;
import com.inventory.core.model.entity.SubCategoryInfo;
import com.inventory.core.model.enumconstant.CategoryType;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.model.liteentity.CategoryDomain;
import com.inventory.core.model.repository.StoreInfoRepository;
import com.inventory.core.model.repository.SubCategoryInfoRepository;
import com.inventory.core.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubCategoryInfoApi implements ISubcategoryInfoApi {

    @Autowired
    private SubCategoryInfoConverter subCategoryInfoConverter;

    @Autowired
    private SubCategoryInfoRepository subCategoryInfoRepository;

    @Autowired
    private StoreInfoRepository storeInfoRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public SubCategoryInfoDTO save(SubCategoryInfoDTO subCategoryInfoDTO) {

        SubCategoryInfo subCategoryInfo = subCategoryInfoConverter.convertToEntity(subCategoryInfoDTO);

        subCategoryInfo.setStatus(Status.ACTIVE);

        subCategoryInfo.setCreatedBy(userRepository.findById(subCategoryInfoDTO.getCreatedById()));
        subCategoryInfo.setStoreInfo(storeInfoRepository.findById(subCategoryInfoDTO.getStoreInfoId()));

        return subCategoryInfoConverter.convertToDto(subCategoryInfoRepository.save(subCategoryInfo));
    }

    @Override
    @Transactional
    public SubCategoryInfoDTO update(SubCategoryInfoDTO subCategoryInfoDTO) {

        SubCategoryInfo subCategoryInfo = subCategoryInfoRepository.findById(subCategoryInfoDTO.getSubCategoryId()).orElse(null);

        subCategoryInfo = subCategoryInfoConverter.copyConvertToEntity(subCategoryInfoDTO, subCategoryInfo);

        subCategoryInfoRepository.save(subCategoryInfo);

        return subCategoryInfoConverter.convertToDto(subCategoryInfo);
    }

    @Override
    @Transactional
    public void delete(long subCategoryId) {

        SubCategoryInfo subCategoryInfo = subCategoryInfoRepository.findById(subCategoryId);

        subCategoryInfo.setStatus(Status.DELETED);

        subCategoryInfoRepository.save(subCategoryInfo);
    }

    @Override
    public SubCategoryInfoDTO show(long subCategoryId, long storeId, Status status) {
        return subCategoryInfoConverter.convertToDto(subCategoryInfoRepository.findByIdAndStatusAndStoreInfo_Id(subCategoryId, status, storeId));
    }

    @Override
    public CategoryDomain showLite(long categoryId, long storeId, Status status) {
        return subCategoryInfoRepository.findLiteCategoryByIdAndStatusAndStoreInfo_Id(categoryId, status, storeId);
    }

    @Override
    public CategoryDomain getLiteCategory(long subCategoryId, long storeId, Status status) {
        return subCategoryInfoRepository.findLiteCategoryByIdAndStatusAndStoreInfo_Id(subCategoryId, status, storeId);
    }

    @Override
    public List<CategoryDomain> list(Status status, long storeId) {
        return subCategoryInfoRepository.findAllByStatusAndStoreInfo(status, storeId);
    }

    @Override
    public List<SubCategoryInfoDTO> getAllByStatusAndStoreInfoAndCagegoryInfo(Status status, long storeId, long categoryId) {
        return subCategoryInfoConverter.convertToDtoList(subCategoryInfoRepository.findAllChildByStatusAndStoreInfoAndCategoryInfo(status, storeId, categoryId));
    }

    @Override
    public SubCategoryInfoDTO getSubCategoryByNameAndStoreAndStatus(String subCategoryName, long storeId, Status status) {
        return subCategoryInfoConverter.convertToDto(subCategoryInfoRepository.findByNameAndStatusAndStoreInfo(subCategoryName, status, storeId));
    }

    @Override
    public List<CategoryDomain> getTree(Status status, long storeId){

        List<CategoryDomain> parentList = subCategoryInfoRepository.findAllByStatusAndStoreInfoAndType(status , storeId , CategoryType.Parent);

        List<CategoryDomain> childList = subCategoryInfoRepository.findAllByStatusAndStoreInfoAndType(status , storeId , CategoryType.Child);

        CategoryTreeService tree = new CategoryTreeService(childList);

        return tree.getTree(parentList);
    }
}
