package com.inventory.core.api.impl;

import com.inventory.core.model.dto.StateInfoDTO;
import com.inventory.core.api.iapi.IStateInfoApi;
import com.inventory.core.model.converter.StateInfoConverter;
import com.inventory.core.model.entity.StateInfo;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.model.repository.StateInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by dhiraj on 4/25/17.
 */
@Service
@Transactional
public class StateInfoApi implements IStateInfoApi {

    @Autowired
    private StateInfoRepository stateRepository;

    @Autowired
    private StateInfoConverter stateConverter;

    @Override
    public StateInfoDTO save(StateInfoDTO stateDTO) {

        return stateConverter.convertToDto(stateRepository.save(stateConverter.convertToEntity(stateDTO)));
    }

    @Override
    public StateInfoDTO update(StateInfoDTO stateDTO) {

        return stateConverter.convertToDto(stateRepository.save(stateConverter.copyConvertToEntity(stateDTO , stateRepository.findByIdAndStatus(stateDTO.getStateId() , Status.ACTIVE))));
    }

    @Override
    public void delete(long stateId) {

        StateInfo state = stateRepository.findByIdAndStatus(stateId , Status.ACTIVE);

        state.setStatus(Status.DELETED);

        stateRepository.save(state);
    }

    @Override
    public StateInfoDTO show(long stateId) {

        return stateConverter.convertToDto(stateRepository.findByIdAndStatus(stateId , Status.ACTIVE));
    }

    @Override
    public StateInfoDTO getByName(String stateName) {
        return stateConverter.convertToDto(stateRepository.findByName(stateName));
    }

    @Override
    public List<StateInfoDTO> list() {

        return stateConverter.convertToDtoList(stateRepository.findAllByStatus(Status.ACTIVE));
    }

	@Override
	public StateInfoDTO getStateByName(String stateName) {
		return stateConverter.convertToDto(stateRepository.findByNameAndStatus(stateName , Status.ACTIVE));
	}
}
