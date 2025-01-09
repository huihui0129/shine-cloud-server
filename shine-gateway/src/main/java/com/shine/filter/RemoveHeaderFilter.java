package com.shine.filter;

import com.alibaba.fastjson2.JSON;
import com.shine.common.enums.IEnum;
import com.shine.common.response.Result;
import com.shine.constant.GatewayConstant;
import com.shine.properties.GatewayCustomizeProperties;
import com.shine.security.constant.SecurityConstant;
import com.shine.security.token.TokenManager;
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
public class RemoveHeaderFilter implements GlobalFilter, Ordered {

    @Resource
    private GatewayCustomizeProperties gatewayCustomizeProperties;

    @Override
    public int getOrder() {
        return GatewayConstant.REMOVE_HEADER_FILTER;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getPath().value();
        // 不需要认证的路径
        log.info("执行移除请求头验证Filter：{}", path);
        if (PathMatchUtil.match(gatewayCustomizeProperties.getAuthorizationExcludePath(), path)) {
            String token = request.getHeaders().getFirst(SecurityConstant.HEADER_TOKEN_KEY);
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
