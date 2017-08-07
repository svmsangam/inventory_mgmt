package com.inventory.core.model.converter;

import com.inventory.core.model.dto.StoreInfoDTO;
import com.inventory.core.model.entity.StoreInfo;
import com.inventory.core.util.IConvertable;
import org.springframework.stereotype.Service;

/**
 * Created by dhiraj on 8/1/17.
 */
@Service
public class StoreInfoConverter implements IConvertable<StoreInfo , StoreInfoDTO> {

    @Override
    public StoreInfo convertToEntity(StoreInfoDTO dto) {
        return null;
    }

    @Override
    public StoreInfoDTO convertToDto(StoreInfo entity) {
        return null;
    }

    @Override
    public StoreInfo copyConvertToEntity(StoreInfoDTO dto, StoreInfo entity) {
        return null;
    }
}
