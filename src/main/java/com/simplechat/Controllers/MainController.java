package com.simplechat.Controllers;

import com.simplechat.Services.CurrentModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class MainController {

    private final CurrentModel currentModel;

    @Autowired
    public MainController(CurrentModel currentModel) {
        this.currentModel = currentModel;
    }


    @GetMapping("/success")
    @PreAuthorize ("hasAnyAuthority('read', 'all')")
    public String showHomePage(){
        switch (currentModel.getCurrentUser ().getRole ()){
            case ADMIN -> {
                return "redirect:/admin";
            }
            default ->  {
                return "redirect:/user";
            }
        }
    }
}
