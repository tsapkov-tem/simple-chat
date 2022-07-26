package com.example.mongodb.spring.Services;


import com.example.mongodb.spring.Models.Users.Users;
import com.example.mongodb.spring.Repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService {

    private final UsersRepository repository;

    @Autowired
    public UsersService(UsersRepository repository){
        this.repository = repository;
    }

    public List<Users> listAll(){
        return (List<Users>) repository.findAll ();
    }

    public Users findByUsername(String username){
        return repository.findByUsername (username);
    }

    public void save(Users user){
        repository.save (user);
    }

}
