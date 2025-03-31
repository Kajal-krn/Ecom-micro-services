package com.kajal.user_service.repository;

import com.kajal.user_service.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User,String> {
    User findByUserName(String username);
    boolean existsByEmail(String email);
    boolean existsByUserName(String userName);
}