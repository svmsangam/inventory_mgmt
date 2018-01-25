package com.inventory.core.model.converter;

import com.inventory.core.model.dto.SubscriberDTO;
import com.inventory.core.model.entity.CityInfo;
import com.inventory.core.model.entity.Subscriber;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.model.repository.CityInfoRepository;
import com.inventory.core.model.repository.UserRepository;
import com.inventory.core.util.IConvertable;
import com.inventory.core.util.IListConvertable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by dhiraj on 1/25/18.
 */
@Service
public class SubscriberConverter implements IListConvertable<Subscriber , SubscriberDTO> , IConvertable<Subscriber , SubscriberDTO>{

    @Autowired
    private CityInfoRepository cityInfoRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Subscriber convertToEntity(SubscriberDTO dto) {
        return copyConvertToEntity(dto , new Subscriber());
    }

    @Override
    public SubscriberDTO convertToDto(Subscriber entity) {

        if (entity == null) {
            return null;
        }

        SubscriberDTO dto = new SubscriberDTO();

        dto.setCityInfoId(entity.getCityInfo().getId());
        dto.setCityName(entity.getCityInfo().getName());
        dto.setContact(entity.getContact());
        dto.setCreatedById(entity.getCreatedBy().getId());
        dto.setCreatedByName(entity.getCreatedBy().getUsername());
        dto.setCreatedByType(entity.getCreatedBy().getUserType());
        dto.setEmail(entity.getEmail());
        dto.setFullName(entity.getFullName());
        dto.setMobile(entity.getMobile());
        dto.setStatus(entity.getStatus());
        dto.setStreet(entity.getStreet());
        dto.setSubscriberId(entity.getUser().getId());
        dto.setUserId(entity.getId());
        dto.setUsername(entity.getUser().getUsername());

        return dto;
    }

    @Override
    public Subscriber copyConvertToEntity(SubscriberDTO dto, Subscriber entity) {

        if (entity == null | dto == null){
            return null;
        }

        entity.setCityInfo(cityInfoRepository.findByIdAndStatus(dto.getCityInfoId() , Status.ACTIVE));
        entity.setContact(dto.getContact());
        entity.setCreatedBy(userRepository.findById(dto.getCreatedById()));
        entity.setEmail(dto.getEmail());
        entity.setFullName(dto.getFullName());
        entity.setMobile(dto.getMobile());
        entity.setStatus(Status.ACTIVE);
        entity.setStreet(dto.getStreet());
        entity.setUser(userRepository.findById(dto.getUserId()));

        return entity;
    }

    @Override
    public List<SubscriberDTO> convertToDtoList(List<Subscriber> entities) {
        return entities.parallelStream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<Subscriber> convertToEntityList(List<SubscriberDTO> dtoList) {
        return dtoList.parallelStream().filter(Objects::nonNull).map(this::convertToEntity).collect(Collectors.toList());
    }
}
