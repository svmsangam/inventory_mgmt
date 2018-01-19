package com.inventory.core.model.converter;

import com.inventory.core.model.dto.NotificationDTO;
import com.inventory.core.model.entity.Notification;
import com.inventory.core.model.repository.StoreInfoRepository;
import com.inventory.core.model.repository.UserRepository;
import com.inventory.core.util.IConvertable;
import com.inventory.core.util.IListConvertable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by dhiraj on 1/19/18.
 */
@Service
public class NotificationConverter implements IListConvertable<Notification , NotificationDTO> , IConvertable<Notification , NotificationDTO>{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StoreInfoRepository storeInfoRepository;

    @Override
    public Notification convertToEntity(NotificationDTO dto) {
        return copyConvertToEntity(dto , new Notification());
    }

    @Override
    public NotificationDTO convertToDto(Notification entity) {

        if (entity == null) {
            return null;
        }

        NotificationDTO dto = new NotificationDTO();

        dto.setNotificationId(entity.getId());
        dto.setBody(entity.getBody());
        dto.setSeen(entity.isSeen());
        dto.setSent(entity.isSent());
        dto.setStatus(entity.getStatus());
        dto.setStoreInfoId(entity.getStoreInfo().getId());
        dto.setTitle(entity.getTitle());
        dto.setToUserId(entity.getTo().getId());
        dto.setToUserName(entity.getTo().getUsername());

        return dto;
    }

    public Notification convertToEntity(String title, String body, long to, long storeInfoId , boolean sent){

        Notification entity = new Notification();

        entity.setBody(body);
        entity.setSeen(false);
        entity.setSent(sent);
        entity.setStoreInfo(storeInfoRepository.findById(storeInfoId));
        entity.setTitle(title);
        entity.setTo(userRepository.findById(to));

        return entity;
    }

    @Override
    public Notification copyConvertToEntity(NotificationDTO dto, Notification entity) {

        if (entity == null | dto == null){
            return null;
        }


        entity.setBody(dto.getBody());
        entity.setSeen(dto.isSeen());
        entity.setSent(dto.isSent());
        entity.setStoreInfo(storeInfoRepository.findById(dto.getStoreInfoId()));
        entity.setTitle(dto.getTitle());
        entity.setTo(userRepository.findById(dto.getToUserId()));

        return entity;
    }

    @Override
    public List<NotificationDTO> convertToDtoList(List<Notification> entities) {
        return entities.parallelStream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<Notification> convertToEntityList(List<NotificationDTO> dtoList) {
        return dtoList.parallelStream().filter(Objects::nonNull).map(this::convertToEntity).collect(Collectors.toList());
    }
}
