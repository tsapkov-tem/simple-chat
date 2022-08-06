package com.simplechat.Services;

import com.simplechat.Models.Message.Message;
import com.simplechat.Repositories.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatMessageService {
    private final ChatMessageRepository repository;

    @Autowired
    public ChatMessageService(ChatMessageRepository repository) {
        this.repository = repository;
    }

    public Message saveMessage(Message message){
        repository.save (message);
        return message;
    }
}
