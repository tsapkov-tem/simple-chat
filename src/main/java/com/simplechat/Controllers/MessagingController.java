package com.simplechat.Controllers;



import com.simplechat.Models.Message.Message;
import com.simplechat.Services.CurrentModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class MessagingController {


    private final CurrentModel currentModel;

    @Autowired
    public MessagingController(CurrentModel currentModel) {
        this.currentModel = currentModel;
    }

    @MessageMapping("/chat.send")
    @SendTo("/topic/public")
    public Message sendMessage(@Payload Message message){
        message.setSender (currentModel.getCurrentUser ().getUsername ());
        return message;
    }
}
