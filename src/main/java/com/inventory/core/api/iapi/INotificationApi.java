package com.inventory.core.api.iapi;

import com.inventory.core.model.dto.NotificationDTO;
import com.inventory.core.model.enumconstant.Status;

import java.util.List;

/**
 * Created by dhiraj on 1/19/18.
 */
public interface INotificationApi {

    NotificationDTO saveAndSendForSuperAdmin(String title, String body, String url, long storeInfoId);

    NotificationDTO saveAndSend(String title , String body , long to , long storeInfoId , String url);

    long countAllByStatusAndTo_Id(Status status , long toUserId);

    long countAllByStatusAndTo_IdAndSeenAndSent(Status status , long toUserId , boolean seen , boolean sent);

    List<NotificationDTO> findAllByStatusAndTo_Id(Status status , long toUserId , int page , int size);

    void send(NotificationDTO notificationDTO, String receiverKey);

    void send(String notification, long storeInfoId);
}
