package com.inventory.core.api.iapi;

/**
 * Created by dhiraj on 1/18/18.
 */
public interface IPushNotification {
    String sendNotification(String title, String body, String sendTo);
}
