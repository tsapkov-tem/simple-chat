package com.simplechat.Repositories;


import com.simplechat.Models.Users.Users;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends MongoRepository<Users, String> {
    Optional<Users> findByUsername(String username);
}
