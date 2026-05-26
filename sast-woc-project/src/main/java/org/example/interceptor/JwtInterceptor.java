package org.example.interceptor;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.example.utils.JwtUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class JwtInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("拦截路径: {}", request.getRequestURI());
        String token = request.getHeader("token");
        if (token == null || token.isEmpty()) {
            log.info("令牌不存在(401)");
            response.setStatus(401);
            return false;
        }
        Claims claims;
        try {
            claims = JwtUtils.parseJWT(token);
        } catch (Exception e) {
            log.info("令牌解析失败(401)");
            response.setStatus(401);
            return false;
        }
        log.info("claims:{}", claims);
        request.setAttribute("UserCode", claims.get("UserCode"));
        Object roleObj = claims.get("Role");
        Integer role = roleObj instanceof Number ? ((Number) roleObj).intValue() : null;
        request.setAttribute("Role", role);
        Object academyIdObj = claims.get("AcademyId");
        Long academyId = academyIdObj instanceof Number ? ((Number) academyIdObj).longValue() : null;
        request.setAttribute("AcademyId", academyId);
        return true;
    }
}
