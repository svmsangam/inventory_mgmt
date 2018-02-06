package com.inventory.core.api.impl;

import com.inventory.core.api.iapi.INotificationApi;
import com.inventory.core.model.converter.NotificationConverter;
import com.inventory.core.model.dto.NotificationDTO;
import com.inventory.core.model.entity.Notification;
import com.inventory.core.model.entity.User;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.model.repository.NotificationRepository;
import com.inventory.core.model.repository.StoreUserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by dhiraj on 1/19/18.
 */
@Service
@Transactional
public class NotificationApi implements INotificationApi{

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private NotificationConverter notificationConverter;

    @Autowired
    private StoreUserInfoRepository storeUserInfoRepository;


    @Override
    public NotificationDTO saveAndSendForSuperAdmin(String title, String body, String url , long storeInfoId) {

        try {

            User superAdmin = storeUserInfoRepository.findSuperAdminByStoreInfo(storeInfoId);

            return saveAndSend(title, body, superAdmin.getId(), storeInfoId , url);

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public NotificationDTO saveAndSend(String title, String body, long to, long storeInfoId , String url) {

        Notification notification = notificationConverter.convertToEntity(title , body , url , to , storeInfoId , true);

        notification.setStatus(Status.ACTIVE);

        notification = notificationRepository.save(notification);

        NotificationDTO notificationDTO = notificationConverter.convertToDto(notification);

       /* if (notification.getTo().getFcmKey() != null) {
            pushNotification.sent(notificationDTO);
        }*/

        return notificationDTO;
    }

    @Override
    public long countAllByStatusAndTo_Id(Status status, long toUserId) {

        return notificationRepository.countAllByStatusAndTo_Id(status, toUserId) ;
    }

    @Override
    public long countAllByStatusAndTo_IdAndSeenAndSent(Status status, long toUserId, boolean seen, boolean sent) {

        return notificationRepository.countAllByStatusAndTo_IdAndSeenAndSent(status, toUserId, seen, sent);
    }

    @Override
    public List<NotificationDTO> findAllByStatusAndTo_Id(Status status, long toUserId, int page, int size) {

        Pageable pageable = createPageRequest(page , size , "id" , Sort.Direction.DESC);

        return notificationConverter.convertToDtoList(notificationRepository.findAllByStatusAndTo_Id(status, toUserId, pageable));
    }

    private Pageable createPageRequest(int page , int size , String properties , Sort.Direction direction) {

        return new PageRequest(page, size, new Sort(direction, properties));
    }
}
