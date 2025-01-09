package com.shine.filter;

import com.alibaba.fastjson2.JSON;
import com.shine.common.enums.IEnum;
import com.shine.common.response.Result;
import com.shine.constant.GatewayConstant;
import com.shine.properties.GatewayCustomizeProperties;
import com.shine.security.constant.SecurityConstant;
import com.shine.security.utils.PathMatchUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * @author huihui
 * @date 2024/11/4 10:39
 * @description AuthorizationFilter
 */
@Slf4j
@Component
public class ClientAuthorizationFilter implements GlobalFilter, Ordered {

    @Resource
    private GatewayCustomizeProperties gatewayCustomizeProperties;

    @Override
    public int getOrder() {
        return GatewayConstant.FILTER_ORDER_CLIENT_AUTHENTICATION;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getPath().value();
        // 不需要认证的路径
        log.info("执行客户端验证Filter：{}", path);
        if (PathMatchUtil.notMatch(gatewayCustomizeProperties.getAuthorizationExcludePath(), path)) {
            String client = request.getHeaders().getFirst(GatewayConstant.CLIENT_KEY);
            if (StringUtils.isBlank(client)) {
                request.mutate()
                        .header(GatewayConstant.CLIENT_KEY, "service")
                        .build();
            } else {
                log.info("客户端：{}", client);
            }
        }
        return chain.filter(exchange);
    }

    private Mono<Void> getVoidMono(ServerHttpResponse response, IEnum status) {
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        DataBuffer buffer = response.bufferFactory().wrap(JSON.toJSONString(Result.error(status)).getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Flux.just(buffer));
    }

}
