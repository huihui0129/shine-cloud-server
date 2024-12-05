package com.shine.security.interceptor;

import com.shine.common.exception.BaseException;
import com.shine.security.authorization.impl.AuthorityPrincipal;
import com.shine.security.constant.SecurityConstant;
import com.shine.security.context.SecurityContext;
import com.shine.security.context.SecurityContextHolder;
import com.shine.security.http.AuthorityStatus;
import com.shine.security.token.TokenManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author huihui
 * @date 2024/11/22 14:30
 * @description SecurityContextInterceptor
 */
public class SecurityContextInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader(SecurityConstant.HEADER_TOKEN_KEY);
        if (StringUtils.isNotBlank(token)) {
            token = token.replace("Bearer ", "");
            if (StringUtils.isNotBlank(token)) {
                try {
                    AuthorityPrincipal principal = TokenManager.parse(token, AuthorityPrincipal.class);
                    SecurityContext context = new SecurityContext();
                    context.setPrincipal(principal);
                    SecurityContextHolder.setContext(context);
                } catch (Exception e) {
                    throw new BaseException(AuthorityStatus.EXPIRED_TOKEN);
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        SecurityContextHolder.clearContext();
    }
}
