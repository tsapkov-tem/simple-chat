package com.simplechat.Controllers;

import com.simplechat.Services.CurrentModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChatController {

    private final
    CurrentModel currentModel;

    @Autowired
    public ChatController(CurrentModel currentModel) {
        this.currentModel = currentModel;
    }

    @GetMapping("/chat")
    public String getChatPage(Model model){
        model.addAttribute ("role", currentModel.getCurrentUser ().getRole ());
        return "chat";
    }
}
