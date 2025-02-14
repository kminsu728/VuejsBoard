package com.mskim.demo.base.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class DemoController {

    @GetMapping("/")
    public String hello() {
        return "Hello from Spring Boot!";
    }

}
