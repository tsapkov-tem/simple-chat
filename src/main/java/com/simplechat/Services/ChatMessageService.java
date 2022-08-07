package com.simplechat.Services;

import com.simplechat.Models.Message.Message;
import com.simplechat.Repositories.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatMessageService {
    private final ChatMessageRepository repository;

    @Autowired
    public ChatMessageService(ChatMessageRepository repository) {
        this.repository = repository;
    }

    public List<Message> findAll(int separate){
        Pageable pageable = PageRequest.of (separate, 10, Sort.by ("time").descending ());
        return repository.findAll (pageable).stream().toList ();
    }

    public void saveMessage(Message message){
        repository.save (message);
    }
}
