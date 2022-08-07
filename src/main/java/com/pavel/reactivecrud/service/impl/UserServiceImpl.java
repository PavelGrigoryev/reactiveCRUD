package com.pavel.reactivecrud.service.impl;

import com.pavel.reactivecrud.model.User;
import com.pavel.reactivecrud.repository.UserRepository;
import com.pavel.reactivecrud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Mono<User> save(User user) {
        return this.userRepository.save(user);
    }

    @Override
    public Flux<User> findAll() {
        return this.userRepository.findAll();
    }

    @Override
    public Mono<User> findById(String id) {
        return this.userRepository.findById(id);
    }

    @Override
    public Mono<User> update(String id, User user) {
        return this.userRepository
                .findById(id)
                .flatMap(u -> {
                    u.setId(id);
                    u.setEmail(user.getEmail());
                    u.setName(user.getName());
                    return save(u);
                }).switchIfEmpty(Mono.empty());
    }

    @Override
    public Mono<User> delete(String id) {
        return this.userRepository
                .findById(id)
                .flatMap(user -> this.userRepository
                        .deleteById(user.getId())
                        .thenReturn(user));
    }
}
