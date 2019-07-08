package com.inventory.core.model.converter;

import com.inventory.core.model.dto.LoggerDTO;
import com.inventory.core.model.entity.Logger;
import com.inventory.core.model.enumconstant.LogType;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.model.repository.StoreInfoRepository;
import com.inventory.core.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by dhiraj on 12/17/17.
 */
@Service
public class LoggerConverter {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StoreInfoRepository storeInfoRepository;

    public Logger convertToEntity(long associateId, LogType type, long storeInfoId, long userId, String log){

        Logger entity = new Logger();

        entity.setAssociateId(associateId);
        entity.setLog(log);
        entity.setStatus(Status.ACTIVE);
        entity.setType(type);
        entity.setStoreInfo(storeInfoRepository.findById(storeInfoId));
        entity.setUser(userRepository.findById(userId));

        return entity;
    }

    public List<LoggerDTO> convertToDTOList(List<Logger> entities){
        return entities.parallelStream().map(this::convertToDto).collect(Collectors.toList());
    }

    private LoggerDTO convertToDto(Logger entity){

        if (entity == null){
            return null;
        }

        LoggerDTO dto = new LoggerDTO();

        dto.setAssociateId(entity.getAssociateId());
        dto.setDate(entity.getCreated());
        dto.setLog(entity.getLog());
        dto.setLogId(entity.getId());
        dto.setStatus(entity.getStatus());
        dto.setType(entity.getType());
        dto.setUsername(entity.getUser().getUsername());

        return dto;

    }
}
