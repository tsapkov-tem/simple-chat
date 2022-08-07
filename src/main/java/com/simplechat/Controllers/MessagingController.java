package com.simplechat.Controllers;



import com.simplechat.Models.Message.Message;
import com.simplechat.Services.ChatMessageService;
import com.simplechat.Services.CurrentModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class MessagingController {


    private final CurrentModel currentModel;
    private final ChatMessageService service;

    @Autowired
    public MessagingController(CurrentModel currentModel, ChatMessageService service) {
        this.currentModel = currentModel;
        this.service = service;
    }

    @MessageMapping("/chat.send")
    @SendTo("/topic/public")
    public Message sendMessage(@Payload Message message){
        message.setSender (currentModel.getCurrentUser ().getUsername ());
        message.setTime (new Date ());
        service.saveMessage (message);
        return message;
    }

    @MessageMapping("/chat.load")
    @SendTo("/topic/public")
    public Message loadMessage(@Payload Map<String, Object> extract){
        int separate = (int) extract.get ("separate");
        int numberOfMessage = (int) extract.get ("numberOfMessage");
        List<Message> messages = service.findAll (separate);
        try {
            return messages.get (numberOfMessage);
        }catch (IndexOutOfBoundsException e){
            return null;
        }
    }
}
