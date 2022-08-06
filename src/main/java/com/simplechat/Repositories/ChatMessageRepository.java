package com.simplechat.Repositories;

import com.simplechat.Models.Message.Message;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatMessageRepository extends MongoRepository<Message, String> {
}
