package com.pavel.reactivecrud.service;

import com.pavel.reactivecrud.model.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {

    Mono<User> save(User user);

    Flux<User> findAll();

    Mono<User> findById(String id);

    Mono<User> update(String id, User user);

    Mono<User> delete(String id);
}
