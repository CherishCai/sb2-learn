package cn.cherish.lomboklearn.web;

import cn.cherish.lomboklearn.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * @author Cherish
 * @version 1.0
 * @date 2018/6/13 12:08
 */
@Slf4j
@RestController
@RequestMapping("/api/users2")
public class UserController2 {


    Map<Long, User> users = new TreeMap<>();

    @PostConstruct
    public void init() throws Exception {
        users.put(1L, new User(1, "Jack", "Smith", 20));
        users.put(2L, new User(2, "Peter", "Johnson", 25));
    }

    /**
     * 获取所有用户
     */
    @GetMapping("/list")
    public Flux<User> getAll() {
        log.info("user2 getAll");
        return Flux.fromIterable(
                users.entrySet().stream()
                        .map(Map.Entry::getValue)
                        .collect(Collectors.toList())
        );
    }

    /**
     * 获取单个用户
     */
    @GetMapping("/{id}")
    public Mono<User> getOne(@PathVariable Long id) {
        return Mono.justOrEmpty(users.get(id));
    }

    /**
     * 创建用户
     */
    @PostMapping
    public Mono<ResponseEntity<String>> create(@RequestBody User user) {
        users.put(user.getId(), user);
        log.info("########### POST:{}", user);
        return Mono.just(new ResponseEntity<>("Post Successfully!", HttpStatus.CREATED));
    }

    /**
     * 修改用户
     */
    @PutMapping("/{id}")
    public Mono<ResponseEntity<User>> update(@PathVariable Long id, @RequestBody User user) {
        user.setId(id);
        users.put(id, user);
        log.info("########### PUT:{}", user);
        return Mono.just(new ResponseEntity<>(user, HttpStatus.CREATED));
    }

    /**
     * 删除用户
     */
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<String>> delete(@PathVariable Long id) {
        users.remove(id);
        log.info("DELETE id:{}", id);
        return Mono.just(new ResponseEntity<>("Delete Successfully!", HttpStatus.ACCEPTED));
    }
}
