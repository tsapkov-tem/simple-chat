package com.example.mongodb.spring.Controllers;


import com.example.mongodb.spring.Services.CurrentModel;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class UserController {

    private final CurrentModel currentModel;

    public UserController(CurrentModel currentModel) {
        this.currentModel = currentModel;
    }

    @GetMapping("/user")
    @PreAuthorize("hasAuthority('read')")
    public String getUserPage(Model model){
        model.addAttribute ("user", currentModel.getCurrentUser ());
        return "user";
    }

}
