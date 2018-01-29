package com.inventory.core.api.impl;

import com.inventory.core.api.iapi.IOrderReturnInfoApi;
import com.inventory.core.model.converter.OrderReturnInfoConverter;
import com.inventory.core.model.dto.OrderReturnInfoDTO;
import com.inventory.core.model.entity.FiscalYearInfo;
import com.inventory.core.model.entity.OrderReturnInfo;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.model.repository.FiscalYearInfoRepository;
import com.inventory.core.model.repository.OrderReturnInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by dhiraj on 1/17/18.
 */
@Service
@Transactional
public class OrderReturnInfoApi implements IOrderReturnInfoApi {

    @Autowired
    private OrderReturnInfoConverter orderReturnInfoConverter;

    @Autowired
    private OrderReturnInfoRepository orderReturnInfoRepository;

    @Autowired
    private FiscalYearInfoRepository fiscalYearInfoRepository;

    @Override
    public OrderReturnInfoDTO save(OrderReturnInfoDTO orderReturnInfoDTO) {

        OrderReturnInfo orderReturnInfo = orderReturnInfoConverter.convertToEntity(orderReturnInfoDTO);

        orderReturnInfo.setStatus(Status.ACTIVE);

        FiscalYearInfo currentFiscalYear = fiscalYearInfoRepository.findByStatusAndStoreInfoAndSelected(Status.ACTIVE , orderReturnInfo.getStoreInfo().getId() , true);

        orderReturnInfo.setFiscalYearInfo(currentFiscalYear);

        orderReturnInfo = orderReturnInfoRepository.save(orderReturnInfo);

        return orderReturnInfoConverter.convertToDto(orderReturnInfo);
    }
}
