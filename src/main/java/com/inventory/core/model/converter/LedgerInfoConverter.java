package com.inventory.core.model.converter;

import com.inventory.core.model.dto.LedgerInfoDTO;
import com.inventory.core.model.entity.LedgerInfo;
import com.inventory.core.model.repository.AccountInfoRepository;
import com.inventory.core.model.repository.StoreInfoRepository;
import com.inventory.core.util.IConvertable;
import com.inventory.core.util.IListConvertable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by dhiraj on 9/16/17.
 */
@Service
public class LedgerInfoConverter implements IListConvertable<LedgerInfo , LedgerInfoDTO> , IConvertable<LedgerInfo , LedgerInfoDTO>{

    @Autowired
    private AccountInfoConverter accountInfoConverter;

    @Autowired
    private AccountInfoRepository accountInfoRepository;

    @Autowired
    private StoreInfoRepository storeInfoRepository;

    @Override
    public LedgerInfo convertToEntity(LedgerInfoDTO dto) {
        return copyConvertToEntity(dto , new LedgerInfo());
    }

    @Override
    public LedgerInfoDTO convertToDto(LedgerInfo entity) {

        if (entity == null) {
            return null;
        }

        LedgerInfoDTO dto = new LedgerInfoDTO();

        dto.setLedgerId(entity.getId());
        dto.setAccountEntryType(entity.getAccountEntryType());
        dto.setAccountId(entity.getAccountInfo().getId());
        dto.setAccountInfo(accountInfoConverter.convertToDto(entity.getAccountInfo()));
        dto.setAmount(entity.getAmount());
        dto.setLedgerEntryType(entity.getLedgerEntryType());
        dto.setRemarks(entity.getRemarks());
        dto.setStatus(entity.getStatus());
        dto.setStoreInfoId(entity.getStoreInfo().getId());

        return dto;
    }

    @Override
    public LedgerInfo copyConvertToEntity(LedgerInfoDTO dto, LedgerInfo entity) {

        if (entity == null | dto == null) {
            return null;
        }

        entity.setAccountEntryType(dto.getAccountEntryType());
        entity.setAccountInfo(accountInfoRepository.findById(dto.getAccountId()));
        entity.setAmount(dto.getAmount());
        entity.setLedgerEntryType(dto.getLedgerEntryType());
        entity.setRemarks(dto.getRemarks());
        entity.setStoreInfo(storeInfoRepository.findById(dto.getStoreInfoId()));

        return entity;
    }

    @Override
    public List<LedgerInfoDTO> convertToDtoList(List<LedgerInfo> entities) {
        return entities.parallelStream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<LedgerInfo> convertToEntityList(List<LedgerInfoDTO> dtoList) {
        return dtoList.parallelStream().filter(Objects::nonNull).map(this::convertToEntity).collect(Collectors.toList());
    }
}
