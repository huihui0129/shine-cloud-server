package com.hh.filter;

import com.hh.common.constant.AuthorizationConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author huihui
 * @date 2024/11/4 11:24
 * @description RequestLogFilter
 */
@Slf4j
@Component
public class RequestLogFilter implements GlobalFilter, Ordered {

    @Override
    public int getOrder() {
        return AuthorizationConstant.FILTER_ORDER_REQUEST_LOG;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String uri = request.getURI().toString();
        String path = request.getPath().value();
        log.info("请求uri：{}", uri);
        log.info("请求path：{}", path);
        return chain.filter(exchange);
    }

}
