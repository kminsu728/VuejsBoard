package com.mskim.demo.base.config;

import java.util.Enumeration;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Slf4j
@Aspect
@Order()
@Component
public class LogAspect {
    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    private String getSessionId() {
        return Optional.ofNullable(RequestContextHolder.getRequestAttributes())
                .filter(ServletRequestAttributes.class::isInstance)
                .map(ServletRequestAttributes.class::cast)
                .map(ServletRequestAttributes::getRequest)
                .map(HttpServletRequest::getSession)
                .map(session -> session.getId().substring(0, 10))
                .orElse("");
    }

    //@Around 말고 다른 어노테이션으로 처리 가능
    @Around("execution(* com.mskim.demo.base..*.*(..))")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        String sessionId = getSessionId();
        String pkgName = joinPoint.getSignature().getDeclaringTypeName();

        logger.info("[{}]->{}", sessionId, pkgName);
        Object result;
        try {
            result = joinPoint.proceed();  // 실제 메서드 실행
            logger.info("[{}]<-{}", sessionId, pkgName);
        } catch (Throwable throwable) {
            logger.error("[{}]!{} 실행 중 예외 발생 : {}", sessionId, pkgName,
                    throwable.getMessage());
            throw throwable;
        }
        return result;
    }

    @Before("execution(* com.mskim.demo.base.controller..*.*(..))")
    public void logRequestHeaderAndParams(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 로그에 컨트롤러 및 메소드 정보 출력
        String controllerName = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        logger.info("Calling controller: {}#{}", controllerName, methodName);

        // Request Header 정보 출력
        HttpHeaders headers = new HttpHeaders();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headers.add(headerName, request.getHeader(headerName));
        }
        logger.info("Request Headers: {}", headers);

        // Request Parameter 정보 출력
        Map<String, String[]> parameterMap = request.getParameterMap();
        parameterMap.forEach((key, value) -> logger.info("Request Parameter: {} = {}", key, String.join(",", value)));
    }

    @After("execution(* com.mskim.demo.base.controller..*(..))")
    public void makeResponse() {
        System.out.println("__LogAspect__");

    }

}