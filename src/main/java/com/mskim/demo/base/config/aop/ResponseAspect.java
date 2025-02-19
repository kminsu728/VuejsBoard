package com.mskim.demo.base.config.aop;

import com.mskim.demo.base.model.VuejsException;
import com.mskim.demo.base.model.VueJsResponse;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Order(1)
@Component
public class ResponseAspect {

    @Around("within(@org.springframework.web.bind.annotation.RestController *)")
    public Object around(ProceedingJoinPoint joinPoint) {
        try {
            return joinPoint.proceed();
        }  catch (AccessDeniedException e) {
            return VueJsResponse.error(HttpStatus.FORBIDDEN, "Forbidden", e.getMessage(), "AUTH_ERROR");
        } catch (VuejsException ve) {
            return VueJsResponse.error(ve);
        } catch (Throwable throwable) {
            return VueJsResponse.error(throwable);
        }
    }
}
