package com.example.mongodb.spring.Repositories;


import com.example.mongodb.spring.Models.Users.Users;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends MongoRepository<Users, String> {
    Users findByUsername(String username);
}
