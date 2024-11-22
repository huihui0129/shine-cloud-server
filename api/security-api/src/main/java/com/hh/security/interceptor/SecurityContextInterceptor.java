package com.hh.security.interceptor;

import com.hh.common.exception.BaseException;
import com.hh.security.authorization.impl.AuthorityPrincipal;
import com.hh.security.constant.SecurityConstant;
import com.hh.security.context.SecurityContext;
import com.hh.security.context.SecurityContextHolder;
import com.hh.security.http.AuthorityStatus;
import com.hh.security.token.TokenManager;
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
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        String token = request.getHeader(SecurityConstant.HEADER_TOKEN_KEY);
        if (StringUtils.isNotBlank(token)) {
            token = token.replace("Bearer ", "");
            if (StringUtils.isNotBlank(token)) {
                try {
                    AuthorityPrincipal principal = TokenManager.parse(token);
                    SecurityContext context = new SecurityContext();
                    context.setPrincipal(principal);
                    SecurityContextHolder.setContext(context);
                } catch (Exception e) {
                    throw new BaseException(AuthorityStatus.EXPIRED_TOKEN);
                }
            }
        }
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
