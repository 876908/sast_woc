package org.example.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.example.annotation.RequireRoleAnnotation;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class RoleInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        RequireRoleAnnotation.RequireRole methodRequireRole = handlerMethod.getMethodAnnotation(RequireRoleAnnotation.RequireRole.class);
        RequireRoleAnnotation.RequireRole classRequireRole = handlerMethod.getBeanType().getAnnotation(RequireRoleAnnotation.RequireRole.class);
        RequireRoleAnnotation.RequireRole requireRole = methodRequireRole != null ? methodRequireRole : classRequireRole;
        if (requireRole == null) {
            return true;
        }
        int[] allowedRoles = requireRole.value();
        Integer role = (Integer) request.getAttribute("Role");
        if (role == null) {
            log.info("用户身份缺失");
            response.setStatus(401);
            return false;
        }
        for (int r : allowedRoles) {
            if (r == role) {
                return true;
            }
        }
        log.info("用户权限不足");
        response.setStatus(403);
        return false;
    }
}




