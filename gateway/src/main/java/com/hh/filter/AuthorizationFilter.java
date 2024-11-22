package com.hh.filter;

import com.alibaba.fastjson2.JSON;
import com.hh.constant.GatewayConstant;
import com.hh.common.enums.IEnum;
import com.hh.properties.GatewayCustomizeProperties;
import com.hh.security.authorization.AuthorityPrincipal;
import com.hh.security.constant.SecurityConstant;
import com.hh.security.http.AuthorityStatus;
import com.hh.security.token.TokenManager;
import com.hh.util.PathMatchUtil;
import com.hh.common.response.Result;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
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
public class AuthorizationFilter implements GlobalFilter, Ordered {

    @Resource
    private GatewayCustomizeProperties gatewayCustomizeProperties;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public int getOrder() {
        return GatewayConstant.FILTER_ORDER_AUTHENTICATION;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        String path = request.getPath().value();
        // 不需要认证的路径
        if (PathMatchUtil.notMatch(gatewayCustomizeProperties.getAuthorizationExcludePath(), path)) {
            log.info("请求地址：{}，需要验证Token", path);
            String token = request.getHeaders().getFirst(SecurityConstant.HEADER_TOKEN_KEY);
            if (StringUtils.isBlank(token)) {
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return getVoidMono(response, AuthorityStatus.NO_TOKEN);
            } else {
                token = token.replace(SecurityConstant.HEADER_TOKEN_PREFIX, "");
                try {
                    AuthorityPrincipal principal = TokenManager.parse(token);
                    // 查询redis是否有key
                    String redisToken = redisTemplate.opsForValue().get(SecurityConstant.TOKEN_REDIS_PREFIX + principal.getId());
                    // 没有就是过期
                    if (StringUtils.isBlank(redisToken)) {
                        response.setStatusCode(HttpStatus.UNAUTHORIZED);
                        return getVoidMono(response, AuthorityStatus.EXPIRED_TOKEN);
                    }
                    // 有不匹配就是下线
                    if (StringUtils.equals(redisToken, token)) {
                        response.setStatusCode(HttpStatus.UNAUTHORIZED);
                        return getVoidMono(response, AuthorityStatus.OFFLINE);
                    }
                } catch (Exception e) {
                    response.setStatusCode(HttpStatus.UNAUTHORIZED);
                    return getVoidMono(response, AuthorityStatus.EXPIRED_TOKEN);
                }
            }
        } else {
            log.info("请求地址：{}，不需要验证Token", path);
        }
        return chain.filter(exchange);
    }

    private Mono<Void> getVoidMono(ServerHttpResponse response, IEnum status) {
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        DataBuffer buffer = response.bufferFactory().wrap(JSON.toJSONString(Result.error(status)).getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Flux.just(buffer));
    }

}
