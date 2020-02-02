package com.inventory.core.model.converter;

import com.inventory.core.model.dto.StoreInvoiceDTO;
import com.inventory.core.model.entity.StoreInvoice;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.model.repository.StoreInfoRepository;
import com.inventory.core.util.IConvertable;
import com.inventory.core.util.IListConvertable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class StoreInvoiceConverter implements IConvertable<StoreInvoice, StoreInvoiceDTO>, IListConvertable<StoreInvoice, StoreInvoiceDTO> {

    @Autowired
    private StoreInfoRepository storeInfoRepository;

    @Override
    public StoreInvoice convertToEntity(StoreInvoiceDTO dto) {

        if (dto == null){
            return null;
        }

        StoreInvoice storeInvoice = copyConvertToEntity(dto , new StoreInvoice());
        storeInvoice.setStatus(Status.ACTIVE);
        return storeInvoice;
    }

    @Override
    public StoreInvoiceDTO convertToDto(StoreInvoice entity) {
        StoreInvoiceDTO dto = new StoreInvoiceDTO();

        dto.setStoreInvoiceId(entity.getId());
        dto.setCount(entity.getCount());
        dto.setMonth(entity.getMonth());
        dto.setStatus(entity.getStatus());

        try {
            dto.setStoreId(entity.getStoreInfo().getId());
            dto.setStoreName(entity.getStoreInfo().getName());
        }catch (Exception e){

        }

        return dto;
    }

    @Override
    public StoreInvoice copyConvertToEntity(StoreInvoiceDTO dto, StoreInvoice entity) {

        if (entity == null || dto == null){
            return null;
        }

        entity.setCount(dto.getCount());
        entity.setMonth(dto.getMonth());
        entity.setStoreInfo(storeInfoRepository.findById(dto.getStoreId()));

        return entity;
    }

    @Override
    public List<StoreInvoiceDTO> convertToDtoList(List<StoreInvoice> entities) {
        return entities.parallelStream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<StoreInvoice> convertToEntityList(List<StoreInvoiceDTO> dtoList) {
        return dtoList.parallelStream().filter(Objects::nonNull).map(this::convertToEntity).collect(Collectors.toList());
    }
}
