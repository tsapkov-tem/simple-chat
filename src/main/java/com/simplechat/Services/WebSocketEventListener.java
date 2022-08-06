package com.simplechat.Services;

import com.simplechat.Models.Message.Message;
import com.simplechat.Models.Message.MessageType;
import com.simplechat.Models.Users.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Service
public class WebSocketEventListener {

    private final SimpMessageSendingOperations sendingOperations;
    private final CurrentModel currentModel;
    private final UsersService usersService;
    private static final Logger LOGGER = LoggerFactory.getLogger (WebSocketEventListener.class);

    @Autowired
    public WebSocketEventListener(SimpMessageSendingOperations sendingOperations, CurrentModel currentModel, UsersService usersService) {
        this.sendingOperations = sendingOperations;
        this.currentModel = currentModel;
        this.usersService = usersService;
    }

    @EventListener
    public void handleWebSocketConnectListener(final SessionConnectedEvent event){
        LOGGER.info ("There is a connection!");
        if(currentModel.getCurrentUser().getStatus () == Status.NEW) {
            Message message = new Message ();
            message.setType (MessageType.CONNECT);
            message.setSender (currentModel.getCurrentUser ().getUsername ());
            sendingOperations.convertAndSend ("/topic/public", message);
            currentModel.getCurrentUser ().setStatus (Status.ACTIVE);
            usersService.save (currentModel.getCurrentUser ());
        }
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event){
        if(currentModel.getCurrentUser().getStatus () == Status.BANNED) {
            Message message = new Message ();
            message.setType (MessageType.DISCONNECT);
            message.setSender (currentModel.getCurrentUser ().getUsername ());
            sendingOperations.convertAndSend ("/topic/public", message);
        }
    }
}
