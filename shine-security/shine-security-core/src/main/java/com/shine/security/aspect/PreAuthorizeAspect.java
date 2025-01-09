package com.shine.security.aspect;

import com.shine.common.exception.SecurityException;
import com.shine.common.status.ResponseStatus;
import com.shine.security.annotation.PreAuthorize;
import com.shine.security.utils.SecurityAuthentication;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

/**
 * @author huihui
 * @date 2024/12/30 13:25
 * @description PreAuthorizeAspect
 */
@Slf4j
@Aspect
@Component
public class PreAuthorizeAspect {

    private final ExpressionParser parser = new SpelExpressionParser();

    @Pointcut("@annotation(preAuthorize)")
    public void preAuthPointcut(PreAuthorize preAuthorize) {
    }

    @Before(value = "preAuthPointcut(preAuthorize)", argNames = "preAuthorize")
    public void checkPermission(PreAuthorize preAuthorize) {
        // 获取注解中的权限表达式
        String expression = preAuthorize.value();
        // 使用 SpEL 解析表达式
        StandardEvaluationContext context = new StandardEvaluationContext(new SecurityAuthentication());
        // 解析并执行表达式
        boolean hasPermission = (Boolean) parser.parseExpression(expression).getValue(context);
        if (!hasPermission) {
            log.error("无权：{}", expression);
            throw new SecurityException(ResponseStatus.UNAUTHORIZED);
        }
    }

}
