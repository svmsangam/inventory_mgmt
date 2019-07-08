package com.inventory.core.model.converter;

import com.inventory.core.model.dto.QualificationLevelDTO;
import com.inventory.core.model.entity.QualificationLevel;
import com.inventory.core.model.repository.StoreInfoRepository;
import com.inventory.core.util.IConvertable;
import com.inventory.core.util.IListConvertable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by dhiraj on 2/4/18.
 */
@Service
public class QualificationConverter implements IConvertable<QualificationLevel, QualificationLevelDTO>, IListConvertable<QualificationLevel, QualificationLevelDTO> {

    @Autowired
    private StoreInfoRepository storeInfoRepository;

    @Override
    public QualificationLevel convertToEntity(QualificationLevelDTO dto) {
        return copyConvertToEntity(dto, new QualificationLevel());
    }

    @Override
    public QualificationLevelDTO convertToDto(QualificationLevel entity) {

        if (entity == null) {
            return null;
        }

        QualificationLevelDTO dto = new QualificationLevelDTO();

        dto.setQlfyLvlId(entity.getId());
        dto.setCode(entity.getCode());

       /* if (entity.getOwner() != null) {

            if (entity.getOwner().getId() != null) {
                dto.setOwnerId(entity.getOwner().getId());
                dto.setOwnerName(entity.getOwner().getName());
            }
        }
*/
        dto.setRemarks(entity.getRemarks());
        dto.setStatus(entity.getStatus());
        dto.setTitle(entity.getTitle());

        return dto;

    }

    @Override
    public QualificationLevel copyConvertToEntity(QualificationLevelDTO dto, QualificationLevel entity) {

        if (entity == null | dto == null) {
            return null;
        }

        entity.setCode(dto.getCode());
        entity.setOwner(storeInfoRepository.findById(dto.getOwnerId()));
        entity.setRemarks(dto.getRemarks());
        entity.setTitle(dto.getTitle());

        return entity;
    }

    @Override
    public List<QualificationLevelDTO> convertToDtoList(List<QualificationLevel> entities) {
        return entities.parallelStream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<QualificationLevel> convertToEntityList(List<QualificationLevelDTO> dtoList) {
        return dtoList.parallelStream().filter(Objects::nonNull).map(this::convertToEntity).collect(Collectors.toList());
    }
}
