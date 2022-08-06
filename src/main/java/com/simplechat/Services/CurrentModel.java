package com.simplechat.Services;

import com.simplechat.Models.Users.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class CurrentModel {
    private final UsersService usersService;
    private Users currentUser;

    @Autowired
    public CurrentModel(UsersService usersService) {
        this.usersService = usersService;
    }

    public void uploadCurrentUser(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername ();
        } else {
            username = principal.toString();
        }
        currentUser = usersService.findByUsername (username);
    }

    public Users getCurrentUser(){
        return currentUser;
    }
}
