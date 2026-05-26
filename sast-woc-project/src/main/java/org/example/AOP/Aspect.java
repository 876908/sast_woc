package org.example.AOP;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class Aspect {
    @Around("@annotation(org.example.annotation.LogOperation)")
    public Object record(ProceedingJoinPoint pjp) throws Throwable {
        String method = pjp.getSignature().toShortString();
        log.info("方法开始: {}", method);
        try {
            Object result = pjp.proceed();
            log.info("方法结束: {}", method);
            return result;
        } catch (Throwable e) {
            log.error("方法异常: {}", method, e);
            throw e;
        }
    }
}
