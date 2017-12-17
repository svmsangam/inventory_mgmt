package com.inventory.core.api.impl;

import com.inventory.core.api.iapi.ILoggerApi;
import com.inventory.core.model.converter.LoggerConverter;
import com.inventory.core.model.dto.LoggerDTO;
import com.inventory.core.model.entity.Logger;
import com.inventory.core.model.enumconstant.LogType;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.model.repository.LoggerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by dhiraj on 12/17/17.
 */
@Service
@Transactional
public class LoggerApi implements ILoggerApi{

    @Autowired
    private LoggerRepository loggerRepository;

    @Autowired
    private LoggerConverter loggerConverter;

    @Override
    public List<LoggerDTO> getAllByStatusAndAssociateIdAndTypeAndStore(Status status, long associateId, LogType type, long storeInfoId) {
        return loggerConverter.convertToDTOList(loggerRepository.findAllByStatusAndAssociateIdAndTypeAndStore(status , associateId , type , storeInfoId));
    }

    @Override
    public void save(long associateId, LogType type, long storeInfoId, long userId, String log) {

        Logger logger = loggerConverter.convertToEntity(associateId , type , storeInfoId , userId , log);

        loggerRepository.save(logger);

    }
}
