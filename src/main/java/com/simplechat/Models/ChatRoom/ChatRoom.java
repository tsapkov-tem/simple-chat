package com.simplechat.Models.ChatRoom;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class ChatRoom {
    @Id
    private String id;
    private String chatId;
    private String senderId;
    private String recipientId;
}
