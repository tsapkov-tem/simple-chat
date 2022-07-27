package com.simplechat.Services;

import com.simplechat.Models.ChatMessage.ChatMessage;
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

    public ChatMessage saveMessage(ChatMessage chatMessage){
        repository.save (chatMessage);
        return chatMessage;
    }
}
