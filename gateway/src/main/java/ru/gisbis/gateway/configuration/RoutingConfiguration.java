package ru.gisbis.gateway.configuration;


import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpMethod;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import ru.gisbis.gateway.filters.CheckEmptySessionFilter;
import ru.gisbis.gateway.filters.CheckSessionFilter;

import java.util.function.Predicate;

@Configuration
public class RoutingConfiguration {


    private final CheckSessionFilter checkSessionFilter;
    private final CheckEmptySessionFilter checkEmptySessionFilter;


    public RoutingConfiguration(CheckSessionFilter checkSessionFilter, CheckEmptySessionFilter checkEmptySessionFilter) {
        this.checkSessionFilter = checkSessionFilter;
        this.checkEmptySessionFilter = checkEmptySessionFilter;
    }


    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("login_id", route -> route.path(
                                "/login"
                        )
                        .uri("http://localhost:8081"))
                .route("logout_id", route -> route.path(
                                "/logout"
                        )
                        .uri("http://localhost:8081"))
                .route("user-ws",
                        route -> route.path("/records")
                                .and()
                                .method(HttpMethod.GET)

                                //.and()
                                // .predicate(checkSession())
                                .filters(f -> f
                                        .filter(checkEmptySessionFilter)
                                        .filter(checkSessionFilter))
                                .uri("http://localhost:8082")
                )
//                .route("any_req_id",
//                        r -> r.alwaysTrue()
//                                .uri("http://localhost:8081")
//                )
                .build();
    }

    private Predicate<ServerWebExchange> checkSession() {
        return serverWebExchange -> {
            MultiValueMap<String, HttpCookie> cookies = serverWebExchange.getRequest().getCookies();
            System.out.println(cookies.keySet());
            return cookies.containsKey("SESSION");
        };
    }

}
