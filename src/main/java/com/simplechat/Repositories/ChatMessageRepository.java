package com.simplechat.Repositories;

import com.simplechat.Models.Message.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends MongoRepository<Message, String> {
    Page<Message> findAll(Pageable pageable);
}
