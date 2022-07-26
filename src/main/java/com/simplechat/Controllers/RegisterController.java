package com.simplechat.Controllers;

import org.springframework.dao.DuplicateKeyException;
import com.simplechat.Services.UsersService;
import com.simplechat.Models.Users.Role;
import com.simplechat.Models.Users.Status;
import com.simplechat.Models.Users.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegisterController {

    private final UsersService usersService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegisterController(UsersService usersService, PasswordEncoder passwordEncoder) {
        this.usersService = usersService;
        this.passwordEncoder = passwordEncoder;
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
        try {
            usersService.save (user);
        }catch (DuplicateKeyException e){
            return "redirect:/register"; //todo
        }
        return "redirect:/login";
    }

}
