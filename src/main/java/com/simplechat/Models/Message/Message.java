package com.simplechat.Models.Message;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
public class Message {

    public Message() {
    }

    @Id
    private String id;
    private MessageType type;
    private String content;
    private String sender;
    private Date time;
}

