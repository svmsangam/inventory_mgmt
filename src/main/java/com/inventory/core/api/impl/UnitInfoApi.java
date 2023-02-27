package com.inventory.core.api.impl;

import com.inventory.core.api.iapi.IUnitInfoApi;
import com.inventory.core.model.converter.UnitInfoConverter;
import com.inventory.core.model.dto.UnitInfoDTO;
import com.inventory.core.model.entity.UnitInfo;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.model.repository.UnitInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UnitInfoApi implements IUnitInfoApi {

    @Autowired
    UnitInfoRepository unitInfoRepository;

    @Autowired
    UnitInfoConverter unitInfoConverter;

    @Override
    public UnitInfoDTO save(UnitInfoDTO unitInfoDTO) {

        UnitInfo unitInfo = unitInfoConverter.convertToEntity(unitInfoDTO);

        unitInfo.setStatus(Status.ACTIVE);

        return unitInfoConverter.convertToDto(unitInfoRepository.save(unitInfo));
    }

    @Override
    public UnitInfoDTO update(UnitInfoDTO unitInfoDTO) {

        UnitInfo unitInfo = unitInfoRepository.findById(unitInfoDTO.getUnitId()).orElseThrow();

        unitInfo = unitInfoConverter.copyConvertToEntity(unitInfoDTO, unitInfo);

        return unitInfoConverter.convertToDto(unitInfoRepository.save(unitInfo));
    }

    @Override
    public void delete(long unitInfoId) {

        UnitInfo unitInfo = unitInfoRepository.findById(unitInfoId);

        unitInfo.setStatus(Status.DELETED);

        unitInfoRepository.save(unitInfo);
    }

    @Override
    public UnitInfoDTO getByNameAndStoreAndStatus(String unitName, long storeId, Status status) {
        return unitInfoConverter.convertToDto(unitInfoRepository.findByCodeAndStatusAndStoreInfo(unitName, status, storeId));
    }

    @Override
    public UnitInfoDTO getByIdAndStoreAndStatus(long unitInfoId, long storeId, Status status) {
        return unitInfoConverter.convertToDto(unitInfoRepository.findByIdAndStatusAndStoreInfo(unitInfoId, status, storeId));
    }

    @Override
    public List<UnitInfoDTO> list(Status status, long storeId) {
        return unitInfoConverter.convertToDtoList(unitInfoRepository.findAllByStatusAndStoreInfo(status, storeId));
    }
}
