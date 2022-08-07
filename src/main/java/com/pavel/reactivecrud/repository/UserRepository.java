package com.pavel.reactivecrud.repository;

import com.pavel.reactivecrud.model.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface UserRepository extends ReactiveMongoRepository<User, String> {
}
