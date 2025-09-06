package com.xk857.framework.aop;

import com.xk857.framework.aop.annotation.AdminOnly;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;

/**
 * 权限保护代理：拦截 @AdminOnly
 */
@Slf4j
@Aspect
@Component
public class SecurityAspect {

    @Before("@annotation(com.xk857.framework.aop.annotation.AdminOnly)")
    public void checkAdmin(JoinPoint joinPoint) {
        RequestAttributes attrs = RequestContextHolder.getRequestAttributes();
        if (!(attrs instanceof ServletRequestAttributes attributes)) {
            // 非Web上下文，默认放行（单元测试/离线调用）
            log.debug("[SecurityAspect] 无请求上下文，默认放行");
            return;
        }
        HttpServletRequest request = attributes.getRequest();
        // 从Header读取角色（教学用）
        String role = request.getHeader("X-ROLE");
        String user = request.getHeader(HttpHeaders.AUTHORIZATION);
        boolean isAdmin = role != null && role.equalsIgnoreCase("admin");
        if (!isAdmin) {
            String method = joinPoint.getSignature().toShortString();
            log.warn("[SecurityAspect] 拒绝访问，method={}, role={}, user={}", method, role, user);
            throw new SecurityException("需要管理员权限");
        }
        log.info("[SecurityAspect] 权限校验通过 (admin)");
    }
}

