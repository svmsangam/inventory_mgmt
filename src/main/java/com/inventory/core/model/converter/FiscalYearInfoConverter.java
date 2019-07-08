package com.inventory.core.model.converter;

import com.inventory.core.model.dto.FiscalYearInfoDTO;
import com.inventory.core.model.entity.FiscalYearInfo;
import com.inventory.core.model.repository.StoreInfoRepository;
import com.inventory.core.util.IConvertable;
import com.inventory.core.util.IListConvertable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by dhiraj on 12/24/17.
 */
@Service
public class FiscalYearInfoConverter implements IConvertable<FiscalYearInfo , FiscalYearInfoDTO> , IListConvertable<FiscalYearInfo , FiscalYearInfoDTO>{

    @Autowired
    private StoreInfoRepository storeInfoRepository;

    @Override
    public FiscalYearInfo convertToEntity(FiscalYearInfoDTO dto) {
        return copyConvertToEntity(dto , new FiscalYearInfo());
    }

    @Override
    public FiscalYearInfoDTO convertToDto(FiscalYearInfo entity) {

        if (entity == null) {
            return null;
        }

        FiscalYearInfoDTO dto = new FiscalYearInfoDTO();

        dto.setFiscalYearInfoId(entity.getId());
        dto.setClosingDate(entity.getClosingDate());
        dto.setOpennigDate(entity.getOpennigDate());
        dto.setSelected(entity.isSelected());
        dto.setStatus(entity.getStatus());
        dto.setTitle(entity.getTitle());

        return dto;
    }

    @Override
    public FiscalYearInfo copyConvertToEntity(FiscalYearInfoDTO dto, FiscalYearInfo entity) {

        if (entity == null | dto == null) {
            return null;
        }

        entity.setClosingDate(dto.getClosingDate());
        entity.setOpennigDate(dto.getOpennigDate());
        entity.setTitle(dto.getTitle());
        entity.setStoreInfo(storeInfoRepository.findById(dto.getStoreInfoId()));

        return entity;
    }

    @Override
    public List<FiscalYearInfoDTO> convertToDtoList(List<FiscalYearInfo> entities) {
        return entities.parallelStream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<FiscalYearInfo> convertToEntityList(List<FiscalYearInfoDTO> dtoList) {
        return dtoList.parallelStream().filter(Objects::nonNull).map(this::convertToEntity).collect(Collectors.toList());
    }
}
