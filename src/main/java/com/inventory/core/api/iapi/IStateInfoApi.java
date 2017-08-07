package com.inventory.core.api.iapi;

import com.inventory.core.model.dto.StateInfoDTO;

import java.util.List;

/**
 * Created by dhiraj on 4/25/17.
 */
public interface IStateInfoApi {

    StateInfoDTO save(StateInfoDTO stateDTO);

    StateInfoDTO update(StateInfoDTO stateDTO);

    void delete(long stateId);

    StateInfoDTO show(long stateId);

    StateInfoDTO getByName(String stateName);

    List<StateInfoDTO> list();

    StateInfoDTO getStateByName(String stateName);
}
