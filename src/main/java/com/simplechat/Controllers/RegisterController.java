package com.example.mongodb.spring.Controllers;

import com.example.mongodb.spring.Models.Users.Role;
import com.example.mongodb.spring.Models.Users.Status;
import com.example.mongodb.spring.Models.Users.Users;
import com.example.mongodb.spring.Services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RegisterController {

    private final UsersService usersService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public RegisterController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model){
        model.addAttribute ("user", new Users ());
        return "register";
    }

    @PostMapping ("/register")
    public String getRegisterSubmit(@RequestParam String username, @RequestParam String password){
        Users user = new Users ();
        user.setUsername (username);
        user.setPassword(passwordEncoder.encode (password));
        user.setRole (Role.USER);
        user.setStatus (Status.ACTIVE);
        usersService.save (user);
        return "redirect:/login";
    }

}
