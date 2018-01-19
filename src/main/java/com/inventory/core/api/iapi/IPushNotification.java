package com.inventory.core.api.iapi;

import com.inventory.core.model.dto.NotificationDTO;

/**
 * Created by dhiraj on 1/18/18.
 */
public interface IPushNotification {

    boolean sent(NotificationDTO notificationDTO);
}
