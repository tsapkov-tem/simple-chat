package com.example.mongodb.spring.Models.Users;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Users {
    @Id
    private String id;

    private String username;

    private String password;

    private Role role;

    private Status status;

}
