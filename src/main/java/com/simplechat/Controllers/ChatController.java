package com.simplechat.Controllers;

import com.simplechat.Models.ChatMessage.ChatMessage;
import com.simplechat.Models.ChatNotification.ChatNotification;
import com.simplechat.Services.ChatMessageService;
import com.simplechat.Services.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMessageService chatMessageService;
    private final ChatRoomService chatRoomService;

    @Autowired
    public ChatController(SimpMessagingTemplate messagingTemplate, ChatMessageService chatMessageService, ChatRoomService chatRoomService) {
        this.messagingTemplate = messagingTemplate;
        this.chatMessageService = chatMessageService;
        this.chatRoomService = chatRoomService;
    }

    @MessageMapping("/chat")
    public void processMessage(@Payload ChatMessage chatMessage){
        var chatId = chatRoomService.getChatId (chatMessage.getSenderId (), chatMessage.getRecipientId (), true);
        chatMessage.setChatId (chatId.get ());

        ChatMessage saved = chatMessageService.saveMessage (chatMessage);

        ChatNotification notification = ChatNotification
                .builder ()
                .id (saved.getId ())
                .senderId (saved.getSenderId ())
                .senderName (saved.getSenderName ())
                .build ();

        messagingTemplate.convertAndSendToUser (
                chatMessage.getRecipientId (),"/queue/messages", notification
        );
    }
}
