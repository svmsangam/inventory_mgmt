package com.inventory.core.api.iapi;

import com.inventory.core.model.dto.LoggerDTO;
import com.inventory.core.model.enumconstant.LogType;
import com.inventory.core.model.enumconstant.Status;

import java.util.List;

/**
 * Created by dhiraj on 12/17/17.
 */
public interface ILoggerApi {

    List<LoggerDTO> getAllByStatusAndAssociateIdAndTypeAndStore(Status status , long associateId , LogType type , long storeInfoId);

    void save(long associateId , LogType type , long storeInfoId , long userId , String log);

}
