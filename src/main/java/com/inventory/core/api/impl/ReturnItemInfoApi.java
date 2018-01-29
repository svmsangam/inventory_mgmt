package com.inventory.core.api.impl;

import com.inventory.core.api.iapi.IReturnItemInfoApi;
import com.inventory.core.model.converter.ReturnItemInfoConverter;
import com.inventory.core.model.dto.OrderItemInfoDTO;
import com.inventory.core.model.dto.ReturnItemInfoDTO;
import com.inventory.core.model.entity.ReturnItemInfo;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.model.repository.ReturnItemInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by dhiraj on 1/29/18.
 */
@Service
@Transactional
public class ReturnItemInfoApi implements IReturnItemInfoApi {

    @Autowired
    private ReturnItemInfoRepository returnItemInfoRepository;

    @Autowired
    private ReturnItemInfoConverter returnItemInfoConverter;

    @Override
    public ReturnItemInfoDTO save(ReturnItemInfoDTO returnItemInfoDTO) {

        ReturnItemInfo returnItemInfo = returnItemInfoConverter.convertToEntity(returnItemInfoDTO);

        returnItemInfo.setStatus(Status.ACTIVE);

        returnItemInfo = returnItemInfoRepository.save(returnItemInfo);

        return returnItemInfoConverter.convertToDto(returnItemInfo);
    }

    @Override
    public List<ReturnItemInfoDTO> list(Status status, long returnOrderInfoId) {
        return returnItemInfoConverter.convertToDtoList(returnItemInfoRepository.findAllByStatusAndOrderReturnInfo_Id(status , returnOrderInfoId));
    }
}
