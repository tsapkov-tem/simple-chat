package com.simplechat.Models.Users;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

@Data
public class Users {
    @Id
    private String id;

    @Indexed(unique = true)
    private String username;

    private String password;

    private Role role;

    private Status status;

}
