package com.mskim.demo.base.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Order(2)
@Component
public class ResponseAspect {
    @After("execution(* com.mskim.demo.base.controller..*(..))")
    public void makeResponse() {
        System.out.println("__ResponseAspect__");

    }
}
