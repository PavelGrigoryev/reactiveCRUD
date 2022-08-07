package com.pavel.reactivecrud.controller;

import com.pavel.reactivecrud.model.User;
import com.pavel.reactivecrud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    private Mono<User> save(@RequestBody User user) {
        return this.userService.save(user);
    }

    @GetMapping("/users")
    private Flux<User> findAll() {
        return this.userService.findAll();
    }

    @GetMapping("/users/{id}")
    private Mono<User> findUserById(@PathVariable("id") String id) {
        return this.userService.findById(id);
    }

    @PutMapping("/users/{id}")
    private Mono<ResponseEntity<User>> update(@PathVariable("id") String id, @RequestBody User user) {
        return this.userService.update(id, user)
                .flatMap(u -> Mono.just(ResponseEntity
                        .ok(u)))
                .switchIfEmpty(Mono.just(ResponseEntity
                        .notFound()
                        .build()));
    }

    @DeleteMapping("/users/{id}")
    private Mono<ResponseEntity<String>> delete(@PathVariable("id") String id) {
        return this.userService.delete(id)
                .flatMap(user -> Mono.just(ResponseEntity
                        .ok("User с id " + id + " успешно удалён")))
                .switchIfEmpty(Mono.just(ResponseEntity
                        .notFound()
                        .build()));
    }
}
