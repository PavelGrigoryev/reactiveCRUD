package com.pavel.reactivecrud.repository;

import com.pavel.reactivecrud.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.concurrent.ConcurrentLinkedQueue;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    public void setup() {
        Flux<User> deleteAndInsert = userRepository.deleteAll()
                .thenMany(userRepository.saveAll(
                        Flux.just(
                                new User("1", "Pavel", "Pavel@email.edu"),
                                new User("2", "Nikita", "Nikita@yandex.ru"),
                                new User("3", "Svetka", "Svetka@shkura.by")
                        )));
        StepVerifier.create(deleteAndInsert)
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    public void shouldSaveAndFetchUsers() {
        StepVerifier.create(userRepository.findAll())
                .recordWith(ConcurrentLinkedQueue::new)
                .thenConsumeWhile(user -> true)
                .consumeRecordedWith(users -> {
                    assertThat(users).hasSize(3);
                    assertThat(users).contains(new User("1", "Pavel", "Pavel@email.edu"));
                    assertThat(users).contains(new User("2", "Nikita", "Nikita@yandex.ru"));
                    assertThat(users).contains(new User("3", "Svetka", "Svetka@shkura.by"));
                })
                .verifyComplete();

        StepVerifier.create(userRepository.findById("1"))
                .assertNext(user -> user.equals(new User("1", "Pavel", "Pavel@email.edu")))
                .verifyComplete();
    }
}
