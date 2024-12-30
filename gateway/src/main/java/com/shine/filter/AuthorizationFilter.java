package com.shine.filter;

import com.alibaba.fastjson2.JSON;
import com.shine.constant.GatewayConstant;
import com.shine.common.enums.IEnum;
import com.shine.properties.GatewayCustomizeProperties;
import com.shine.security.authorization.impl.AuthorityPrincipal;
import com.shine.security.constant.SecurityConstant;
import com.shine.security.http.AuthorityStatus;
import com.shine.security.token.TokenManager;
import com.shine.security.utils.PathMatchUtil;
import com.shine.common.response.Result;
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
        String token = request.getHeaders().getFirst(SecurityConstant.HEADER_TOKEN_KEY);
        // 不需要认证的路径
        if (PathMatchUtil.notMatch(gatewayCustomizeProperties.getAuthorizationExcludePath(), path)) {
            log.info("请求地址：{}，需要验证Token", path);
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
                    if (!StringUtils.equals(redisToken, token)) {
                        log.error("不匹配RedisToken，下线");
                        response.setStatusCode(HttpStatus.UNAUTHORIZED);
                        return getVoidMono(response, AuthorityStatus.OFFLINE);
                    }
                } catch (Exception e) {
                    log.error("解析Token异常：", e);
                    response.setStatusCode(HttpStatus.UNAUTHORIZED);
                    return getVoidMono(response, AuthorityStatus.EXPIRED_TOKEN);
                }
            }
        } else {
            // 不需要验证的路径尝试解析内容，如果解析异常，那么就删除这个头就行
            log.info("请求地址：{}，不需要验证Token", path);
            if (StringUtils.isNotBlank(token)) {
                token = token.replace("Bearer ", "");
                if (StringUtils.isNotBlank(token)) {
                    // 尝试解析
                    try {
                        // 可以解析到就没事了，继续往下走
                        TokenManager.parse(token);
                    } catch (Exception e) {
                        log.error("解析Token异常，但是这个路径是可以访问的");
                        // 删除这个头以免下游服务误解
                        request.mutate()
                                .headers(headers -> headers.remove(SecurityConstant.HEADER_TOKEN_KEY))
                                .build();
                    }
                }
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
