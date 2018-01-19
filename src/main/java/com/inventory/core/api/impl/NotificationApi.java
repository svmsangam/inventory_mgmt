package com.inventory.core.api.impl;

import com.inventory.core.api.iapi.INotificationApi;
import com.inventory.core.api.iapi.IPushNotification;
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
    private IPushNotification pushNotification;

    @Autowired
    private StoreUserInfoRepository storeUserInfoRepository;

    @Override
    public NotificationDTO saveAndSendForSuperAdmin(String title, String body, long storeInfoId) {

        try {

            User superAdmin = storeUserInfoRepository.findSuperAdminByStoreInfo(storeInfoId);

            return saveAndSend(title, body, superAdmin.getId(), storeInfoId);

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public NotificationDTO saveAndSend(String title, String body, long to, long storeInfoId) {

        Notification notification = notificationConverter.convertToEntity(title , body , to , storeInfoId , true);

        notification.setStatus(Status.ACTIVE);

        notification = notificationRepository.save(notification);

        NotificationDTO notificationDTO = notificationConverter.convertToDto(notification);

        if (notification.getTo().getFcmKey() != null) {
            pushNotification.sent(notificationDTO);
        }

        return notificationDTO;
    }

    @Override
    public long countAllByStatusAndStoreInfo_IdAndTo_Id(Status status, long storeInfoId, long toUserId) {

        return notificationRepository.countAllByStatusAndStoreInfo_IdAndTo_Id(status, storeInfoId, toUserId) ;
    }

    @Override
    public long countAllByStatusAndStoreInfo_IdAndTo_IdAndSeenAndSent(Status status, long storeInfoId, long toUserId, boolean seen, boolean sent) {

        return countAllByStatusAndStoreInfo_IdAndTo_IdAndSeenAndSent(status, storeInfoId, toUserId, seen, sent);
    }

    @Override
    public List<NotificationDTO> findAllByStatusAndStoreInfo_IdAndTo_Id(Status status, long storeInfoId, long toUserId, int page, int size) {

        Pageable pageable = createPageRequest(page , size , "id" , Sort.Direction.DESC);

        return notificationConverter.convertToDtoList(notificationRepository.findAllByStatusAndStoreInfo_IdAndTo_Id(status, storeInfoId, toUserId, pageable));
    }

    private Pageable createPageRequest(int page , int size , String properties , Sort.Direction direction) {

        return new PageRequest(page, size, new Sort(direction, properties));
    }
}
