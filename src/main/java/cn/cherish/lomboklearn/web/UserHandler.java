package cn.cherish.lomboklearn.web;

import cn.cherish.lomboklearn.dao.UserRepository;
import cn.cherish.lomboklearn.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;

/**
 * @author Cherish
 * @version 1.0
 * @date 2018/6/13 12:04
 */
@Slf4j
@Component
public class UserHandler {

    private final UserRepository customerRepository;

    public UserHandler(UserRepository repository) {
        this.customerRepository = repository;
    }

    /**
     * GET ALL Users
     */
    public Mono<ServerResponse> getAll(ServerRequest request) {
        // fetch all customers from repository
        Flux<User> customers = customerRepository.getAll();
        // build response
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(customers, User.class);
    }

    /**
     * GET a User by ID
     */
    public Mono<ServerResponse> getUser(ServerRequest request) {
        // parse path-variable
        long customerId = Long.valueOf(request.pathVariable("id"));

        // get customer from repository
        Mono<User> customerMono = customerRepository.getById(customerId);

        // build response
        return customerMono
                .flatMap(customer -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(fromObject(customer)))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    /**
     * POST a User
     */
    public Mono<ServerResponse> postUser(ServerRequest request) {
        Mono<User> customer = request.bodyToMono(User.class);
        return ServerResponse.ok().build(customerRepository.save(customer));
    }

    /**
     * PUT a User
     */
    public Mono<ServerResponse> putUser(ServerRequest request) {
        // parse id from path-variable
        long customerId = Long.valueOf(request.pathVariable("id"));

        // get customer data from request object
        Mono<User> customer = request.bodyToMono(User.class);

        // get customer from repository
        Mono<User> responseMono = customerRepository.update(customerId, customer);

        // build response
        return responseMono
                .flatMap(cust -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(fromObject(cust)));
    }

    /**
     * DELETE a User
     */
    public Mono<ServerResponse> deleteUser(ServerRequest request) {
        // parse id from path-variable
        long customerId = Long.valueOf(request.pathVariable("id"));

        log.info("DELETE customerId:{}", customerId);
        // get customer from repository
        Mono<String> responseMono = customerRepository.delete(customerId);

        // build response
        return responseMono
                .flatMap(strMono -> ServerResponse.ok().contentType(MediaType.TEXT_PLAIN).body(fromObject(strMono)));
    }

}
