package com.inventory.core.api.impl;

import com.inventory.core.api.iapi.ISubcategoryInfoApi;
import com.inventory.core.model.converter.SubCategoryInfoConverter;
import com.inventory.core.model.dto.SubCategoryInfoDTO;
import com.inventory.core.model.entity.SubCategoryInfo;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.model.repository.SubCategoryInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubCategoryInfoApi implements ISubcategoryInfoApi {

    @Autowired
    private SubCategoryInfoConverter subCategoryInfoConverter;

    @Autowired
    private SubCategoryInfoRepository subCategoryInfoRepository;

    @Override
    public SubCategoryInfoDTO save(SubCategoryInfoDTO subCategoryInfoDTO) {

        SubCategoryInfo subCategoryInfo = subCategoryInfoConverter.convertToEntity(subCategoryInfoDTO);

        subCategoryInfo.setStatus(Status.ACTIVE);

        return subCategoryInfoConverter.convertToDto(subCategoryInfoRepository.save(subCategoryInfo));
    }

    @Override
    public SubCategoryInfoDTO update(SubCategoryInfoDTO subCategoryInfoDTO) {

        SubCategoryInfo subCategoryInfo = subCategoryInfoRepository.findById(subCategoryInfoDTO.getSubCategoryId());

        subCategoryInfo = subCategoryInfoConverter.copyConvertToEntity(subCategoryInfoDTO,subCategoryInfo);

        return subCategoryInfoConverter.convertToDto(subCategoryInfoRepository.save(subCategoryInfo));
    }

    @Override
    public void delete(long subCategoryId) {

        SubCategoryInfo subCategoryInfo = subCategoryInfoRepository.findById(subCategoryId);

        subCategoryInfo.setStatus(Status.DELETED);

        subCategoryInfoRepository.save(subCategoryInfo);
    }

    @Override
    public SubCategoryInfoDTO show(long subCategoryId, long storeId, Status status) {
        return subCategoryInfoConverter.convertToDto(subCategoryInfoRepository.findByIdAndStatusAndStoreInfo(subCategoryId,status,storeId));
    }

    @Override
    public List<SubCategoryInfoDTO> list(Status status, long storeId) {
        return subCategoryInfoConverter.convertToDtoList(subCategoryInfoRepository.findAllByStatusAndStoreInfo(status,storeId));
    }

    @Override
    public List<SubCategoryInfoDTO> getAllByStatusAndStoreInfoAndCagegoryInfo(Status status, long storeId, long categoryId) {
        return subCategoryInfoConverter.convertToDtoList(subCategoryInfoRepository.findAllByStatusAndStoreInfoAndCategoryInfo(status , storeId , categoryId));
    }

    @Override
    public SubCategoryInfoDTO getSubCategoryByNameAndStoreAndStatus(String subCategoryName, long storeId, Status status) {
        return subCategoryInfoConverter.convertToDto(subCategoryInfoRepository.findByNameAndStatusAndStoreInfo(subCategoryName,status,storeId));
    }

}
