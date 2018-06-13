package cn.cherish.lomboklearn.dao;

import cn.cherish.lomboklearn.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Cherish
 * @version 1.0
 * @date 2018/6/13 12:03
 */
@Slf4j
@Repository
public class UserRepositoryImpl implements UserRepository {
    private Map<Long, User> users = new TreeMap<>();

    @PostConstruct
    public void init() throws Exception {
        users.put(1L, new User(1, "Jack", "Smith", 20));
        users.put(2L, new User(2, "Peter", "Johnson", 25));
    }

    @Override
    public Mono<User> getById(Long id) {
        return Mono.just(users.get(id));
    }

    @Override
    public Flux<User> getAll() {
        return Flux.fromIterable(this.users.values());
    }

    @Override
    public Mono<Void> save(Mono<User> monoUser) {
        Mono<User> userMono = monoUser.doOnNext(user -> {
            // do post
            users.put(user.getId(), user);

            // log on console
            log.info("########### POST:{}", user);
        });

        return userMono.then();
    }

    @Override
    public Mono<User> update(Long id, Mono<User> monoUser) {

        return monoUser.doOnNext(user -> {
            // reset user.Id
            user.setId(id);

            // do put
            users.put(id, user);

            // log on console
            log.info("########### PUT:{}", user);
        });
    }

    @Override
    public Mono<String> delete(Long id) {
        // delete processing
        users.remove(id);
        return Mono.just("Delete Succesfully!");
    }
}
