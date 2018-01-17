package com.inventory.core.model.converter;

import com.inventory.core.model.dto.ReturnItemInfoDTO;
import com.inventory.core.model.entity.ReturnItemInfo;
import com.inventory.core.model.repository.OrderItemInfoRepository;
import com.inventory.core.model.repository.OrderReturnInfoRepository;
import com.inventory.core.util.IConvertable;
import com.inventory.core.util.IListConvertable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by dhiraj on 1/17/18.
 */
@Service
public class ReturnItemInfoConverter implements IListConvertable<ReturnItemInfo , ReturnItemInfoDTO> , IConvertable<ReturnItemInfo , ReturnItemInfoDTO> {

    @Autowired
    private OrderItemInfoRepository orderItemInfoRepository;

    @Autowired
    private OrderReturnInfoRepository orderReturnInfoRepository;

    @Override
    public List<ReturnItemInfoDTO> convertToDtoList(List<ReturnItemInfo> entities) {
        return null;
    }

    @Override
    public List<ReturnItemInfo> convertToEntityList(List<ReturnItemInfoDTO> dtoList) {
        return null;
    }

    @Override
    public ReturnItemInfo convertToEntity(ReturnItemInfoDTO dto) {
        return null;
    }

    @Override
    public ReturnItemInfoDTO convertToDto(ReturnItemInfo entity) {
        return null;
    }

    @Override
    public ReturnItemInfo copyConvertToEntity(ReturnItemInfoDTO dto, ReturnItemInfo entity) {
        return null;
    }
}
