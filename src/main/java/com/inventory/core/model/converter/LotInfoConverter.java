package com.inventory.core.model.converter;

import com.inventory.core.model.dto.LotInfoDTO;
import com.inventory.core.model.entity.LotInfo;
import com.inventory.core.model.repository.UserRepository;
import com.inventory.core.util.IConvertable;
import com.inventory.core.util.IListConvertable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by dhiraj on 8/11/17.
 */
@Service
public class LotInfoConverter implements IConvertable<LotInfo, LotInfoDTO>, IListConvertable<LotInfo, LotInfoDTO> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public LotInfo convertToEntity(LotInfoDTO dto) {
        return copyConvertToEntity(dto, new LotInfo());
    }

    @Override
    public LotInfoDTO convertToDto(LotInfo entity) {

        if (entity == null) {
            return null;
        }

        LotInfoDTO dto = new LotInfoDTO();

        dto.setLot(entity.getLot());
        dto.setLotId(entity.getId());
        dto.setVersion(entity.getVersion());

        return dto;
    }

    @Override
    public LotInfo copyConvertToEntity(LotInfoDTO dto, LotInfo entity) {

        if (entity == null | dto == null) {
            return null;
        }

        entity.setLot(dto.getLot().trim());

        return entity;
    }

    @Override
    public List<LotInfoDTO> convertToDtoList(List<LotInfo> entities) {
        return entities.parallelStream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<LotInfo> convertToEntityList(List<LotInfoDTO> dtoList) {
        return dtoList.parallelStream().filter(Objects::nonNull).map(this::convertToEntity).collect(Collectors.toList());
    }
}
