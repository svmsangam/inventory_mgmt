package com.inventory.core.api.impl;

import com.inventory.core.api.iapi.IFiscalYearInfoApi;
import com.inventory.core.model.converter.FiscalYearInfoConverter;
import com.inventory.core.model.dto.FiscalYearInfoDTO;
import com.inventory.core.model.entity.FiscalYearInfo;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.model.repository.FiscalYearInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by dhiraj on 12/24/17.
 */
@Service
@Transactional
public class FiscalYearInfoApi implements IFiscalYearInfoApi {

    @Autowired
    private FiscalYearInfoConverter fiscalYearInfoConverter;

    @Autowired
    private FiscalYearInfoRepository fiscalYearInfoRepository;

    @Override
    public FiscalYearInfoDTO save(FiscalYearInfoDTO fiscalYearInfoDTO) {

        FiscalYearInfo fiscalYearInfo = fiscalYearInfoConverter.convertToEntity(fiscalYearInfoDTO);

        FiscalYearInfo selected = fiscalYearInfoRepository.findByStatusAndStoreInfoAndSelected(Status.ACTIVE , fiscalYearInfoDTO.getStoreInfoId() , true);

        if (selected != null){

            selected.setSelected(false);

            fiscalYearInfoRepository.save(selected);
        }

        fiscalYearInfo.setSelected(true);
        fiscalYearInfo.setStatus(Status.ACTIVE);

        return fiscalYearInfoConverter.convertToDto(fiscalYearInfoRepository.save(fiscalYearInfo));
    }

    private Pageable createPageRequest(int page , int size , String properties , Sort.Direction direction) {

        return PageRequest.of(page, size, Sort.by(direction, properties));
    }

    @Override
    public List<FiscalYearInfoDTO> list(Status status, long storeInfoId, int page, int size) {

        Pageable pageable = createPageRequest(page,size ,"id" , Sort.Direction.DESC);

        return fiscalYearInfoConverter.convertToDtoList(fiscalYearInfoRepository.findAllByStatusAndStoreInfo(status , storeInfoId , pageable));
    }

    @Override
    public FiscalYearInfoDTO show(Status status, long fiscalYearInfoId, long storeInfoId) {
        return fiscalYearInfoConverter.convertToDto(fiscalYearInfoRepository.findByIdAndStatusAndStoreInfo(fiscalYearInfoId , status , storeInfoId));
    }

    @Override
    public FiscalYearInfoDTO select(long fiscalYearInfoId, long storeInfoId) {

        FiscalYearInfo selected = fiscalYearInfoRepository.findByStatusAndStoreInfoAndSelected(Status.ACTIVE , storeInfoId , true);

        if (selected != null){

            selected.setSelected(false);

            fiscalYearInfoRepository.save(selected);
        }

        FiscalYearInfo fiscalYearInfo = fiscalYearInfoRepository.findById(fiscalYearInfoId);

        fiscalYearInfo.setSelected(true);

        return fiscalYearInfoConverter.convertToDto(fiscalYearInfoRepository.save(fiscalYearInfo));
    }

    @Override
    public FiscalYearInfoDTO getByIdAndStatusAndStoreInfoAndSelected(long fiscalYearInfoId, Status status, long storeInfoId, boolean selected) {
        return fiscalYearInfoConverter.convertToDto(fiscalYearInfoRepository.findByIdAndStatusAndStoreInfoAndSelected(fiscalYearInfoId , status , storeInfoId , selected));
    }

    @Override
    public FiscalYearInfoDTO getCurrentFiscalYearByStoreInfo(long storeInfoId){
        return fiscalYearInfoConverter.convertToDto(fiscalYearInfoRepository.findByStatusAndStoreInfoAndSelected(Status.ACTIVE , storeInfoId , true));
    }

    @Override
    public long getCurrentFiscalYearIdByStoreInfo(long storeInfoId) {

        FiscalYearInfo fiscalYearInfo = fiscalYearInfoRepository.findByStatusAndStoreInfoAndSelected(Status.ACTIVE , storeInfoId , true);

        if (fiscalYearInfo == null){
            return 0;
        }
        return fiscalYearInfo.getId();
    }
}
