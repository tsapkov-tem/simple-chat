package com.simplechat.Services;

import com.simplechat.Repositories.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatRoomService {

    @Autowired
    private final ChatMessageRepository repository;

    public ChatRoomService(ChatMessageRepository repository) {
        this.repository = repository;
    }


}
