package com.mskim.demo.base.config.aop;

import com.mskim.demo.base.model.VuejsException;
import com.mskim.demo.base.model.VueJsResponse;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
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
        } catch (VuejsException ve) {
            return VueJsResponse.error(ve);
        } catch (Throwable throwable) {
            return VueJsResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", throwable.getMessage(), "Z9999");
        }
    }
    //TODO: 현재 객체를 반환하기에 status code 가 무조건 200으로 응답 됨. (RestController 의 기능)
    //TODO: 만약 200이 아닌 status code 로 반환하려면 return ResponseEntity 로 하도록 한번 감싸야 됨.
}
