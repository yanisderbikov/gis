package ru.gisbis.gateway.filters;


import org.springframework.boot.web.server.Cookie;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.*;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebSession;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import ru.gisbis.gateway.model.Authorities;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

@Component
public class CheckSessionFilter implements GatewayFilter {


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        var cookies1 = request.getCookies();
        var sessionName = cookies1.get("SESSION");
        var respM = exchange.getResponse();
        respM.addCookie(ResponseCookie.from("SESSION", sessionName.get(0).getValue())
                .httpOnly(true).build());
        WebClient client = WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(
                        HttpClient.create().followRedirect(true)
                ))
                .defaultCookie("SESSION", sessionName.get(0).getValue())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();


        return client
                .get()
                .uri("http://localhost:8081/user")
                .retrieve()
                .onStatus(status->status.is3xxRedirection()
                , response -> {
                    return Mono.error(new RuntimeException("Server error: " + response.statusCode()));
                })
                .bodyToMono(Authorities.class).flatMap(auth -> {
                    var swe = addPrincipal(exchange, auth.getUserName());
                    return chain.filter(swe);
                });
    }


    public ServerWebExchange addPrincipal(ServerWebExchange exchange, String namePrincipal) {
        ServerHttpRequest request = exchange.getRequest();

        UriComponentsBuilder builder = UriComponentsBuilder.fromUri(request.getURI());

        // Добавляем параметр запроса
        builder.queryParam("username", namePrincipal);
        // Строим новый URI с параметрами запроса
        String newUri = builder.build().toString();
        // Создаем новый запрос с измененным URI
        ServerHttpRequest newRequest = request.mutate()
                .uri(URI.create(newUri))
                .build();
        // Обновляем ServerWebExchange с новым запросом
        ServerWebExchange newExchange = exchange.mutate().request(newRequest).build();

        // Возвращаем результат обработки запроса
        return newExchange;
    }


}
