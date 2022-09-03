package com.simplechat.Controllers;

import com.simplechat.Services.CurrentModel;
import com.simplechat.Services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class UserController {

    private final CurrentModel currentModel;
    private final UsersService usersService;

    @Autowired
    public UserController(CurrentModel currentModel, UsersService usersService) {
        this.currentModel = currentModel;
        this.usersService = usersService;
    }

    @GetMapping("/user")
    @PreAuthorize("hasAnyAuthority('read', 'all')")
    public String getUserPage(Model model){
        model.addAttribute ("user", currentModel.getCurrentUser ());
        return "user";
    }

    @PostMapping("/user/edit")
    @PreAuthorize("hasAnyAuthority('read', 'all')")
    public String editUsername(@RequestParam String username){
        if (!currentModel.getCurrentUser ().getUsername ().equals (username) && !username.isEmpty ()){
            currentModel.getCurrentUser ().setUsername (username);
            usersService.save (currentModel.getCurrentUser ());
        }
        return "user";
    }

}
