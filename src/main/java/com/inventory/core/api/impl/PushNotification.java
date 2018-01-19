package com.inventory.core.api.impl;

import com.inventory.core.api.fb.PushNotificationsService;
import com.inventory.core.api.fb.FirebaseResponse;
import com.inventory.core.api.iapi.IPushNotification;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Created by dhiraj on 1/18/18.
 */
@Service
public class PushNotification implements IPushNotification {

    @Autowired
    PushNotificationsService pushNotificationsService;

    private static String End_Point = "https://fcm.googleapis.com/fcm/send";
    private static String Server_Key = "AAAAG5laY5g:APA91bHKBO_j7wH-vbfYUWeSc7OpNFNCKC0xiTvL9zb046boTXCvrcpfLaYSbLrR3LfrJNtlDxICpJ99tM8Q1Yyuhsn7sx8Yur3u_6OITCJ3mpH5hxTfMcQb1nWfRbn5Wf6v73Dhz3tK";

   /* @Override
    public String sendNotification(NotificationDTO notificationDTO) {
        try {
            JSONObject info = new JSONObject();
            info.put("title", notificationDTO.getTitle());
            info.put("body", notificationDTO.getBody());
            info.put("sound", "default");

            JSONObject json = new JSONObject();
            json.put("to", notificationDTO.getSendTo());
            json.put("data", info);

            HttpHeaders headers = new HttpHeaders();
            headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
            headers.set("Authorization", "key=" + Server_Key);
            HttpEntity<?> entity = new HttpEntity<>(json, headers);

            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<String> response = restTemplate.exchange(End_Point, HttpMethod.POST, entity, String.class);

            System.out.println(response);
            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return "Failure";
        }

    }*/

    @Override
    public String sendNotification(String title, String body, String sendTo) {
        try {
            JSONObject info = new JSONObject();
            info.put("title", title);
            info.put("body", body);
            info.put("sound", "default");

            JSONObject json = new JSONObject();
            json.put("to", sendTo);
            json.put("data", info);

            HttpHeaders headers = new HttpHeaders();
            headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
            headers.set("Authorization", "key=" + Server_Key);
            HttpEntity<?> entity = new HttpEntity<>(json, headers);

            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<String> response = restTemplate.exchange(End_Point, HttpMethod.POST, entity, String.class);

            System.out.println(response);
            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return "Failure";
        }

    }

    public static void main(String[] args) {

            JSONObject body = new JSONObject();
            // JsonArray registration_ids = new JsonArray();
            // body.put("registration_ids", registration_ids);
            body.put("to", "c7Ks_EYEXNQ:APA91bEcJfocpqdnkI-Co78OrVuoqz-6oBOQak3Knk8u1xLtD7p7OeiBo0USOFCZf9WzbfVkmognuCOAJanqn-t-MtDl2Rv0VMDIfrr7McyhIBBscP3WEe633DNFOoKVVPUvCNPUiZBJ");
            //body.put("priority", "high");
            // body.put("dry_run", true);

            JSONObject notification = new JSONObject();
            notification.put("body", "dhiraj badu");
            notification.put("title", "title string here");
            // notification.put("icon", "myicon");

            JSONObject data = new JSONObject();
            data.put("key1", "value1");
            data.put("key2", "value2");

            body.put("notification", notification);
            body.put("data", data);

            HttpEntity<String> request = new HttpEntity<>(body.toString());

            CompletableFuture<FirebaseResponse> pushNotification = new PushNotificationsService().send(request);
            CompletableFuture.allOf(pushNotification).join();

            try {
                FirebaseResponse firebaseResponse = pushNotification.get();
                if (firebaseResponse.getSuccess() == 1) {
                    System.out.println("push notification sent ok!");
                } else {
                    System.out.println("error sending push notifications: " + firebaseResponse.toString());
                }
                // return new ResponseEntity<>(firebaseResponse.toString(), HttpStatus.OK);

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

    }
}
