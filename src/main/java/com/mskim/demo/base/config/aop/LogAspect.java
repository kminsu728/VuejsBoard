package com.mskim.demo.base.config.aop;

import java.util.Enumeration;
import java.util.Map;

import com.mskim.demo.base.model.VuejsException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Slf4j
@Aspect
@Order(2)
@Component
public class LogAspect {

    private static final ThreadLocal<String> sessionIdThreadLocal = new ThreadLocal<>();

    private String getSessionId() {
        if (sessionIdThreadLocal.get() == null) {
            String sessionId = Optional.ofNullable(RequestContextHolder.getRequestAttributes())
                    .filter(ServletRequestAttributes.class::isInstance)
                    .map(ServletRequestAttributes.class::cast)
                    .map(ServletRequestAttributes::getRequest)
                    .map(HttpServletRequest::getSession)
                    .map(session -> session.getId().substring(0, 10))
                    .orElse("");
            sessionIdThreadLocal.set(sessionId);
        }
        return sessionIdThreadLocal.get();
    }

    @Around("execution(* com.mskim.demo.rest..*.*(..))")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        String sessionId = getSessionId();
        String pkgName = joinPoint.getSignature().getDeclaringTypeName();

        log.debug("[{}]->{}", sessionId, pkgName);
        Object result;
        try {
            result = joinPoint.proceed();  // 실제 메서드 실행
            log.debug("[{}]<-{}", sessionId, pkgName);
        } catch (VuejsException ve) {
            log.error("[{}]!{} Exception : [{}] {}", sessionId, pkgName, ve.getTitle(), ve.getDescription());
            throw ve;
        } catch (Throwable throwable) {
            log.error("[{}]!{} Exception : ", sessionId, pkgName, throwable);
            throw throwable;
        }
        return result;
    }

    @Around("execution(* com.mskim.demo.base.config..*.*(..)) || execution(* com.mskim.demo.base.component..*.*(..))")
    public Object logAroundConfig(ProceedingJoinPoint joinPoint) throws Throwable {
        String pkgName = joinPoint.getSignature().getDeclaringTypeName();

        log.debug("->{}", pkgName);
        Object result;
        try {
            result = joinPoint.proceed();
            log.debug("<-{}", pkgName);
        } catch (VuejsException ve) {
            log.error("!{} Exception : [{}] {}", pkgName, ve.getTitle(), ve.getDescription());
            throw ve;
        } catch (Throwable throwable) {
            log.error("!{} Exception : ", pkgName, throwable);
            throw throwable;
        }
        return result;
    }

    @Before("execution(* com.mskim.demo.base.sample..*.*(..))")
    public void logRequestHeaderAndParams(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 로그에 컨트롤러 및 메소드 정보 출력
        String controllerName = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        log.debug("Calling controller: {}#{}", controllerName, methodName);

        // Request Header 정보 출력
        HttpHeaders headers = new HttpHeaders();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headers.add(headerName, request.getHeader(headerName));
        }
        log.debug("Request Headers: {}", headers);

        // Request Parameter 정보 출력
        Map<String, String[]> parameterMap = request.getParameterMap();
        parameterMap.forEach((key, value) -> log.debug("Request Parameter: {} = {}", key, String.join(",", value)));
    }

}