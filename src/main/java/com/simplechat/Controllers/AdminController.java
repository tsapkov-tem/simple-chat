package com.simplechat.Controllers;


import com.simplechat.Services.UsersService;
import com.simplechat.Models.Users.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AdminController {
    private final UsersService usersService;

    @Autowired
    public AdminController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/admin")
    @PreAuthorize ("hasAuthority('all')")
    public String getAdminPage(Model model){
        List<Users> users = usersService.listAll ();
        model.addAttribute ("users", users);
        return "admin";
    }
}
