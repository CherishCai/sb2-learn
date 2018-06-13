package cn.cherish.lomboklearn.route;


import cn.cherish.lomboklearn.web.UserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * @author Cherish
 * @version 1.0
 * @date 2018/6/13 12:00
 */
@Configuration
public class RoutingConfiguration {

    @Bean
    public RouterFunction<ServerResponse> monoRouterFunction(UserHandler userHandler) {
        return route(GET("/api/users/list").and(accept(MediaType.APPLICATION_JSON)), userHandler::getAll)
                .andRoute(GET("/api/users/{id}").and(accept(MediaType.APPLICATION_JSON)), userHandler::getUser)
                .andRoute(POST("/api/users").and(accept(MediaType.APPLICATION_JSON)), userHandler::postUser)
                .andRoute(PUT("/api/users/{id}").and(accept(MediaType.APPLICATION_JSON)), userHandler::putUser)
                .andRoute(DELETE("/api/users/{id}").and(accept(MediaType.APPLICATION_JSON)), userHandler::deleteUser);
    }

}