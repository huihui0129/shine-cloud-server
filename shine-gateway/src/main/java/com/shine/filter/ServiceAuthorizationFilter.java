package com.shine.filter;

import com.alibaba.fastjson2.JSON;
import com.shine.common.enums.IEnum;
import com.shine.common.response.Result;
import com.shine.constant.GatewayConstant;
import com.shine.properties.GatewayCustomizeProperties;
import com.shine.security.authorization.impl.AuthorityPrincipal;
import com.shine.security.constant.SecurityConstant;
import com.shine.security.http.SecurityStatus;
import com.shine.security.token.TokenManager;
import com.shine.security.utils.PathMatchUtil;
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
public class ServiceAuthorizationFilter extends CommonGlobalFilter implements GlobalFilter, Ordered {

    @Resource
    private GatewayCustomizeProperties gatewayCustomizeProperties;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public int getOrder() {
        return GatewayConstant.FILTER_ORDER_SERVICE_AUTHENTICATION;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        String path = request.getPath().value();
        String client = request.getHeaders().getFirst(GatewayConstant.CLIENT_KEY);
        // 不需要认证的路径
        if ("service".equals(client)) {
            if (PathMatchUtil.notMatch(gatewayCustomizeProperties.getAuthorizationExcludePath(), path)) {
                String token = request.getHeaders().getFirst(SecurityConstant.HEADER_TOKEN_KEY);
                if (StringUtils.isBlank(token)) {
                    response.setStatusCode(HttpStatus.UNAUTHORIZED);
                    return getVoidMono(response, SecurityStatus.NO_TOKEN);
                } else {
                    token = token.replace(SecurityConstant.HEADER_TOKEN_PREFIX, "");
                    try {
                        AuthorityPrincipal principal = TokenManager.parse(token);
                        // 查询redis是否有key
                        String redisToken = redisTemplate.opsForValue().get(SecurityConstant.ACCESS_TOKEN_REDIS_PREFIX + principal.getId());
                        // 没有就是过期
                        Boolean hasKey = redisTemplate.hasKey(SecurityConstant.OFFLINE_REDIS_PREFIX + principal.getId());
                        if (StringUtils.isBlank(redisToken)) {
                            log.error("没有Token，查看是否被人踢下线");
                            response.setStatusCode(HttpStatus.UNAUTHORIZED);
                            if (hasKey != null && hasKey) {
                                return getVoidMono(response, SecurityStatus.ADMIN_OFFLINE);
                            } else {
                                return getVoidMono(response, SecurityStatus.EXPIRED_TOKEN);
                            }
                        }
                        // 有不匹配就是下线
                        if (!StringUtils.equals(redisToken, token)) {
                            log.error("不匹配RedisToken，下线");
                            response.setStatusCode(HttpStatus.UNAUTHORIZED);
                            return getVoidMono(response, SecurityStatus.OFFLINE);
                        }
                        if (hasKey != null && hasKey) {
                            redisTemplate.delete(SecurityConstant.OFFLINE_REDIS_PREFIX + principal.getId());
                        }
                    } catch (Exception e) {
                        log.error("解析Token异常：", e);
                        response.setStatusCode(HttpStatus.UNAUTHORIZED);
                        return getVoidMono(response, SecurityStatus.EXPIRED_TOKEN);
                    }
                }
            }
        }
        return chain.filter(exchange);
    }

}
