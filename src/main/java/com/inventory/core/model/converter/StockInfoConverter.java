package com.inventory.core.model.converter;

import com.inventory.core.model.dto.StockInfoDTO;
import com.inventory.core.model.entity.StockInfo;
import com.inventory.core.util.IConvertable;
import com.inventory.core.util.IListConvertable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by dhiraj on 8/10/17.
 */
@Service
public class StockInfoConverter implements IConvertable<StockInfo , StockInfoDTO> , IListConvertable<StockInfo , StockInfoDTO>{

    @Override
    public StockInfo convertToEntity(StockInfoDTO dto) {
        return null;
    }

    @Override
    public StockInfoDTO convertToDto(StockInfo entity) {
        return null;
    }

    @Override
    public StockInfo copyConvertToEntity(StockInfoDTO dto, StockInfo entity) {
        return null;
    }

    @Override
    public List<StockInfoDTO> convertToDtoList(List<StockInfo> entities) {
        return null;
    }

    @Override
    public List<StockInfo> convertToEntityList(List<StockInfoDTO> dtoList) {
        return null;
    }
}
