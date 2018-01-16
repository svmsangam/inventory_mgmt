package com.inventory.core.model.converter;

import com.inventory.core.model.dto.StoreInfoDTO;
import com.inventory.core.model.entity.AccountInfo;
import com.inventory.core.model.entity.StoreInfo;
import com.inventory.core.model.enumconstant.AccountAssociateType;
import com.inventory.core.model.repository.AccountInfoRepository;
import com.inventory.core.model.repository.CityInfoRepository;
import com.inventory.core.util.IConvertable;
import com.inventory.core.util.IListConvertable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by dhiraj on 8/1/17.
 */
@Service
public class StoreInfoConverter implements IConvertable<StoreInfo, StoreInfoDTO>, IListConvertable<StoreInfo, StoreInfoDTO> {

    @Autowired
    private CityInfoRepository cityInfoRepository;

    @Override
    public StoreInfo convertToEntity(StoreInfoDTO dto) {
        return copyConvertToEntity(dto, new StoreInfo());
    }

    @Autowired
    private AccountInfoRepository accountInfoRepository;

    @Override
    public StoreInfoDTO convertToDto(StoreInfo entity) {

        if (entity == null) {

            return null;
        }

        StoreInfoDTO dto = new StoreInfoDTO();

        dto.setStoreId(entity.getId());
        dto.setCityId(entity.getCityInfo().getId());
        dto.setCityName(entity.getCityInfo().getName());
        dto.setContact(entity.getContact());
        dto.setEmail(entity.getEmail());
        dto.setMobileNumber(entity.getMobileNumber());
        dto.setName(entity.getName());
        dto.setPanNumber(entity.getPanNumber());
        dto.setRegNumber(entity.getRegNumber());
        dto.setStatus(entity.getStatus());
        dto.setStreet(entity.getStreet());
        dto.setVersion(entity.getVersion());
        dto.setStateName(entity.getCityInfo().getStateInfo().getName());
        dto.setCountryName(entity.getCityInfo().getStateInfo().getCountryInfo().getName());

        AccountInfo accountInfo = accountInfoRepository.findByAssociateIdAndAssociateType(entity.getId(), AccountAssociateType.STORE);

        if (accountInfo != null) {
            dto.setAccountId(accountInfo.getId());
            dto.setAccountNo(accountInfo.getAcountNumber());
        }

        return dto;
    }

    @Override
    public StoreInfo copyConvertToEntity(StoreInfoDTO dto, StoreInfo entity) {

        if (entity == null | dto == null) {

            return null;
        }

        if (dto.getName() != null & !("".equals(dto.getName().trim()))) {
            entity.setName(dto.getName().trim());
        } else {
            entity.setName(entity.getName());
        }

        if (dto.getCityId() != null & 0 < dto.getCityId()) {
            entity.setCityInfo(cityInfoRepository.findOne(dto.getCityId()));
        }

        entity.setContact(dto.getContact().trim());
        entity.setEmail(dto.getEmail().trim().toLowerCase());
        entity.setMobileNumber(dto.getMobileNumber().trim());
        entity.setPanNumber(dto.getPanNumber().trim());
        entity.setRegNumber(dto.getRegNumber().trim());
        entity.setStreet(dto.getStreet().trim());

        return entity;
    }

    @Override
    public List<StoreInfoDTO> convertToDtoList(List<StoreInfo> entities) {
        return entities.parallelStream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<StoreInfo> convertToEntityList(List<StoreInfoDTO> dtoList) {
        return dtoList.parallelStream().filter(Objects::nonNull).map(this::convertToEntity).collect(Collectors.toList());
    }
}
