/*
package com.inventory.core.api.impl;

import com.inventory.core.api.fb.FirebaseResponse;
import com.inventory.core.api.fb.PushNotificationsService;
import com.inventory.core.api.iapi.IPushNotification;
import com.inventory.core.model.dto.NotificationDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

*/
/**
 * Created by dhiraj on 1/18/18.
 *//*

@Service
public class PushNotification implements IPushNotification {

    @Autowired
    PushNotificationsService pushNotificationsService;

   @Override
    public boolean sent(NotificationDTO notificationDTO){

        JSONObject body = new JSONObject();
        // JsonArray registration_ids = new JsonArray();
        // body.put("registration_ids", registration_ids);
        body.put("to", notificationDTO.getReceiverKey());
        //body.put("priority", "high");
        // body.put("dry_run", true);

        JSONObject notification = new JSONObject();
        notification.put("body", notificationDTO.getBody());
        notification.put("title", notificationDTO.getTitle());
        // notification.put("icon", "myicon");

        JSONObject data = new JSONObject();
        data.put("url", notificationDTO.getUrl());
        data.put("key2", "value2");

        body.put("notification", notification);
        body.put("data", data);

        HttpEntity<String> request = new HttpEntity<>(body.toString());

        CompletableFuture<FirebaseResponse> pushNotification = pushNotificationsService.send(request);

        CompletableFuture.allOf(pushNotification).join();

        try {
            FirebaseResponse firebaseResponse = pushNotification.get();
            if (firebaseResponse.getSuccess() == 1) {
                System.out.println("push notification sent ok!");
                return true;
            } else {
                System.out.println("error sending push notifications: " + firebaseResponse.toString());
                return false;
            }
            // return new ResponseEntity<>(firebaseResponse.toString(), HttpStatus.OK);

        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        } catch (ExecutionException e) {
            e.printStackTrace();
            return false;
        }
    }
}
*/
