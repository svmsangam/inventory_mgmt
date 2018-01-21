package com.inventory.web.controller.ajax;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by dhiraj on 1/21/18.
 */
@Controller
public class Socket {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/websocket")
    //@SendTo("/topic/messages/")
    public void sendMessage(){
//	System.out.println("reciever "+message.getTo());
        messagingTemplate.convertAndSend("/topic/notification/"+"dhirajbadu", "hello");

    }

    @GetMapping(value = "/websocket")
    public void connect(){
//	System.out.println("reciever "+message.getTo());
        messagingTemplate.convertAndSend("/topic/notification/"+"dhirajbadu", "hello");

    }

}
