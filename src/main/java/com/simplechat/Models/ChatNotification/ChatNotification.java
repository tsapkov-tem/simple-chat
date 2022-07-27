package com.simplechat.Models.ChatNotification;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@Builder
public class ChatNotification {
    @Id
    private String id;
    private String senderId;
    private String senderName;
}
