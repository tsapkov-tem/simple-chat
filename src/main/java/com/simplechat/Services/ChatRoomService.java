package com.simplechat.Services;

import com.simplechat.Models.ChatRoom.ChatRoom;
import com.simplechat.Repositories.ChatRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ChatRoomService {

    private final ChatRoomRepository repository;

    @Autowired
    public ChatRoomService(ChatRoomRepository repository) {
        this.repository = repository;
    }

    public Optional<String> getChatId(String senderId, String recipientId, boolean createIfNotExist){
        return repository.findBySenderIdAndRecipientId (senderId,recipientId)
                .map (ChatRoom::getChatId).or(() -> {
                    if (!createIfNotExist){
                        return Optional.empty ();
                    }

                    var chatId = String.format ("%s_%s", senderId, recipientId);

                    ChatRoom recipientSender = ChatRoom
                            .builder ()
                            .chatId (chatId)
                            .senderId (senderId)
                            .recipientId (recipientId)
                            .build ();

                    ChatRoom senderRecipient = ChatRoom
                            .builder ()
                            .chatId (chatId)
                            .senderId (recipientId)
                            .recipientId (senderId)
                            .build ();

                    repository.save (senderRecipient);
                    repository.save (recipientSender);

                    return  Optional.of (chatId);
                });
    }

}
