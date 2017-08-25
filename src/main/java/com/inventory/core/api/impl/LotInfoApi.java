package com.inventory.core.api.impl;

import com.inventory.core.api.iapi.ILotInfoApi;
import com.inventory.core.model.converter.LotInfoConverter;
import com.inventory.core.model.dto.LotInfoDTO;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.model.repository.LotInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by dhiraj on 8/25/17.
 */
@Transactional
@Service
public class LotInfoApi implements ILotInfoApi{

    @Autowired
    private LotInfoRepository lotInfoRepository;

    @Autowired
    private LotInfoConverter lotInfoConverter;

    @Override
    public LotInfoDTO save(LotInfoDTO lotInfoDTO) {
        return null;
    }

    @Override
    public LotInfoDTO update(LotInfoDTO lotInfoDTO) {
        return null;
    }

    @Override
    public void delete(long lotInfoId) {

    }

    @Override
    public LotInfoDTO getByNameAndStatus(String lotName, Status status) {
        return null;
    }

    @Override
    public LotInfoDTO getByIdAndStatus(long lotInfoId, Status status) {
        return null;
    }

    @Override
    public List<LotInfoDTO> list(Status status) {
        return lotInfoConverter.convertToDtoList(lotInfoRepository.findAllByStatus(Status.ACTIVE));
    }
}
