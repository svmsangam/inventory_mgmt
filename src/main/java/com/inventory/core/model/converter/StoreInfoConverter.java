package com.inventory.core.model.converter;

import com.inventory.core.model.dto.StoreInfoDTO;
import com.inventory.core.model.entity.StoreInfo;
import com.inventory.core.util.IConvertable;
import com.inventory.core.util.IListConvertable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by dhiraj on 8/1/17.
 */
@Service
public class StoreInfoConverter implements IConvertable<StoreInfo , StoreInfoDTO> , IListConvertable<StoreInfo , StoreInfoDTO>{

    @Override
    public StoreInfo convertToEntity(StoreInfoDTO dto) {
        return copyConvertToEntity(dto , new StoreInfo());
    }

    @Override
    public StoreInfoDTO convertToDto(StoreInfo entity) {

        if (entity == null){

            return null;
        }

        StoreInfoDTO dto = new StoreInfoDTO();

        dto.setStoreId(entity.getId());

        return dto;
    }

    @Override
    public StoreInfo copyConvertToEntity(StoreInfoDTO dto, StoreInfo entity) {
        return null;
    }

    @Override
    public List<StoreInfoDTO> convertToDtoList(List<StoreInfo> entities) {
        return null;
    }

    @Override
    public List<StoreInfo> convertToEntityList(List<StoreInfoDTO> dtoList) {
        return null;
    }
}
