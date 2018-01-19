package com.inventory.core.api.iapi;

import com.inventory.core.model.dto.NotificationDTO;
import com.inventory.core.model.enumconstant.Status;

import java.util.List;

/**
 * Created by dhiraj on 1/19/18.
 */
public interface INotificationApi {

    NotificationDTO saveAndSendForSuperAdmin(String title, String body, long storeInfoId);

    NotificationDTO saveAndSend(String title , String body , long to , long storeInfoId);

    long countAllByStatusAndStoreInfo_IdAndTo_Id(Status status , long storeInfoId , long toUserId);

    long countAllByStatusAndStoreInfo_IdAndTo_IdAndSeenAndSent(Status status , long storeInfoId , long toUserId , boolean seen , boolean sent);

    List<NotificationDTO> findAllByStatusAndStoreInfo_IdAndTo_Id(Status status , long storeInfoId , long toUserId , int page , int size);

}
