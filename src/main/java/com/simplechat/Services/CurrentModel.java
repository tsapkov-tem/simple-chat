package com.example.mongodb.spring.Services;


import com.example.mongodb.spring.Models.Users.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class CurrentModel {
    private final UsersService usersService;

    @Autowired
    public CurrentModel(UsersService usersService) {
        this.usersService = usersService;
    }

    public Users getCurrentUser(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername ();
        } else {
            username = principal.toString();
        }
        return usersService.findByUsername (username);
    }
}
