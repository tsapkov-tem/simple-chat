package com.simplechat.Controllers;

import com.simplechat.Models.EditResponse.EditResponse;
import com.simplechat.Models.EditResponse.TypeResponse;
import com.simplechat.Models.Users.Role;
import com.simplechat.Models.Users.Status;
import com.simplechat.Models.Users.Users;
import com.simplechat.Services.CurrentModel;
import com.simplechat.Services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Controller
public class EditUsersController {

    private final SimpMessageSendingOperations sendingOperations;
    private final CurrentModel currentModel;
    private final UsersService usersService;

    @Autowired
    public EditUsersController(SimpMessageSendingOperations sendingOperations, CurrentModel currentModel, UsersService usersService) {
        this.sendingOperations = sendingOperations;
        this.currentModel = currentModel;
        this.usersService = usersService;
    }

    @MessageMapping("/user.editUsername")
    public void editUsername(@Payload Map<String, Object> user){
        EditResponse editResponse = new EditResponse ();
        try{
            currentModel.getCurrentUser ().setUsername ((String) user.get ("username"));
            usersService.save (currentModel.getCurrentUser ());
            editResponse.setTypeResponse (TypeResponse.TRUE);
        }catch (DuplicateKeyException e){
            editResponse.setTypeResponse (TypeResponse.ERROR_USER);
        }
        sendingOperations.convertAndSend ("/edit/user",editResponse);
    }

    @MessageMapping("/user.changeUsers")
    public void changeUsers(@Payload Map<String, Object> user){
        EditResponse editResponse = new EditResponse ();
        Users users = usersService.findByUsername ((String) user.get ("username"));
        users.setRole (Role.valueOf ((String) user.get ("role")));
        users.setStatus (Status.valueOf ((String) user.get ("status")));
        usersService.save (users);
        editResponse.setTypeResponse (TypeResponse.TRUE_ADMIN);
        sendingOperations.convertAndSend ("/edit/user",editResponse);
    }
}
