package cn.cherish.lomboklearn.dao;

import cn.cherish.lomboklearn.entity.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Cherish
 * @version 1.0
 * @date 2018/6/13 12:03
 */
public interface UserRepository {
    Mono<User> getById(Long id);

    Flux<User> getAll();

    Mono<Void> save(Mono<User> user);

    Mono<User> update(Long id, Mono<User> user);

    Mono<String> delete(Long id);
}
